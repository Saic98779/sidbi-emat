# PII Data Protection

This document describes how Personally Identifiable Information (PII) is protected while in
transit between the EMAT frontend and backend, and what frontend engineers need to do to
integrate with it.

## What is treated as PII

- Email addresses
- Mobile / contact numbers
- OTPs (one-time passwords, when implemented)
- User credentials (passwords)
- Backend table identifiers (`Long` primary / foreign keys such as `id`, `userId`,
  `registrationId`, `sidbeApprovedByUserId`)

Business identifier strings (GSTIN, PAN, bank account number, IFSC, internal account codes) are
**not** encrypted - they are transmitted as plain text. Only the categories listed above are
encrypted at the payload level.

## Layer 1 (primary control): Transport encryption (TLS/HTTPS)

All traffic between the frontend and the backend **must** be served over HTTPS. This is the
primary, non-negotiable control for protecting PII and everything else in transit.

- `SecurityConfig` now enforces HTTPS via `security.require-ssl` (defaults to `false` for local
  development, **must be `true`** in staging/production). When enabled, all HTTP requests are
  redirected/rejected in favor of HTTPS (`http.requiresChannel().anyRequest().requiresSecure()`).
- HTTP Strict Transport Security (HSTS) is enabled on all responses
  (`includeSubDomains`, `preload`, 1 year max-age) to prevent protocol downgrade attacks.
- TLS termination can happen at this application (via `server.ssl.*` properties) or at an
  upstream load balancer/reverse proxy - either way, `security.require-ssl=true` must be set in
  any environment reachable outside a trusted private network.

## Layer 2 (defense in depth): Field-level payload encryption

Because TLS is sometimes terminated upstream (load balancer, API gateway, CDN) before traffic
reaches this application, and to guard against accidental logging/caching/inspection of request
and response bodies, sensitive fields are **additionally encrypted at the JSON payload level**
using AES-256-GCM authenticated encryption.

### Components

| Class | Purpose |
|---|---|
| `org.emat.util.PiiEncryptionService` | Core encrypt/decrypt logic (AES-256-GCM). Output uses URL-safe base64 (no padding). Also exposes `encryptId`/`decryptId` for `Long` identifiers. |
| `org.emat.dto.serializer.PiiStringEncryptSerializer` | Encrypts a `String` PII field (email, mobile, password) on the way out (response). |
| `org.emat.dto.serializer.PiiStringDecryptDeserializer` | Decrypts a `String` PII field on the way in (request). |
| `org.emat.dto.serializer.PiiIdEncryptSerializer` | Encrypts a `Long` backend identifier on the way out (response). |
| `org.emat.dto.serializer.PiiIdDecryptDeserializer` | Decrypts a `Long` backend identifier on the way in (request). |
| `org.emat.config.EncryptedIdConverter` | Registered with Spring so encrypted identifiers in REST URLs (`@PathVariable`) / query strings (`@RequestParam`) are decrypted transparently. |

### How it works

- Values are encrypted with AES-256-GCM using a 256-bit secret key configured via
  `pii.encryption.secret-key` (see `application.properties`). The output format is
  `ENC:<url-safe-base64(iv + ciphertext) without padding>` for both `String` PII and `Long`
  identifiers. The URL-safe encoding keeps the payloads safe in any transport context (JSON
  bodies, URL path segments, query strings, logs).
- Encryption/decryption is applied transparently by Jackson during JSON (de)serialization -
  controllers and services continue to work with plain Java `String`/`Long` values; only the
  wire format changes.
- Because `EncryptedIdConverter` is registered globally, encrypted identifiers returned in JSON
  payloads can be passed straight back in REST URLs (`@PathVariable`) or query strings
  (`@RequestParam`) without extra frontend work beyond using the same encryption helper.
- **Backward-compatible by design**: if an inbound value does not carry the `ENC:` marker, it is
  treated as plain text (with a decrypt no-op). This allows a phased rollout - existing/legacy
  clients keep working while new clients adopt the encrypted contract, and Postman/manual testing
  during development remains simple.
- Field encryption can be globally toggled off with `pii.field-encryption.enabled=false` (should
  only ever be used for local debugging).

### Fields currently protected

Enforced via annotations on the DTOs (requests decrypt on the way in, responses encrypt on the way
out). `Long` identifiers are encrypted via the `PiiId*` serializers/deserializers; string PII
(email, mobile, password) via the `PiiString*` ones.

**String PII:**
- Auth/users: `LoginRequest.password`; `LoginResponse.email`; `CreateUserRequest.password`,
  `email`, `contactNo`; `UserResponse.email`, `contactNo`
- Vendor: `VendorRequestDTO.email`/`spocMobileNo`/`mobileNo` (decrypt); `VendorResponseDTO`
  same fields (encrypt)
- Industry Association Registration: apex holder/nodal `email` and `mobile` fields, `email`
  on both create/update requests; response encrypts the same string fields
