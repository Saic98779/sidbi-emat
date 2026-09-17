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
# pii.login-rsa.private-key: base64 PKCS#8 RSA private key used to decrypt the login password
#   (encrypted with the public key from GET /auth/public-key). Generate a new one with:
#   node -e "const c=require('crypto');const k=c.generateKeyPairSync('rsa',{modulusLength:2048});console.log(Buffer.from(k.privateKey.export({type:'pkcs8',format:'der'})).toString('base64'))"
#   or:  openssl genpkey -algorithm RSA -pkeyopt rsa_keygen_bits:2048 | openssl pkcs8 -topk8 -nocrypt -outform DER | base64 -w0
$seed = @(
    "docker", "exec", $Container, "vault", "kv", "put", "secret/emat/local",
    "spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/EMAT",
    "spring.datasource.username=EMAT",
    "spring.datasource.password=EMAT",
    "jwt.secret=EMATSecretKeyForJwtSigningMustBeAtLeast32BytesLong123!",
    "pii.encryption.secret-key=7fK9vQ2mX8rT4zLp6Nw3Yc1Hs5Ua0EeJ9",
    "pii.login-rsa.private-key=MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDKXAVp7zuk/5FgSaesUmT5NYNpabduPlostEVMTPYi0BGfP78HEMHpl1YLh8VtVr1ehFqEDhSyG4z/Oy85Ps+goIga27Dsksa00qK3n5D6pTHKPDM92vRpXsa4i+CkT2ubR/Duuj12xiFfunvQniFxcdyhyX7UC/erT8lqflhVoRPmuJyZm+Av9qfs1cAFYPaB0DERKYigI5ISPy6AZS6H9lSxCPklZuo5QxH2fuFuE+BofRHJ/2QbfNcmaGir0CEnKiKWfJ30vHAWBenAOtjoy/3uijzAp3VBVXYidnIVWxeGPWnByXwcbyvyHHzhVysooZx/yydLHc9QmxwQ75aJAgMBAAECggEAED3KgXIeC2Qnut6ugnoqeIX/AhrmCgcfHa9qUHFs3+ZrQ6jOIDcMGT94PHytL9ViZY+h5C9tIc5sRp/tbl7IM1AvHFi9nn85S5IMVUiNTlCr6ltikXhdXYep024GnalyLYhcogx4mmvUieZhwaCvdnd5CdMuRr6fJjTFKbLyTAz5DQ0Ho6darM5ALzVtpRnqbYhudmHOb/UMA6VXKK6uwHC0un3MO7E8w+GI5Q2YXt+9IbOq+nU4Z+Yb32PFi09KFZlxyj7fl/Y5N77TdGBg4OZ/JckLVLfO7AxYRwrEGLUOunYM7kTfKKsUj8zmWhXQ1JzZxmswBqV8fyF6McJWAQKBgQDoCrtrdp/DRkrqtZCUYhW7WQ/6Q+j/1ucxxzwu7sP3bkI6ugsmSDTU53XODMxFbXh/xNXye72lY8UkntgO3dkhPxPEMVHbWUYeOxUwgn0eooGpXM1vK9dY22cM4MuFAkX/Ai5uXNp/sveHyZuiKLa/AilWcyR9NvcgO9Xpl4eZWQKBgQDfQLr33lAZmHIRDPJkKmvD2WDSmdglze7laxFElKeqx/c1QjVeX/Jm1a5GH4CCFmfJsdNH48JJd72+VPcYPkC23ApUF6dDij4AMJiS7byxc64yfLP8/yX7HYwVYXJRKf+DnIzF17SDcVcSAqo3Pfjbxs07Lt4HPVlXclBTm0EQsQKBgCfe+DdN6cPHB0ZvLkmg1PUdJ5asJpQXXCB8iIXT9w5D/tNSdmn8jD9tChv+rPbdCZCCxPRmeL67dvuE48EiM5X33bCffN5A58B4DHsKYO9rovX0AUSx9s3I3VskITRYoKhhupKyF8VLfibx6HEjp8sIVwwIuuNdF3eNcIMzy1P5AoGBAL1PcXwASyH5MDifJuejRTCVI1JSXIRqJPrrmH/0Il1iJZFG7GRzreRfq+EQk3M8NpREs2mRi82quP4tpD4tg8jLvAJk8FQIZNzaWHa3G7vJ4Vw0fJe7cGSJQKFcuYhzuZTL6l9Vs1+Ge5PtKJOhfGY5JQzbiDDRZ/IMIGtVCo5xAoGBANlHm+QTc9bVSapbe+u9mBh6W/U7QhgTR5F6SNVKVVgXE4Wjv8+uES+Utvj9ZMbjsgUGf1FBjyqXZDBACtXXAcBOAHJOi0CUpr4A1lK3dOtS/EC9Vz2f3uUpOxNq102/HmousRAvbPRJYZ0TUj5CqQmvqIZl12PszImLM+BU+kj5"
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