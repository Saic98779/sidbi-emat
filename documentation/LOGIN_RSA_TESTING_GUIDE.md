# Login (RSA) Testing Guide

How to test the **RSA-OAEP login password flow** end-to-end. Login is the only endpoint where the
password is encrypted with an **RSA public key** (because no JWT / AES key exists yet).

Complements:

- [`FE_BE_ENCRYPTION_CONTRACT.md`](./FE_BE_ENCRYPTION_CONTRACT.md) - wire contract, §9 = the RSA flow
- [`ENCRYPTION_TESTING_GUIDE.md`](./ENCRYPTION_TESTING_GUIDE.md) - AES `ENC:` testing for everything after login

---

## 1. The flow under test

```
1. GET  /auth/public-key            (NO auth)  -> RSA public key (base64 SPKI)
2. Encrypt password with public key            -> base64 RSA-OAEP/SHA-256 ciphertext
3. POST /users/login                (NO auth)  -> 200 + JWT (or 400/401 on failure)
4. GET  /pii-encryption-key         (WITH JWT) -> AES-256 key for all other fields
```

| Sending to login `password` | Backend result |
|---|---|
| Valid RSA ciphertext + **correct** username/password | `200` + JWT |
| Valid RSA ciphertext + **wrong** password | `401 BadCredentials` (BCrypt mismatch) |
| Valid RSA ciphertext + **unknown** username | `401 BadCredentials` |
| **Plain** text password (never encrypted) | `400` - RSA decrypt fails before auth |
| RSA ciphertext from a **different/stale** public key | `400` - decrypt fails |
| `null` / empty / invalid base64 | `400` |

The RSA private key only **decrypts** the password (transport layer). Username/password validation
happens afterwards with BCrypt, so wrong credentials still return `401`.

---

## 2. Prerequisites

1. App running locally:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=local
   ```
2. Base URL for everything below: `http://localhost:8086/emat/v1`
3. The RSA private key comes from (in priority order):
   - Vault: configured `pii.login-rsa.private-key` (prod - shared across instances)
   - Local file: `pii.login-rsa.key-file` (`application-local.properties` declares `.emat/login-rsa-private.key`).
     Generated **once**, then reloaded on every restart - the public key stays stable, so you do
     **not** need to re-encrypt after restarts.
   - Ephemeral (no config): a fresh pair on every startup (`GET /auth/public-key` must be re-fetched).

   > If a login unexpectedly fails with the mismatched-key message below, delete
   > `.emat/login-rsa-private.key` (or the earlier Vault key), restart, and re-fetch
   > `/auth/public-key`. In local dev the key should now stay the same across restarts.

---

## 3. Step 1 - Get the public key

### cURL

```bash
curl -s http://localhost:8086/emat/v1/auth/public-key
```

Expected `200` (note: **no** `Authorization` header):

```json
{
  "status": 200,
  "message": "Public key for login password encryption",
  "data": {
    "publicKey": "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA...",
    "algorithm": "RSA-OAEP",
    "hash": "SHA-256",
    "keySize": 2048
  }
}
```

### Postman

`GET http://localhost:8086/emat/v1/auth/public-key` - no auth, no headers. Copy `data.publicKey`.

---

## 4. Step 2 - Encrypt the password

The public key is **base64 SPKI (DER)** - import it with Web Crypto `importKey("spki", ...)`, hash
`SHA-256`, and output **standard base64** (no `ENC:` prefix, no URL-safe transforms).

### 4a. Browser - save as `login-encrypt.html`, open at `http://localhost:<port>`

```js
const enc = new TextEncoder();
const dec = new TextDecoder();

const b64ToBuf = (s) => Uint8Array.from(atob(s), (c) => c.charCodeAt(0));
const bufToB64 = (b) => {
  const u = new Uint8Array(b);
  let s = "";
  u.forEach((x) => (s += String.fromCharCode(x)));
  return btoa(s);
};

const pubB64 = "...paste data.publicKey...";   // from step 1

const publicKey = await crypto.subtle.importKey(
  "spki",
  b64ToBuf(pubB64),
  { name: "RSA-OAEP", hash: "SHA-256" },
  false,
  ["encrypt"]
);

const ct = await crypto.subtle.encrypt(
  { name: "RSA-OAEP" },
  publicKey,
  enc.encode("Test@123")          // your password
);

console.log(bufToB64(ct));        // -> base64 string for the `password` field
```

