# EMAT Field-Encryption Contract: Frontend <-> Backend

This is the practical guide for how the EMAT frontend must send and receive encrypted field
values, and what the backend does with them. It complements
[`PII_DATA_PROTECTION.md`](./PII_DATA_PROTECTION.md).

## 1. Scope of encryption

Two things are encrypted on the wire:

1. **String PII fields** - email addresses, mobile/contact numbers and user credentials
   (passwords).
2. **Backend table identifiers** (`Long` primary/foreign keys such as `id`, `userId`,
   `registrationId`, `stageId`, `sidbeApprovedByUserId`, `bseId`).

Business identifier **strings** (GSTIN, PAN, bank account number, IFSC, internal account codes)
are **not** encrypted and are passed as plain text: `gstNo`, `panNo`, `accountNumber`, `ifscCode`,
`gstinOfAgency`, `gstinOfSdbi`, `gstinIa`, `gstinSidbi`, `accountCode`.

| Direction | Backend behaviour |
|---|---|
| Request (FE -> BE) | Encrypted string fields and encrypted `Long` identifiers are **decrypted** by Jackson deserializers before reaching services. Plain values are also accepted (see §6). |
| Response (BE -> FE) | String PII fields and `Long` identifiers that carry the encryption marker are **encrypted** by Jackson serializers before leaving the backend. |
| URL path / query params | Encrypted identifiers returned in JSON are accepted back in URLs (`@PathVariable`) and query strings (`@RequestParam`) - `EncryptedIdConverter` decrypts them transparently. |

### Fields currently encrypted

String PII:

- User/auth: `password`, `email`, `contactNo`
- Vendor: `email`, `mobileNo`, `spocMobileNo`
- Industry Association Registration/Appraisal: `apexHolderEmail`, `apexHolderMobile`,
  `nodalEmail`, `nodalMobile`, `email`
- BSE Recommendation: `emailId`, `mobileNumber`
- Secretariat staff: `email`, `contact`
- Regional office / branch / SDE: `contactNo`, `email`, `mobileNo`

Backend identifiers (`Long`, serialized as encrypted strings):

- Auth/users: `UserResponse.id`, `LoginResponse.userId`
- Vendor: `VendorResponseDTO.id`, `VendorDropdownDTO.id`
- Industry Association Registration: request `sidbeApprovedByUserId`, `stageId`; response `id`,
  `sidbeApprovedByUserId`
- Industry Association Appraisal: request `registrationId`, `stageId`; response `id`,
  `registrationId`, `sidbeApprovedByUserId`
- BSE Recommendation: request `registrationId`, `userId`; response `id`, `registrationId`, `userId`
- RO/branch/SDE dropdowns: `id` on `RegionalOfficeResponse`, `BranchDropdownResponse`,
  `RegistrationDropdownDto`, `AppraisalDropdownDto`, `SidbiSdeDropdownResponse`
- Financial: `DisbursementCapexRequest.id`/`registrationId`, `DisbursementCapexResponse.id`/`registrationId`,
  `BseSalaryResponse.id`, `MonthlySalaryDetailsRequest.bseId`, `MonthlySalaryDetailsResponse.id`

## 2. Wire format

Every encrypted value is a single string with a fixed marker:

```
ENC:<base64url(iv || ciphertext || GCM-tag)>
```

- Algorithm: **AES-256-GCM**
- Key: the raw bytes of `pii.encryption.secret-key` (must be exactly 16, 24 or 32 ASCII
  characters => AES-128/192/256). The key is shared out-of-band with the frontend via a secrets
  manager / secure build configuration. Do not hard-code it in a public bundle.
- IV: 12 random bytes generated per encryption, **prepended** to the ciphertext
- GCM authentication tag: 128 bits (16 bytes), appended to the ciphertext by the cipher
- Encoding: **base64url, no padding** (`-` and `_` instead of `+` and `/`; padding `=` stripped)
- Message = `ENC:` + that encoded string

## 3. Request flow (frontend -> backend)

Example payload for creating a user:

```json
{
  "username": "jdoe",
  "password": "ENC:Y2FsbG...",
  "email": "ENC:6sx5wL...",
  "contactNo": "ENC:gkP2m...",
  "firstName": "Jane",
  "role": "USER"
}
```

