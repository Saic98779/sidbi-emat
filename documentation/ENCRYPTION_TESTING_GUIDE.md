# EMAT Encryption Testing Guide

How to test the EMAT backend **with field-level encryption enabled** from Postman (or cURL/Insomnia),
including how to generate encrypted values, what to expect back, and how to verify responses.

Complements:

- [`PII_DATA_PROTECTION.md`](./PII_DATA_PROTECTION.md) - what & why
- [`FE_BE_ENCRYPTION_CONTRACT.md`](./FE_BE_ENCRYPTION_CONTRACT.md) - the wire contract + full JS implementation

---

## 1. What is encrypted (reminder)

| Category | Fields | Wire format |
|---|---|---|
| Email | `email`, `emailId`, `apexHolderEmail`, `nodalEmail` | `ENC:...` |
| Mobile / contact | `contactNo`, `mobileNo`, `mobileNumber`, `spocMobileNo`, `apexHolderMobile`, `nodalMobile` | `ENC:...` |
| Credentials | `password` | `ENC:...` (requests only; never in responses) |
| Backend ids (`Long`) | `id`, `userId`, `registrationId`, `stageId`, `sidbeApprovedByUserId`, `bseId` | `ENC:...` (string, not number) |
| **NOT encrypted** | `gstNo`, `panNo`, `accountNumber`, `ifscCode`, `gstinOfAgency`, `gstinOfSdbi`, `gstinIa`, `gstinSidbi`, `accountCode`, usernames, all other business data | plain text |

## 2. Configuration

`src/main/resources/application.properties`:

```properties
server.port=8086
server.servlet.context-path=/emat/v1

# Secret key used by AES-256-GCM. Keep it >= 32 chars for AES-256.
pii.encryption.secret-key=EmatPiiEncryptionKey!2026Secure@

# true = encryption active. false = everything plain (local debug only).
pii.field-encryption.enabled=true
```

> The app also has the devtools hot-reload, so editing properties triggers a restart automatically
> in an IDE run. Otherwise restart `mvn spring-boot:run`.

## 3. Postman setup

1. Base URL for all requests: `http://localhost:8086/emat/v1`
2. **Login** first (public endpoint) and copy the JWT:

   `POST /emat/v1/users/login`
   ```json
   {
     "username": "admin",
     "password": "ENC:..."
   }
   ```
   `password` can also be sent **plain** during development (see §5).
3. For protected endpoints add the header:
   ```
   Authorization: Bearer <token-from-login>
   Content-Type: application/json
   ```

## 4. Generating encrypted values

### 4a. In your browser (JavaScript)

Save this as `encrypt-helper.html` and open it at `http://localhost:<any-port>` (WebCrypto requires
a secure context, and `localhost` qualifies). Open DevTools console:

```js
const KEY = "EmatPiiEncryptionKey!2026Secure@";
const enc = new TextEncoder();
const b64url = b => btoa(String.fromCharCode(...b)).replace(/\+/g, "-").replace(/\//g, "_").replace(/=+$/, "");

async function encPii(plain) {
  if (plain === null || plain === undefined || plain === "") return plain;
  const iv = crypto.getRandomValues(new Uint8Array(12));
  const k = await crypto.subtle.importKey("raw", enc.encode(KEY), { name: "AES-GCM" }, false, ["encrypt"]);
  const ct = new Uint8Array(await crypto.subtle.encrypt({ name: "AES-GCM", iv, tagLength: 128 }, k, enc.encode(String(plain))));
  const out = new Uint8Array(iv.length + ct.length);
  out.set(iv, 0); out.set(ct, iv.length);
  return "ENC:" + b64url(out);
}

async function decPii(value) {
  if (!value || !value.startsWith("ENC:")) return value;
  const dec = new TextDecoder();
  const atoburl = s => Uint8Array.from(atob(s.replace(/-/g, "+").replace(/_/g, "/")), c => c.charCodeAt(0));
  const raw = atoburl(value.slice(4));
  const k = await crypto.subtle.importKey("raw", enc.encode(KEY), { name: "AES-GCM" }, false, ["decrypt"]);
  return dec.decode(await crypto.subtle.decrypt({ name: "AES-GCM", tagLength: 128, iv: raw.slice(0, 12) }, k, raw.slice(12)));
}

await encPii("Test@123");     // -> "ENC:C3oYfw..."
await encPii(1042);           // -> "ENC:Q8zLm..." (id; number is converted to string)
await decPii("ENC:Q8zLm..."); // -> "1042"
```

> `String(id)` matters: a `Long` id is encrypted the same way as a string - `await encPii(vendor.id)`.

### 4b. Node.js (same code, run with a prompt)

```bash
node -e "global.crypto = require('node:crypto').webcrypto; ...paste the snippet..."
```