### 4b. Node.js (same logic)

```bash
node -e "
const { webcrypto } = require('node:crypto');
const enc = new TextEncoder();
const b64ToBuf = s => Uint8Array.from(Buffer.from(s, 'base64'));
const bufToB64 = b => Buffer.from(b).toString('base64');
(async () => {
  const pubB64 = process.argv[1] || '...paste data.publicKey...';
  const key = await webcrypto.subtle.importKey('spki', b64ToBuf(pubB64),
    { name: 'RSA-OAEP', hash: 'SHA-256' }, false, ['encrypt']);
  const ct = await webcrypto.subtle.encrypt({ name: 'RSA-OAEP' }, key, enc.encode('Test@123'));
  console.log(bufToB64(ct));
})();
" "...paste data.publicKey..."
```

### 4c. Postman - Pre-request Script (automatic, recommended)

Paste this into the **Pre-request Script** tab of your `POST /users/login` request. It fetches the
public key, encrypts the `password` variable, and sets the request body automatically:

```js
// PM: encrypt login password with the RSA public key (fetched on every run)
const pw = pm.variables.get("loginPassword") || "Test@123";  // set loginPassword as a collection var

const bufFromB64 = (s) => {
  const bin = atob(s);
  const bytes = new Uint8Array(bin.length);
  for (let i = 0; i < bin.length; i++) bytes[i] = bin.charCodeAt(i);
  return bytes;
};
const bufToB64 = (b) => {
  const bytes = new Uint8Array(b);
  let bin = "";
  bytes.forEach((x) => (bin += String.fromCharCode(x)));
  return btoa(bin);
};

const pubKeyReq = {
  url: "http://localhost:8086/emat/v1/auth/public-key",
  method: "GET",
};

pm.sendRequest(pubKeyReq, async (err, res) => {
  if (err) return console.error("public-key fetch failed", err);
  const { publicKey, hash } = res.json().data;
  const key = await crypto.subtle.importKey("spki", bufFromB64(publicKey),
    { name: "RSA-OAEP", hash }, false, ["encrypt"]);
  const ct = await crypto.subtle.encrypt({ name: "RSA-OAEP" }, key, new TextEncoder().encode(pw));
  pm.variables.set("encryptedPassword", bufToB64(ct));
});
```

Then use `{{encryptedPassword}}` in the body:

```json
{
  "username": "admin",
  "password": "{{encryptedPassword}}",
  "captchaId": "...",
  "captchaAnswer": "..."
}
```

> Postman pre-request scripts run in a Chromium webview - `crypto.subtle` is available there.

---

## 5. Step 3 - Login tests

### 5a. Happy path (correct credentials)

```
POST http://localhost:8086/emat/v1/users/login
Content-Type: application/json

{
  "username": "admin",
  "password": "<base64 RSA ciphertext from step 2>",
  "captchaId": "<from GET /captcha>",
  "captchaAnswer": "<from GET /captcha>"
}
```

Expected `200`:

```json
{
  "status": 200,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "expiresAt": "2026-09-16T08:30:00Z",
    "userId": "ENC:8mWQ2p...",
    "username": "admin",
    "email": "ENC:7QkM3p...",
    "firstName": "...",
    "lastName": "...",
    "role": "SIDBI_HO_MAKER",
    "active": true
  }
}
```

`userId` and `email` are **AES encrypted** (`ENC:...`) - decrypt them with the AES key from step 4.

### 5b. Wrong password (valid ciphertext)

Use the same encrypted password but a **different plain value** would decrypt to a wrong password.
Simplest: encrypt `"WrongPass@999"` in step 2 and send it with a valid `username`.

Expected `401`:

```json
{
  "status": 401,
  "message": "Bad credentials"
}
```

### 5c. Plain-text password (never encrypted) - must fail before auth

```
POST http://localhost:8086/emat/v1/users/login

{ "username": "admin", "password": "Test@123" }
```

Expected `400` (RSA decrypt fails in the deserializer):

```json
{
  "status": 400,
  "message": "Failed to decrypt login credential with the RSA private key"
}
```

### 5d. Missing / empty password

```
POST http://localhost:8086/emat/v1/users/login

{ "username": "admin", "password": "" }
```

Expected `400`:

```json
{
  "status": 400,
  "message": "Encrypted login credential must not be blank; encrypt the password with the public key from GET /auth/public-key first"
}
```