Example payload that includes encrypted identifiers (creating a BSE recommendation):

```json
{
  "registrationId": "ENC:Cb3A8x...",
  "userId": "ENC:W7pQ2n...",
  "mobileNumber": "ENC:gkP2m...",
  "emailId": "ENC:6sx5wL..."
}
```

Note: identifier fields are typed `Long` in the backend, but on the wire they are **strings**
(`"ENC:..."`). Encrypt the numeric id the same way you encrypt a string (see `encryptPii(..., key)`
below with the id converted to a string).

Backend processing on the way in:

1. Jackson deserializes the body. String fields annotated with
   `@JsonDeserialize(using = PiiStringDecryptDeserializer.class)` and `Long` fields annotated with
   `@JsonDeserialize(using = PiiIdDecryptDeserializer.class)` are decrypted via
   `PiiEncryptionService.decrypt`/`decryptId`.
2. `decrypt()` checks the `ENC:` prefix. If present it base64url-decodes, splits off the first 12
   bytes as the IV, AES-256-GCM decrypts and returns the plain value. If the marker is absent the
   value is returned unchanged (backward-compatible no-op).
3. The controller/service receives the **plain** value. Uniqueness checks, BCrypt hashing,
   validation (`@Email`, `@NotBlank`), DB lookups on identifiers and persistence all run on the
   plain value.
4. The database stores plain text (encryption is a transport-only, defense-in-depth layer).

## 4. Response flow (backend -> frontend)

Example login response:

```json
{
  "token": "eyJhbGciOi...",
  "expiresAt": "2026-09-14T20:30:00Z",
  "userId": "ENC:7QkM3p...",
  "username": "jdoe",
  "email": "ENC:7QkM3p..."
}
```

Notice both `userId` (a `Long` id) and `email` are returned as encrypted strings.

Backend processing on the way out:

1. Services build response DTOs with plain values.
2. Jackson serializes the body. String fields annotated with
   `@JsonSerialize(using = PiiStringEncryptSerializer.class)` and `Long` fields annotated with
   `@JsonSerialize(using = PiiIdEncryptSerializer.class)` are encrypted via
   `PiiEncryptionService.encrypt`/`encryptId`.
3. `encrypt()` skips `null` (writes `null`) and produces `ENC:<...>` for non-null values.
4. The frontend must decrypt such values before displaying them or before passing them back in a
   URL/query string (`encryptId` output uses the same URL-safe encoding, so it is safe in URLs).

## 5. Reference implementations

### JavaScript (Web Crypto API)

The key string is 32 ASCII characters (256-bit key).

```js
const PREFIX = "ENC:";
const encoder = new TextEncoder();
const decoder = new TextDecoder();

const toBase64Url = (bytes) =>
  btoa(String.fromCharCode(...bytes))
    .replace(/\+/g, "-")
    .replace(/\//g, "_")
    .replace(/=+$/, "");

const fromBase64Url = (str) =>
  Uint8Array.from(
    atob(str.replace(/-/g, "+").replace(/_/g, "/")),
    (c) => c.charCodeAt(0)
  );

async function importKey(secretKey) {
  return crypto.subtle.importKey(
    "raw",
    encoder.encode(secretKey),
    { name: "AES-GCM" },
    false,
    ["encrypt", "decrypt"]
  );
}

/** Encrypt a plain string for a protected field (request body). */
async function encryptPii(plain, secretKey) {
  if (plain === null || plain === undefined || plain === "") return plain;
  const iv = crypto.getRandomValues(new Uint8Array(12));
  const key = await importKey(secretKey);
  const ciphertext = new Uint8Array(
    await crypto.subtle.encrypt({ name: "AES-GCM", iv, tagLength: 128 }, key, encoder.encode(plain))
  );
  const payload = new Uint8Array(iv.length + ciphertext.length);
  payload.set(iv, 0);
  payload.set(ciphertext, iv.length);
  return PREFIX + toBase64Url(payload);
}

/** Decrypt a value received from the API. Plain values pass through unchanged. */
async function decryptPii(value, secretKey) {
  if (!value || !value.startsWith(PREFIX)) return value;
  const raw = fromBase64Url(value.slice(PREFIX.length));
  const iv = raw.slice(0, 12);
  const ciphertext = raw.slice(12);
  const key = await importKey(secretKey);
  const plain = await crypto.subtle.decrypt(
    { name: "AES-GCM", iv, tagLength: 128 },
    key,
    ciphertext
  );
  return decoder.decode(plain);
}
```

