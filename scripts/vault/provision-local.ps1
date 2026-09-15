# EMAT local Vault provisioning (Windows dev machine)
# Start the local dev Vault and seed the local-profile secrets into it.
#
# Usage:  powershell -ExecutionPolicy Bypass -File scripts/vault/provision-local.ps1
$ErrorActionPreference = "Stop"

$ComposeFile = "deploy/vault/docker-compose.local.yml"
$Container = "emat-vault"
$Token      = "emat-local-dev-token"   # matches VAULT_DEV_ROOT_TOKEN_ID in the compose file

# --- 1. Start Vault ---------------------------------------------------------
docker --version | Out-Null
if ($LASTEXITCODE -ne 0) { throw "Docker is not installed / not running." }

docker compose -f $ComposeFile up -d
if ($LASTEXITCODE -ne 0) { throw "Failed to start the Vault container." }

Write-Host "Waiting for Vault to become healthy..."
$healthy = $false
for ($i = 0; $i -lt 30; $i++) {
    try {
        $r = Invoke-WebRequest -UseBasicParsing "http://localhost:8200/v1/sys/health" -TimeoutSec 2
        if ($r.StatusCode -in 200, 429) { $healthy = $true; break }
    } catch { }
    Start-Sleep -Seconds 1
}
if (-not $healthy) { throw "Vault did not become healthy on port 8200." }
Write-Host "Vault is up."

# --- 2. Seed local secrets --------------------------------------------------
# Runs the vault CLI inside the container. Update values to match your local Oracle setup.
$seed = @(
    "docker", "exec", $Container, "vault", "kv", "put", "secret/emat/local",
    "spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/EMAT",
    "spring.datasource.username=EMAT",
    "spring.datasource.password=EMAT",
    "jwt.secret=EMATSecretKeyForJwtSigningMustBeAtLeast32BytesLong123!",
    "pii.encryption.secret-key=7fK9vQ2mX8rT4zLp6Nw3Yc1Hs5Ua0EeJ9"
)
& $seed
if ($LASTEXITCODE -ne 0) { throw "Failed to write secrets to Vault." }

Write-Host ""
Write-Host "====================================================="
Write-Host "Local Vault ready. Secrets written to secret/emat/local"
Write-Host ""
Write-Host "Before running the app (local profile), set the env var:"
Write-Host "  setx VAULT_TOKEN $Token   (new terminals will pick it up)"
Write-Host "  `$env:VAULT_TOKEN = `"$Token`"   (current terminal)"
Write-Host ""
Write-Host "Then run the app with:  --spring.profiles.active=local"
Write-Host "====================================================="