### 5e. Stale key (ciphertext from a different public key)

Simulate by encrypting with a public key from a different server/instance (or with an earlier
ephemeral key), then submitting to login:

Expected `400`:
```json
{
  "status": 400,
  "title": "Bad Request",
  "detail": "Failed to decrypt the login password: it was encrypted with a mismatched public key. Re-fetch GET /auth/public-key and encrypt the password again before submitting login."
}
```

Fix = re-fetch `/auth/public-key` and re-encrypt. With the local `key-file` configured this should
not happen on normal restarts.

---

## 6. Step 4 - Get the AES key (after login) and verify the rest still works

```
GET http://localhost:8086/emat/v1/pii-encryption-key
Authorization: Bearer <token-from-login>
```

Expected `200`:

```json
{
  "status": 200,
  "message": "Vault AppRole credentials validated; PII encryption key retrieved successfully",
  "data": {
    "secretKey": "7fK9vQ2mX8rT4zLp6Nw3Yc1Hs5Ua0EeJ9",
    "enabled": true
  }
}
```

Then all other endpoints use the **AES** `ENC:` contract from
[`ENCRYPTION_TESTING_GUIDE.md`](./ENCRYPTION_TESTING_GUIDE.md) (e.g. create user, create vendor,
etc.).

---

## 7. Full cURL walk-through (copy-paste)

```bash
BASE=http://localhost:8086/emat/v1

# 1. public key
curl -s $BASE/auth/public-key

# 2. (in JS/browser) encrypt "Test@123" -> $ENC_PW

# 3. login (adjust captcha)
curl -s -X POST $BASE/users/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"'$ENC_PW'","captchaId":"...","captchaAnswer":"..."}'
```

---

## 8. Key points for the frontend team

- **Fetch `/auth/public-key` before every login attempt** (keys can rotate / regenerate).
- Login `password` = **standard base64** RSA-OAEP/SHA-256 ciphertext. **No** `ENC:` prefix.
- After login, everything (including `password` on *create/change user*, and `userId`/`email` in the
  login response) is **AES** `ENC:...` using the key from `/pii-encryption-key`.
- Wrong credentials still come back as `401`, exactly like before encryption.
- Sending plain text to login `password` is impossible now - it is rejected with `400`.

---

## 9. Troubleshooting

| Symptom | Likely cause / fix |
|---|---|
| `400 Failed to decrypt the login password: ... mismatched public key ...` | Ciphertext was made with a different public key (stale/other instance). Re-fetch `/auth/public-key` and re-encrypt, then retry. |
| `400 Failed to decrypt login credential...` | Sent plain text, or invalid base64 / corrupt payload. Re-fetch the public key and re-encrypt. |
| `400 Encrypted login credential must not be blank` | `password` null/empty. Encrypt the value first. |
| `401 Bad credentials` | Ciphertext decrypted fine but username/password wrong (BCrypt). Check the actual login credentials. |
| `401` on `/pii-encryption-key` after login | Missing/invalid `Authorization: Bearer <token>`; or the app's Vault AppRole can't read the key. |
| `404` on `/auth/public-key` | Wrong base path - remember the `/emat/v1` context path. |
| Login works one time then fails after restart | Old behavior pre-key-file (ephemeral key). With `.emat/login-rsa-private.key` (local) or a Vault key, the key is stable; delete the file / re-provision and restart if it recurs. |
| Postman pre-request script fails on `importKey` | Proxy/secure-context issue; the Postman sandbox needs `crypto.subtle` - use a localhost base URL or the browser/Node helper instead. |

---

## 10. Testing checklist

- [ ] `GET /auth/public-key` returns `200` with `publicKey` - accessible **without** JWT
- [ ] Encrypting a password with the public key produces base64 (works in browser + Node + Postman)
- [ ] `POST /users/login` with **correct** credentials + encrypted password → `200` + JWT
- [ ] `POST /users/login` with **wrong** password (encrypted) → `401 Bad credentials`
- [ ] `POST /users/login` with **plain** password → `400`
- [ ] `POST /users/login` with **empty** password → `400`
- [ ] After login, `GET /pii-encryption-key` with JWT returns the AES key
- [ ] Login response `userId`/`email` decrypt correctly with the AES key (`ENC:...` prefix)
- [ ] Restart app → login still works (local `key-file` keeps the same RSA key); encrypting with a mismatched public key gives a clear `400` mismatch message