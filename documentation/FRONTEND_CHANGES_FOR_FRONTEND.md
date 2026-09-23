# Frontend Changes — Encrypt/Decrypt Contract Update

> What changed in the backend for the frontend team. Built from the uncommitted diff on `prod`.
> Related docs: `FE_BE_ENCRYPTION_CONTRACT.md`, `ENCRYPTION_TESTING_GUIDE.md`, `PII_DATA_PROTECTION.md`.

## 1. Summary of behavioural changes (breaking)

| # | Change | Impact on FE |
|---|--------|--------------|
| 1 | **Encryption of `Long` ids is now STRICT.** | In JSON bodies, URL paths and query strings, every identifier must be sent as `ENC:<base64url>` produced by the FE encryption helper. Sending a **plain number → HTTP 400 Bad Request**. Previously plain numbers were accepted. |
| 2 | **More response DTOs now encrypt their `Long` ids.** | Any encrypted id received in a response must be passed back to the BE **as-is** (no manual decrypt needed for lookups). |
| 3 | **More request DTOs now decrypt `Long` ids.** | When sending ids in request bodies, FE must encrypt them first (same `ENC:` format). |
| 4 | **`stageId` is NO LONGER encrypted anywhere.** | `stageId` in **all** DTOs (Industry-Association Create/Update, `ApprovalRequest`, `SustainabilityMatrixRequest`, `EligibilityMatrixDto`) is sent as a **plain number**. Do NOT encrypt `stageId`. |
| 5 | **Stage-related ids (`StageResponse`, `StageHistoryResponse`) are plain.** | `StageResponse.id`, `StageHistoryResponse.id`, `StageHistoryResponse.registrationId` are **not encrypted** — send/receive as plain numbers. |
| 6 | **New input validation on all JSON string fields.** | Any request-body `String` containing `<`, `>`, `/` or `\` is **rejected with 400** (never sanitized). Ensure FE does not send these characters, and that encrypted values (base64url) are safe by construction. |
| 7 | **Better error handling.** | Malformed/unreadable JSON bodies now return a clear `400` ProblemDetail (`Malformed request body: ...`) instead of a generic failure. |

### How to encrypt (FE) — reference
`ENC:` + Base64url(no padding) of `IV (12 bytes) || AES-256-GCM ciphertext`.
```js
// reference shape (parity with backend PiiEncryptionService)
const iv = crypto.getRandomValues(new Uint8Array(12));
const out = AES256GCM.encrypt(plainUtf8, key, iv); // key from /pii-encryption-key endpoint
const buf = concat(iv, out);                        // IV || ciphertext || tag
const value = "ENC:" + base64url(buf);              // URL-safe, no padding
```

## 2. Encrypt / Decrypt field matrix (from this diff)

Legend:
- **Encrypted (response):** field comes back **encrypted** (`ENC:` string).
- **Encrypt (request):** FE must send the field **encrypted** (`ENC:` string); BE decrypts it.
- **Plain:** field is a normal value; do NOT encrypt.

### Activity module
| DTO | Direction | Fields | FE action |
|-----|-----------|--------|-----------|
| `ActivityResponse` | Response | `activityId`, `followUpId`, `createdUserId`, `bseId`, `gtId` | **Encrypted** on output · use as-is in requests/URLs |
| `ActivityRequest` | Request | `followUpId`, `createdUserId`, `bseId`, `gtId` | **Encrypt** before send → BE decrypts |
| `ActivityStatusResponse` | Response | `statusId`, `activityId`, `followupActivityId` | **Encrypted** on output |
| `ActivityStatusUpdateResponse` | Response | `statusId`, `activityId`, `followupActivityId` | **Encrypted** on output |

> `ActivityRequest` fields `activityType`, `details`, `dateTime`, `status`, `followUpReq`, `locationDetails`, timestamps stay **plain**.

### BSE Attendance module
| DTO | Direction | Fields | FE action |
|-----|-----------|--------|-----------|
| `BseAttendanceDTO` | Both | `id`, `bseRecommendationId` | **Encrypted** on output · **encrypt** on input (added `@JsonDeserialize` this diff) |
| `BseAttendanceManualRequestDTO` | Request | `id`, `bseRecommendationId`, `approvedBy` | **Encrypt** before send — **`approvedBy` newly added** in this diff |
| `ApprovalRequest` | Request | `stageId` | **Plain** (not encrypted) |
| `ApprovalRequest` | Request | `isSidbeApproved`, `stageComments` | **Plain** (unchanged) |

### Stage module — all plain
| DTO | Direction | Fields | FE action |
|-----|-----------|--------|-----------|
| `StageResponse` | Response | `id` | **Plain** (not encrypted) |
| `StageHistoryResponse` | Response | `id`, `registrationId` | **Plain** (not encrypted) |
| `StageHistoryResponse` | Response | `stage`, `subStage`, `time`, `createdBy`, `comment` | **Plain** |

> `stageId` is **plain** in every DTO it appears in: `ApprovalRequest`, `CreateIndustryAssociationAppraisalRequest`, `UpdateIndustryAssociationAppraisalRequest`, `CreateIndustryAssociationRegistrationRequest`, `UpdateIndustryAssociationRegistrationRequest`, `SustainabilityMatrixRequest`, `EligibilityMatrixDto`.

### File upload
| DTO | Direction | Fields | FE action |
|-----|-----------|--------|-----------|
| `UploadedFileResponse` | Response | `id`, `registrationId`, `stageId` | **Encrypted** on output · use as-is in requests/URLs |
| `UploadedFileResponse` | Response | `filename`, `contentType`, `size`, `downloadUrl`, `createdAt` | **Plain** |
| `POST /files`, `POST /files/batch`, `GET /files`, `GET|DELETE /files/{filename}` | Request (query params) | `registrationId`, `stageId` | **Encrypt** before send (optional for `registrationId`) · plain numbers rejected |

> `downloadUrl` already embeds encrypted `registrationId`/`stageId` — pass it through as-is.

### Industry Association — request IDs
| DTO | Direction | Fields | FE action |
|-----|-----------|--------|-----------|
| `CreateIndustryAssociationAppraisalRequest` | Request | `stageId` | **Plain** (not encrypted) |
| `UpdateIndustryAssociationAppraisalRequest` | Request | `stageId` | **Plain** (not encrypted) |
| `CreateIndustryAssociationRegistrationRequest` | Request | `stageId` | **Plain** (not encrypted) |
| `UpdateIndustryAssociationRegistrationRequest` | Request | `stageId` | **Plain** (not encrypted) |
| `SustainabilityMatrixRequest` | Request | `stageId` | **Plain** (not encrypted) |
| `EligibilityMatrixDto` | Request | `stageId` | **Plain** (not encrypted) |

> String PII fields (`email`, `mobile`, `password`, etc.) remain encrypted as before — only `stageId` and stage-related ids are plain.

## 3. Global rules the FE must follow now

1. **Never send a plain number for a `Long` id — EXCEPT `stageId` in DTO bodies and Stage module ids.** For every other id (`activityId`, `bseId`, `gtId`, `followUpId`, `createdUserId`, `statusId`, `followupActivityId`, `id` in most responses), FE must send the `ENC:` string. Plain ids → `400`.
2. **`stageId` in DTO bodies is plain, but `registrationId`/`stageId` on the file APIs (`/files` query params) MUST be encrypted.** `StageResponse.id` / `StageHistoryResponse.id` / `StageHistoryResponse.registrationId` are plain numbers.
3. **Pass encrypted ids back verbatim.** `GET /emat/v1/.../ENC:xxxx` — the `ENC:` string goes straight into the URL; there is no need to decrypt on the FE.
4. **Encrypt request-body id fields** (except stage-related) using the FE helper before POST/PUT (format above).
5. **No `<`, `>`, `/`, `\` in any request-body string.** This includes business text. Invalid → 400 with a field path in the error message.
6. **String PII (email, mobile, password):** FE may still send **plain** OR **encrypted** (BE decrypts both, backward compatible). Responses are always encrypted.
7. **Handle 400 ProblemDetail responses** for ciphertext/validation errors (`detail` contains the reason).

## 4. Files changed in this diff (backend reference)

```
Modified:
  config/EncryptedIdConverter.java            -> strict: plain numbers rejected
  config/EncryptedIdResolver.java             -> strict: plain numbers rejected
  dto/serializer/PiiIdDecryptDeserializer.java -> strict: plain ids rejected
  util/PiiEncryptionService.java              -> decryptId() strict mode; masked error msgs
  exception/GlobalExceptionHandler.java       -> + HttpMessageNotReadableException (400)
  dto/StageResponse.java                      -> removed encryption on id
  dto/StageHistoryResponse.java               -> removed encryption on id, registrationId
  dto/ActivityRequest.java                    -> added encryption on followUpId, createdUserId
  dto/ActivityResponse.java                   -> added encryption on activityId, followUpId, createdUserId, bseId, gtId
  dto/ActivityStatusResponse.java             -> added encryption on statusId, activityId, followupActivityId
  dto/ActivityStatusUpdateResponse.java       -> added encryption on statusId, activityId, followupActivityId
  dto/BseAttendanceDTO.java                   -> added @JsonDeserialize on id, bseRecommendationId
  dto/BseAttendanceManualRequestDTO.java      -> added encryption on approvedBy
  dto/UploadedFileResponse.java               -> added encryption on id
  dto/CreateIndustryAssociationAppraisalRequest.java   -> removed encryption on stageId
  dto/UpdateIndustryAssociationAppraisalRequest.java   -> removed encryption on stageId
  dto/CreateIndustryAssociationRegistrationRequest.java -> removed encryption on stageId
  dto/UpdateIndustryAssociationRegistrationRequest.java -> removed encryption on stageId
  documentation/FE_BE_ENCRYPTION_CONTRACT.md   -> updated strict-mode docs
  documentation/ENCRYPTION_TESTING_GUIDE.md    -> updated strict-mode docs
  documentation/PII_DATA_PROTECTION.md         -> updated strict-mode docs

Added (new):
  config/InputValidationConfig.java            -> global safe-string deserializer registration
  validation/SafeInputValidator.java           -> rejects <, >, /, \
  validation/SafeInputStringDeserializer.java  -> wraps StringDeserializer with validation
  validation/InvalidInputCharacterException.java -> 400 error with field path
```
