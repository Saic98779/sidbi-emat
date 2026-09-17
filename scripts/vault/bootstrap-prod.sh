#!/usr/bin/env bash
#
# Bootstrap HashiCorp Vault for the EMAT production environment.
# Run on the Ubuntu VM where Vault will live.
#
# Does (idempotently):
#   1. Start the Vault container (docker compose)
#   2. Initialize + unseal Vault (keys written to deploy/vault/keys/ - gitignored)
#   3. Enable KV v2 at secrets/<...> and write the EMAT prod secrets
#   4. Enable AppRole auth and create a role bound to a least-privilege policy
#   5. Print the VAULT_HOST / VAULT_ROLE_ID / VAULT_SECRET_ID values for the app
#
# Prerequisites: docker + docker compose plugin, and the prod secret values:
#   PROD_DB_URL, PROD_DB_USERNAME, PROD_DB_PASSWORD, JWT_SECRET, PII_ENCRYPTION_SECRET_KEY,
#   LOGIN_RSA_PRIVATE_KEY (base64-encoded PKCS#8 RSA private key used to decrypt login passwords)
# Generate a key with:
#   openssl genpkey -algorithm RSA -pkeyopt rsa_keygen_bits:2048 -out login-rsa.pem
#   openssl pkcs8 -topk8 -nocrypt -in login-rsa.pem -outform DER | base64 -w0
set -euo pipefail

VAULT_COMPOSE="deploy/vault/docker-compose.yml"
VAULT_CONTAINER="emat-vault"
KEYS_DIR="deploy/vault/keys"
KEYS_FILE="$KEYS_DIR/prod-keys.json"
POLICY="emat-read"
ROLE="emat"

mkdir -p "$KEYS_DIR"
chmod 700 "$KEYS_DIR"

# --- 1. Start Vault ---------------------------------------------------------
docker compose -f "$VAULT_COMPOSE" up -d
echo "Waiting for Vault to come up..."
for i in $(seq 1 30); do
  if docker exec "$VAULT_CONTAINER" vault status >/dev/null 2>&1; then
    break
  fi
  sleep 1
done

if ! docker exec "$VAULT_CONTAINER" vault status >/dev/null 2>&1; then
  echo "ERROR: Vault did not become reachable." >&2
  exit 1
fi
echo "Vault is up."

# --- 2. Initialize + unseal -------------------------------------------------
if [ ! -s "$KEYS_FILE" ]; then
  echo "Initializing Vault..."
  docker exec "$VAULT_CONTAINER" vault operator init \
    -key-shares=5 -key-threshold=3 -format=json > "$KEYS_FILE"
  chmod 600 "$KEYS_FILE"
  echo "Unseal keys + root token saved to $KEYS_FILE (permission 600). Back this up securely!"
fi

ROOT_TOKEN=$(sed -n 's/.*"root_token": "\([^"]*\)".*/\1/p' "$KEYS_FILE")

sealed=$(docker exec "$VAULT_CONTAINER" vault status -format=json | sed -n 's/.*"sealed": \([^,]*\).*/\1/p')
if [ "$sealed" = "true" ]; then
  echo "Unsealing Vault (3 keys)..."
  UNSEAL_KEYS=$(sed -n 's/.*"unseal_keys_b64": \[\([^]]*\)\].*/\1/p' "$KEYS_FILE" | tr -d '"' | tr ',' '\n' | sed 's/^ *//;s/ *$//' | head -n 3)
  for key in $UNSEAL_KEYS; do
    docker exec "$VAULT_CONTAINER" vault operator unseal "$key" >/dev/null
  done
  echo "Vault unsealed."
fi

# Use the CLI via docker exec (no Vault binary needed on the host).
vault_cli() { docker exec -e VAULT_TOKEN="$ROOT_TOKEN" -e VAULT_ADDR="http://127.0.0.1:8200" "$VAULT_CONTAINER" vault "$@"; }

# --- 3. KV v2 engine + secrets ----------------------------------------------
echo "Ensuring KV v2 secrets engine at secret/ (silently ignoring 'already in use')..."
vault_cli secrets enable -path=secret kv-v2 2>/dev/null || true

echo "Writing production secrets to secret/emat/prod..."
vault_cli kv put "secret/emat/prod" \
  "spring.datasource.url=${PROD_DB_URL:?Set PROD_DB_URL}" \
  "spring.datasource.username=${PROD_DB_USERNAME:?Set PROD_DB_USERNAME}" \
  "spring.datasource.password=${PROD_DB_PASSWORD:?Set PROD_DB_PASSWORD}" \
  "jwt.secret=${JWT_SECRET:?Set JWT_SECRET}" \
  "pii.encryption.secret-key=${PII_ENCRYPTION_SECRET_KEY:?Set PII_ENCRYPTION_SECRET_KEY}" \
  "pii.login-rsa.private-key=${LOGIN_RSA_PRIVATE_KEY:?Set LOGIN_RSA_PRIVATE_KEY (base64 PKCS#8 RSA key)}"

# --- 4. Policy + AppRole -----------------------------------------------------
echo "Writing policy $POLICY..."
cat > /tmp/emat-vault-policy.hcl <<EOF
path "secret/data/emat/*" {
  capabilities = ["read"]
}
path "secret/metadata/emat/*" {
  capabilities = ["read"]
}
EOF
vault_cli policy write "$POLICY" /tmp/emat-vault-policy.hcl

echo "Enabling AppRole auth (silently ignoring 'already in use')..."
vault_cli auth enable approle 2>/dev/null || true

vault_cli write "auth/approle/role/$ROLE" \
  secret_id_ttl="0m" token_ttl="1h" token_max_ttl="24h" \
  "policies=$POLICY" >/dev/null

ROLE_ID=$(vault_cli read -format=json "auth/approle/role/$ROLE/role-id" | sed -n 's/.*"role_id": *"\([^"]*\)".*/\1/p')
SECRET_ID=$(vault_cli write -force -format=json "auth/approle/role/$ROLE/secret-id" | sed -n 's/.*"secret_id": *"\([^"]*\)".*/\1/p')

# --- 5. Output ---------------------------------------------------------------
echo
echo "==========================================================="
echo "EMAT production Vault is ready."
echo
echo "Set these on the app container / shell (do NOT commit them):"
echo "  SPRING_PROFILES_ACTIVE=prod"
echo "  VAULT_HOST=<IP-or-hostname-of-this-VM>"
echo "  VAULT_ROLE_ID=$ROLE_ID"
echo "  VAULT_SECRET_ID=$SECRET_ID"
echo
echo "Unseal keys + root token: $KEYS_FILE (see deploy/vault/keys/)"
echo "Secrets vault path        : secret/emat/prod"
echo "==========================================================="