- Industry Association Appraisal: apex holder/nodal `email` and `mobile` fields on create/update
  requests; response encrypts the same string fields
- BSE Recommendation: `mobileNumber`, `emailId` on create/update requests; response encrypts the
  same string fields
- `SecretariatStaffDto.contact`, `email` (encrypted in responses and decrypted in requests)
- ROs/Branches: `contactNo` on `RegionalOfficeRequest`, `UpdateRegionalOfficeRequest`,
  `UpdateBranchRequest`; `RegionalOfficeResponse.contactNo`
- SDE: `UpdateSidbiSdeRequest.email`, `mobileNo`

**Backend identifiers (`Long`, via `PiiId*`):**
- Auth/users: `UserResponse.id` (encrypt); `LoginResponse.userId` (encrypt)
- Vendor: `VendorResponseDTO.id` and `VendorDropdownDTO.id` (encrypt)
- Industry Association Registration: `sidbeApprovedByUserId`, `stageId` on
  `CreateIndustryAssociationRegistrationRequest` (decrypt); `stageId` on
  `UpdateIndustryAssociationRegistrationRequest` (decrypt); `id` (encrypt) and
  `sidbeApprovedByUserId` (encrypt) on the response
- Industry Association Appraisal: `registrationId`, `stageId` on
  `CreateIndustryAssociationAppraisalRequest` (decrypt); `stageId` on
  `UpdateIndustryAssociationAppraisalRequest` (decrypt); `id`, `registrationId`,
  `sidbeApprovedByUserId` on the response (encrypt)
- BSE Recommendation: `registrationId`, `userId` on `CreateBseRecommendationRequest` (decrypt);
  `userId` on `UpdateBseRecommendationRequest` (decrypt); `id`, `registrationId`, `userId` on the
  response (encrypt)
- ROs: `RegionalOfficeResponse.id` (encrypt); `BranchDropdownResponse.id` (encrypt);
  `RegistrationDropdownDto.id`, `AppraisalDropdownDto.id`, `SidbiSdeDropdownResponse.id`
  (encrypt)
- Financial: `id`/`registrationId` on `DisbursementCapexRequest` (decrypt) and
  `DisbursementCapexResponse` (encrypt); `id` on `BseSalaryResponse` (encrypt);
  `bseId` on `MonthlySalaryDetailsRequest` (decrypt); `id` on `MonthlySalaryDetailsResponse`
  (encrypt)

GSTIN, PAN, bank account number, IFSC and internal account codes (`gstNo`, `gstinOfAgency`,
`gstinOfSdbi`, `gstinIa`, `gstinSidbi`, `panNo`, `accountNumber`, `ifscCode`, `accountCode`) are
**not** encrypted and are transmitted as plain text.

Additional DTOs can be protected the same way: annotate the field with
`@JsonSerialize(using = PiiStringEncryptSerializer.class)` / `@JsonDeserialize(using =
PiiStringDecryptDeserializer.class)` for string PII, or the `PiiId*` variants for `Long`
identifiers.

### Frontend integration

The frontend must be provisioned with the same AES-256 secret key (out-of-band, via secure build
configuration / secrets manager - never hard-coded in a public bundle). For the exact wire format,
the field-by-field list with request/response examples, and a JavaScript (Web Crypto)
implementation, see [`FE_BE_ENCRYPTION_CONTRACT.md`](./FE_BE_ENCRYPTION_CONTRACT.md).

In short:

1. Encrypt outgoing values for protected fields using AES-256-GCM before sending them in the
   request body, producing the `ENC:<url-safe-base64(iv + ciphertext)>` format (12-byte IV,
   128-bit GCM tag, URL-safe base64 without padding).
2. Decrypt any field it receives from the API that carries the `ENC:` prefix using the same key.
3. Never persist decrypted PII in browser storage (`localStorage`/`sessionStorage`) longer than
   necessary; keep it in memory only.

## Key management

- `pii.encryption.secret-key` (and `jwt.secret`) must be sourced from environment
  variables/secrets manager in every non-local environment - never committed to source control
  with real values. Rotate keys periodically and on suspected compromise.
- Passwords are never returned in any API response (see `UserResponse`, `LoginResponse`) and are
  stored using one-way `BCryptPasswordEncoder` hashing - they are only *encrypted in transit* on
  the way in (login/create user), never decrypted for storage or display.

## OTPs

The application does not currently implement OTP-based flows. If/when OTP functionality is
added, OTP values must:

- Never be logged.
- Be transmitted using the same `Pii*Serializer`/`Deserializer` pattern described above.
- Be short-lived and single-use, hashed at rest (not stored in plain text), similar to passwords.

## Logging guidance

Do not log raw PII values (email, mobile, password, OTP, decrypted identifiers). If logging is
required for troubleshooting, mask the value (e.g. `j***@example.com`, `98******10`) before
writing to logs.