### 4c. Java (quick parity check)

```java
// Reuse org.emat.util.PiiEncryptionService in a @SpringBootTest, or replicate:
byte[] key = "EmatPiiEncryptionKey!2026Secure@".getBytes(StandardCharsets.UTF_8);
byte[] iv = new byte[12]; new SecureRandom().nextBytes(iv);
Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, iv));
ByteBuffer b = ByteBuffer.allocate(12 + c.getOutputSize(plainText.length()));
b.put(iv).put(c.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
System.out.println("ENC:" + Base64.getUrlEncoder().withoutPadding().encodeToString(b.array()));
```

## 5. Key behaviour to remember while testing

| You send… | Backend does… |
|---|---|
| `"ENC:...."` for a protected field | decrypts it |
| plain value for a protected field | uses it **as-is** (backward compatible; no error) |
| any value for a non-protected field | uses it as-is, never encrypted |
| plain number `42` in a URL for a `Long` id | works (converter parses it) |
| `"ENC:...."` in a URL for a `Long` id | works (converter decrypts it) |

**Responses are always encrypted** for protected fields and `Long` ids when `enabled=true`, regardless
of what you sent. To read them you must decrypt (or set `pii.field-encryption.enabled=false`).

### 5a. Full-payload encryption helper (/testing/encrypt)

The `/testing/encrypt` helper accepts **your full API request JSON directly** and returns it with all protected fields encrypted — no wrapping, no manual field selection, no separate `encrypt-payload` endpoint.

#### Single-value mode

Send `{"value":"..."} ` to encrypt a single field value. This is the same as before:

```
POST /testing/encrypt
Content-Type: application/json

{ "value": "9876543210" }
```

Response:
```json
{ "status": 200, "message": "Encrypted successfully", "data": { "value": "ENC:GGrUoPtU..." } }
```

#### Full-payload mode

Paste your **entire** request JSON directly — no wrapper needed:

```
POST /testing/encrypt
Content-Type: application/json
Authorization: Bearer <jwt>

{
  "state": "Telangana",
  "industryAssociationName": "Telangana Small Industries Association",
  "constitutionType": "Society",
  "panNo": "ABCDE1234F",
  "apexHolderName": "Rajesh Kumar",
  "apexHolderMobile": "9876543210",
  "apexHolderEmail": "president@tsia.example.com",
  "nodalMobile": "9876501234",
  "nodalEmail": "nodal@tsia.example.com",
  "email": "contact@tsia.example.com",
  "sidbeApprovedByUserId": 101,
  "stageId": 3,
  ...
}
```

Response (all protected fields are now `ENC:...`; business fields like `panNo` stay plain):
```json
{
  "status": 200,
  "message": "Payload encrypted",
  "data": {
    "dtoName": "CreateIndustryAssociationRegistrationRequest",
    "encrypted": {
      "state": "Telangana",
      "industryAssociationName": "Telangana Small Industries Association",
      "panNo": "ABCDE1234F",
      "apexHolderName": "Rajesh Kumar",
      "apexHolderMobile": "ENC:GGrUoPtU...",
      "apexHolderEmail": "ENC:6sx5wL...",
      "nodalMobile": "ENC:gkP2m...",
      "nodalEmail": "ENC:7QkM3p...",
      "email": "ENC:9xK2m...",
      "sidbeApprovedByUserId": "ENC:8mWQ2p...",
      "stageId": "ENC:5HtRg...",
      ...
    }
  }
}
```

The endpoint **auto-detects** the target DTO from the JSON fields. If you need to force a specific DTO (e.g., for ambiguous payloads), add a query parameter:

```
POST /testing/encrypt?dtoName=VendorRequestDTO
Content-Type: application/json

{ "vendorName": "Acme Corp", "email": "admin@acme.com", ... }
```

> **How auto-detection works:** The body is deserialised into every known request DTO; the one whose fields best match the supplied JSON is chosen. If the body clearly belongs to a specific DTO, pass `?dtoName=...` explicitly to avoid surprises.

**Copy the `encrypted` object from the response** and use it directly as the body of the real endpoint.

## 6. Walk-through test scenarios

### 6.1 Login (public)

```
POST http://localhost:8086/emat/v1/users/login
Body: { "username": "admin", "password": "ENC:C3oYfw..." }   // or plain "Test@123"
```

Expected response - **`userId` and `email` are encrypted strings**:

```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "expiresAt": "2026-09-14T21:00:00Z",
    "userId": "ENC:8mWQ2p...",
    "username": "admin",
    "email": "ENC:7QkM3p...",
    "role": "ADMIN"
  }
}
```

Use the `token` for all protected calls.

### 6.2 Create user (public)

