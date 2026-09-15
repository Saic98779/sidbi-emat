# HashiCorp Vault integration for EMAT

Secrets (database credentials, JWT signing key, PII AES encryption key) are stored in
HashiCorp Vault and injected into the Spring context at startup via
**Spring Cloud Vault 5.0.2** (Spring Boot 4.0.1 / Jackson 3 alignment).

## How it works

- Base config (`application.yaml`) declares `spring.config.import: vault://` plus the Vault
  connection defaults and the KV backend (`secret`, v2, application name `emat`).
- The active profile (`local` / `prod`) selects the profile-specific properties file, which
  overrides the Vault endpoint + auth method.
- At startup Spring Cloud Vault reads secrets from these paths (first match wins):

  | Path                     | Used when            |
  |--------------------------|----------------------|
  | `secret/emat/{profile}`  | profile specific     |
  | `secret/emat`            | all profiles         |
  | `secret/application/{profile}` | shared defaults |
  | `secret/application`     | shared defaults      |

  Any key stored there becomes a Spring property directly (e.g. `spring.datasource.password`,
  `jwt.secret`, `pii.encryption.secret-key`). Those secrets are **not** committed in any
  properties file anymore.

## Lifecycle of a secret value (example)

    properties files (committed)                 Vault (NOT committed)
    ---------------------------                  -----------------------
    spring.datasource.driver-class-name  ->      spring.datasource.url
    (non-secret, stays in YAML)                  spring.datasource.username
    pii.testing-endpoint.enabled (flag)   ->     spring.datasource.password
    file.storage.base (path)                    jwt.secret
                                                 pii.encryption.secret-key

## Local development (Windows)

1. Start the dev Vault and seed the local secrets:

   ```powershell
   docker compose -f deploy/vault/docker-compose.local.yml up -d
   powershell -ExecutionPolicy Bypass -File scripts/vault/provision-local.ps1
   ```

2. Export the dev token (the dev Vault runs with a fixed dev-only root token):

   ```powershell
   setx VAULT_TOKEN emat-local-dev-token
   ```

3. Run the app with the local profile:

   ```powershell
   mvn spring-boot:run -Dspring-boot.run.profiles=local
   ```

   The dev Vault is in-memory - **data resets when the container is removed**; just re-run
   `provision-local.ps1`.

## Production (Ubuntu VM with Docker)

On the VM:

```bash
# 1. Set the real prod values (never persist them in the repo)
export PROD_DB_URL='jdbc:oracle:thin:@//<db-host>:1521/EMAT'
export PROD_DB_USERNAME='EMAT'
export PROD_DB_PASSWORD='<password>'
export JWT_SECRET='<>= 32 chars, high entropy>'
export PII_ENCRYPTION_SECRET_KEY='<exactly 32 chars>'

# 2. First-time bootstrap: init, unseal, KV+secrets, AppRole
bash scripts/vault/bootstrap-prod.sh
```

The script writes the 5 unseal keys + root token to `deploy/vault/keys/prod-keys.json`
(`chmod 600`, gitignored) and prints the values to feed the app:

- `SPRING_PROFILES_ACTIVE=prod`
- `VAULT_HOST=<IP-or-hostname-of-this-VM>`
- `VAULT_ROLE_ID=...`
- `VAULT_SECRET_ID=...`

Run the app container with those env vars (values as `docker run -e` / compose `environment`,
never baked into the image or committed):

```bash
docker run -d -p 8086:8086 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e VAULT_HOST=<vault-ip> \
  -e VAULT_ROLE_ID=<role-id> \
  -e VAULT_SECRET_ID=<secret-id> \
  emat:1.0-SNAPSHOT
```

Notes:
- Vault runs with file storage under `deploy/vault/docker-compose.yml` (data survives restarts;
  it is not sealed automatically on restart - re-run `bootstrap-prod.sh` to unseal, or configure
  auto-unseal).
- `VAULT_HOST` is read by `application-prod.properties` for the Vault connection only; the app
  still binds all real secrets from Vault.
- AppRole auth: role `emat`, policy `emat-read` (read-only on `secret/emat/*`), token TTL 1h.
  If the app starts before Vault/Vault is sealed, startup fails fast (`spring.cloud.vault.fail-fast=true`).
- Note: Vault listens on plain HTTP (TLS disabled). Put a TLS-terminating reverse proxy/load
  balancer in front of port 8200 in a real production deployment.

## Rotating / updating a secret

```bash
# Local (in the container, with the dev token):
docker exec emat-vault vault kv put secret/emat/local spring.datasource.password='newpass'

# Prod (on the VM, with root token):
docker exec -e VAULT_TOKEN=<saved-root-token> emat-vault vault kv put secret/emat/prod \
  spring.datasource.password='newpass'
```

Then restart the app (or trigger a config refresh when the app is wired for it).

## Security notes

- All secrets were removed from the committed properties files.
- The `pii.encryption.secret-key` must be exactly 16/24/32 characters (AES-128/192/256).
- Unseal keys and root token live only in `deploy/vault/keys/` (gitignored) - back them up
  offline and revoke the root token after AppRole is verified.
- Kubernetes-based prod would typically add `spring-cloud-vault-config` Kubernetes auth; this
  repo uses AppRole for the Docker-on-VM setup.