Usage in a request:

```js
const body = {
  username: form.username,
  password: await encryptPii(form.password, SECRET_KEY),
  email: await encryptPii(form.email, SECRET_KEY),
  contactNo: await encryptPii(form.contactNo, SECRET_KEY),
};
await fetch("/emat/v1/users", {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify(body),
});
```

Usage when sending an identifier in a request body (encrypt the number as a string):

```js
const body = {
  registrationId: await encryptPii(String(bseForm.registrationId), SECRET_KEY),
  userId: await encryptPii(String(userId), SECRET_KEY),
  mobileNumber: await encryptPii(form.mobile, SECRET_KEY),
  emailId: await encryptPii(form.email, SECRET_KEY),
};
```

Usage when rendering a response (numbers come back as `ENC:` strings - decrypt then parse):

```js
const data = await res.json();
const id = Number(await decryptPii(data.userId, SECRET_KEY));
user.email = await decryptPii(data.email, SECRET_KEY);
```

Using an encrypted id in a URL: pass the `ENC:` string straight into the path or query string -
the encoding is URL-safe and the backend decrypts it automatically (`EncryptedIdConverter`):

```js
const res = await fetch(`/emat/v1/vendor/${vendor.id}`, ...); // vendor.id === "ENC:..."
```

If you prefer, the decrypted plain number also works since the converter accepts both forms.

### Java (reference for backend parity, useful to cross-check your implementation)

Encryption:

```java
byte[] iv = new byte[12];
SecureRandom.getInstanceStrong().nextBytes(iv);
Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"),
        new GCMParameterSpec(128, iv));
byte[] out = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));
ByteBuffer buf = ByteBuffer.allocate(iv.length + out.length);
buf.put(iv).put(out);
String value = "ENC:" + Base64.getUrlEncoder().withoutPadding().encodeToString(buf.array());
```

## 6. Backward compatibility (important for rollout)

- **Inbound:** if a client (or Postman) sends a *plain* value for a protected string field or a
  *plain number* for a `Long` identifier, the backend treats it as plain and uses it as-is. This
  means existing clients keep working while they migrate to the encrypted contract.
- **Outbound:** every response is *always* encrypted for the protected fields/identifiers, so new
  clients must handle the `ENC:` marker for display purposes.
- **URLs:** encrypted and plain identifier forms are both accepted in URL paths and query strings
  (`EncryptedIdConverter`), so existing links keep working.

## 7. Things to watch

- Never log raw `ENC:` payloads or the decrypted values; mask before logging.
- Do not persist decrypted PII in `localStorage`/`sessionStorage` longer than needed.
- Empty string or `null`: the backend encrypts/skips `null` only; an empty string in a request is
  decrypted as `ENC:<...>` of an empty string or passed through if plain. Match the behaviour by
  not encrypting empty/blank values unless you must.
- If the key changes, all previously issued encrypted payloads become undecryptable on the backend.
  Rotate carefully (persist old keys briefly or re-encrypt client caches).
- The backend supports AES-128/192/256 depending on key length; both sides must agree on the key
  bytes (ASCII) explicitly.

## 8. Quick reference table

| Question | Answer |
|---|---|
| What is encrypted? | String PII (emails, mobiles, passwords) and `Long` backend identifiers (PKs/FKs like `id`, `userId`, `registrationId`, `stageId`, `bseId`). |
| What is NOT encrypted? | GSTIN/PAN/account number/IFSC/account codes, usernames, all other business data. |
| Algorithm | AES-256-GCM (128-bit tag, 12-byte IV prepended). |
| Encoded as | `ENC:` + base64url (no padding), covering `IV \|\| ciphertext`. |
| Backend request handling | Decrypt on `@JsonDeserialize` fields (both `String` and `Long`); plain values accepted too. |
| Backend response handling | Encrypt on `@JsonSerialize` fields (both `String` and `Long`) before writing the response. |
| URLs / query params | Encrypted or plain ids both accepted (`EncryptedIdConverter` string->Long). |
| Database | Plain text (transport-level protection only). |