```
POST http://localhost:8086/emat/v1/users
Body:
{
  "username": "jdoe",
  "password": "ENC:C3oYfw...",
  "email": "ENC:6sx5wL...",
  "contactNo": "ENC:gkP2m...",
  "firstName": "Jane",
  "lastName": "Doe",
  "role": "USER",
  "district": "Mumbai",
  "state": "Maharashtra"
}
```

Expected 200/201 response: `id`, `email`, `contactNo` encrypted.

### 6.3 Use an encrypted id end-to-end (proves the URL converter works)

1. Create a vendor (protected) - **business fields stay plain**:

```
POST http://localhost:8086/emat/v1/vendor
Authorization: Bearer <token>
Body:
{
  "vendorName": "Acme Corp",
  "spocName": "Ravi Kumar",
  "spocMobileNo": "ENC:gkP2m...",
  "email": "ENC:6sx5wL...",
  "mobileNo": "ENC:gkP2m...",
  "gstNo": "27AABCU9603R1ZM",
  "panNo": "ABCDE1234F",
  "accountNumber": "1234567890123456",
  "ifscCode": "SBIN0001234",
  "bankName": "SBI",
  "address": "123 Industrial Area",
  "district": "Pune",
  "state": "Maharashtra"
}
```

2. The response contains `"id": "ENC:W9nTqB..."` (encrypted id).

3. Fetch that vendor using **both** the encrypted id and the plain number - both work:

```
GET http://localhost:8086/emat/v1/vendor/ENC:W9nTqB...
GET http://localhost:8086/emat/v1/vendor/1042
```

### 6.4 Industry Association registration (ids inside the body)

```
POST http://localhost:8086/emat/v1/industry-association-registrations
Body (relevant fields):
{
  "state": "Maharashtra",
  "industryAssociationName": "Pune IA",
  "apexHolderName": "Suresh Mehta",
  "apexHolderMobile": "ENC:gkP2m...",
  "apexHolderEmail": "ENC:6sx5wL...",
  "nodalMobile": "ENC:gkP2m...",
  "nodalEmail": "ENC:6sx5wL...",
  "email": "ENC:6sx5wL...",
  "panNo": "ABCDE1234F",
  "sidbeApprovedByUserId": "ENC:8mWQ2p...",   // encrypted Long id
  "stageId": "ENC:5HtRg..."                    // encrypted Long id
}
```

### 6.5 BSE recommendation (encrypted ids in request)

```
POST http://localhost:8086/emat/v1/bse-recommendations
Body:
{
  "registrationId": "ENC:W9nTqB...",
  "userId": "ENC:8mWQ2p...",
  "mobileNumber": "ENC:gkP2m...",
  "emailId": "ENC:6sx5wL...",
  "bseName": "Rahul Verma",
  "state": "Maharashtra"
}
```

## 7. Verifying that responses are encrypted

For any GET, e.g. `GET http://localhost:8086/emat/v1/vendor/1042`:

| Field | You should see |
|---|---|
| `id` | `"ENC:..."` (string) |
| `email`, `mobileNo`, `spocMobileNo` | `"ENC:..."` |
| `gstNo`, `panNo`, `accountNumber`, `ifscCode` | plain text (`"ABCDE1234F"` etc.) |
| `vendorName`, `address` etc. | plain text |

To confirm a value decrypts correctly, run `decPii("ENC:...")` from the console helper and check it
matches the value you posted.

## 8. Troubleshooting

| Symptom | Likely cause / fix |
|---|---|
| `401 Unauthorized` on protected calls | Missing/invalid `Authorization: Bearer <token>` |
| `malformed payload` on a `ENC:` value | Value was clipped by Postman or the key used to generate it differs from `application.properties` |
| `Id starting with ENC not recognized`-style conversion errors | Wrong endpoint param type; the URL converter handles `Long` params only - check the controller signature |
| Login `password` rejected | Sending `ENC:` that decrypts fine but password is wrong, or sending plain while account password was set via a different value |
| Everything returns plain text | `pii.field-encryption.enabled=false` is set; encryption is effectively off |
| Response shows `ENC:` but garbage | Response was produced under a different key or truncated; regenerate and verify with `decPii` |

## 9. Testing checklist

- [ ] `pii.field-encryption.enabled=true` in `application.properties`
- [ ] `pii.encryption.secret-key` matches the key used in your encryption helper
- [ ] Login works with `ENC:` password (or plain)
- [ ] `userId`/`email` in the login response are `ENC:` strings
- [ ] Create vendor: business strings plain, PII strings encrypted, `id` encrypted
- [ ] `GET /vendor/{id}` works with both encrypted and plain id
- [ ] Decrypt a response field with `decPii` and it matches what you posted
- [ ] No `ENC:` values in logs (if you see them, don't paste full payloads in logs)