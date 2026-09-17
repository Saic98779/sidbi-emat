# PII Encryption / Decryption Fields - Frontend Reference

## Encryption Contract

- **Pre-login (AUTH):** Login is the first request — no JWT exists, so the AES key is NOT available yet. The `password` field of login must be encrypted with **RSA-OAEP/SHA-256** using a public key from an unauthenticated endpoint.
- **Post-login:** All other fields use **AES-256-GCM** with the AES key from `GET /pii-encryption-key`.
- **AES format:** `ENC:<url-safe-base64(iv + ciphertext + GCM-tag)>`
- **RSA format:** `base64(RSA-OAEP-SHA256 ciphertext)` — no `ENC:` prefix
- **Key endpoints:**
  - `GET /auth/public-key` — unauthenticated, returns RSA public key for login password
  - `GET /pii-encryption-key` — requires JWT, returns AES-256 key for everything else
- **Reference:** See `documentation/FE_BE_ENCRYPTION_CONTRACT.md` for JS Web Crypto implementation

---

## LOGIN FLOW (MUST DO FIRST)

1. Call `GET /auth/public-key` (no auth needed) → get `data.publicKey` (base64 SPKI)
2. Encrypt password: Web Crypto `crypto.subtle.encrypt({name: "RSA-OAEP"}, key, passwordBytes)` with SPKI-imported key, hash `SHA-256`
3. Send `password` as **standard base64** (no `ENC:` prefix) in `POST /users/login`
4. After login, call `GET /pii-encryption-key` with the JWT for the AES key (use for all other fields)

> Full step-by-step testing guide: `documentation/LOGIN_RSA_TESTING_GUIDE.md` (includes Postman pre-request script)

### 1. `LoginRequest` (request) — `password` uses RSA-OAEP/SHA-256
| Field | Type | Encryption |
|---|---|---|
| `password` | String | **RSA-OAEP** (base64 ciphertext, no `ENC:` prefix) |

---

## Request DTOs (Frontend Must Encrypt Before Sending — AES after JWT)

### 2. `CreateUserRequest`
| Field | Type | Encryption |
|---|---|---|
| `password` | String | PiiString (encrypt, AES after JWT) |
| `email` | String | PiiString (encrypt) |
| `contactNo` | String | PiiString (encrypt) |

### 3. `CreateBseRecommendationRequest`
| Field | Type | Encryption |
|---|---|---|
| `registrationId` | Long | PiiId (encrypt) |
| `userId` | Long | PiiId (encrypt) |
| `mobileNumber` | String | PiiString (encrypt) |
| `emailId` | String | PiiString (encrypt) |

### 4. `UpdateBseRecommendationRequest`
| Field | Type | Encryption |
|---|---|---|
| `userId` | Long | PiiId (encrypt) |
| `mobileNumber` | String | PiiString (encrypt) |
| `emailId` | String | PiiString (encrypt) |

### 5. `CreateIndustryAssociationRegistrationRequest`
| Field | Type | Encryption |
|---|---|---|
| `apexHolderMobile` | String | PiiString (encrypt) |
| `apexHolderEmail` | String | PiiString (encrypt) |
| `nodalMobile` | String | PiiString (encrypt) |
| `nodalEmail` | String | PiiString (encrypt) |
| `email` | String | PiiString (encrypt) |
| `sidbeApprovedByUserId` | Long | PiiId (encrypt) |
| `stageId` | Long | PiiId (encrypt) |

### 6. `UpdateIndustryAssociationRegistrationRequest`
| Field | Type | Encryption |
|---|---|---|
| `apexHolderMobile` | String | PiiString (encrypt) |
| `apexHolderEmail` | String | PiiString (encrypt) |
| `nodalMobile` | String | PiiString (encrypt) |
| `nodalEmail` | String | PiiString (encrypt) |
| `email` | String | PiiString (encrypt) |
| `stageId` | Long | PiiId (encrypt) |

### 7. `CreateIndustryAssociationAppraisalRequest`
| Field | Type | Encryption |
|---|---|---|
| `registrationId` | Long | PiiId (encrypt) |
| `stageId` | Long | PiiId (encrypt) |
| `apexHolderMobile` | String | PiiString (encrypt) |
| `apexHolderEmail` | String | PiiString (encrypt) |
| `nodalMobile` | String | PiiString (encrypt) |
| `nodalEmail` | String | PiiString (encrypt) |

### 8. `UpdateIndustryAssociationAppraisalRequest`
| Field | Type | Encryption |
|---|---|---|
| `stageId` | Long | PiiId (encrypt) |
| `apexHolderMobile` | String | PiiString (encrypt) |
| `apexHolderEmail` | String | PiiString (encrypt) |
| `nodalMobile` | String | PiiString (encrypt) |
| `nodalEmail` | String | PiiString (encrypt) |

### 9. `VendorRequestDTO`
| Field | Type | Encryption |
|---|---|---|
| `spocMobileNo` | String | PiiString (encrypt) |
| `email` | String | PiiString (encrypt) |
| `mobileNo` | String | PiiString (encrypt) |

### 10. `ActivityRequest`
| Field | Type | Encryption |
|---|---|---|
| `bseId` | Long | PiiId (encrypt) |
| `gtId` | Long | PiiId (encrypt) |

### 11. `ActivityStatusUpdateRequest`
| Field | Type | Encryption |
|---|---|---|
| `followupActivityId` | Long | PiiId (encrypt) |

### 12. `BseAttendanceManualRequestDTO`
| Field | Type | Encryption |
|---|---|---|
| `id` | Long | PiiId (encrypt) |
| `bseRecommendationId` | Long | PiiId (encrypt) |

### 13. `BseSalaryUpdateRequest`
| Field | Type | Encryption |
|---|---|---|
| `bseId` | String | PiiId (encrypt) |

### 14. `DisbursementCapexRequest`
| Field | Type | Encryption |
|---|---|---|
| `id` | Long | PiiId (encrypt) |
| `registrationId` | Long | PiiId (encrypt) |

### 15. `EligibilityMatrixDto`
| Field | Type | Encryption |
|---|---|---|
| `registrationId` | Long | PiiId (encrypt) |

### 16. `MonthlySalaryDetailsRequest`
| Field | Type | Encryption |
|---|---|---|
| `bseId` | Long | PiiId (encrypt) |

### 17. `MonthlySalaryDetailsUpdateRequest`
| Field | Type | Encryption |
|---|---|---|
| `iaId` | String | PiiId (encrypt) |
| `bseId` | String | PiiId (encrypt) |

### 18. `RegionalOfficeRequest`
| Field | Type | Encryption |
|---|---|---|
| `roId` | String | PiiId (encrypt) |
| `contactNo` | String | PiiString (encrypt) |

### 19. `UpdateRegionalOfficeRequest`
| Field | Type | Encryption |
|---|---|---|
| `contactNo` | String | PiiString (encrypt) |

### 20. `UpdateBranchRequest`
| Field | Type | Encryption |
|---|---|---|
| `contactNo` | String | PiiString (encrypt) |
| `regionalOfficeId` | String | PiiId (encrypt) |

### 21. `UpdateSidbiSdeRequest`
| Field | Type | Encryption |
|---|---|---|
| `email` | String | PiiString (encrypt) |
| `mobileNo` | String | PiiString (encrypt) |
| `regionalOfficeId` | String | PiiId (encrypt) |

### 22. `SustainabilityMatrixRequest`
| Field | Type | Encryption |
|---|---|---|
| `appraisalId` | Long | PiiId (encrypt) |

---

## Response DTOs (Frontend Must Decrypt When Receiving)

### 1. `LoginResponse`
| Field | Type | Decryption |
|---|---|---|
| `userId` | Long | PiiId (decrypt) |
| `email` | String | PiiString (decrypt) |

### 2. `UserResponse`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |
| `email` | String | PiiString (decrypt) |
| `contactNo` | String | PiiString (decrypt) |

### 3. `IndustryAssociationRegistrationResponse`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |
| `apexHolderMobile` | String | PiiString (decrypt) |
| `apexHolderEmail` | String | PiiString (decrypt) |
| `nodalMobile` | String | PiiString (decrypt) |
| `nodalEmail` | String | PiiString (decrypt) |
| `email` | String | PiiString (decrypt) |
| `sidbeApprovedByUserId` | Long | PiiId (decrypt) |

### 4. `IndustryAssociationAppraisalResponse`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |
| `registrationId` | Long | PiiId (decrypt) |
| `sidbeApprovedByUserId` | Long | PiiId (decrypt) |
| `apexHolderMobile` | String | PiiString (decrypt) |
| `apexHolderEmail` | String | PiiString (decrypt) |
| `nodalMobile` | String | PiiString (decrypt) |
| `nodalEmail` | String | PiiString (decrypt) |

### 5. `BseRecommendationResponse`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |
| `registrationId` | Long | PiiId (decrypt) |
| `userId` | Long | PiiId (decrypt) |
| `mobileNumber` | String | PiiString (decrypt) |
| `emailId` | String | PiiString (decrypt) |

### 6. `VendorResponseDTO`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |
| `vendorId` | String | PiiId (decrypt) |
| `spocMobileNo` | String | PiiString (decrypt) |
| `email` | String | PiiString (decrypt) |
| `mobileNo` | String | PiiString (decrypt) |

### 7. `VendorDropdownDTO`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |
| `vendorId` | String | PiiId (decrypt) |

### 8. `RegionalOfficeResponse`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |
| `contactNo` | String | PiiString (decrypt) |

### 9. `DisbursementCapexResponse`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |
| `registrationId` | Long | PiiId (decrypt) |

### 10. `BseSalaryResponse`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |

### 11. `BseAttendanceDTO`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |
| `bseRecommendationId` | Long | PiiId (decrypt) |

### 12. `MonthlySalaryDetailsResponse`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |

### 13. `SustainabilityMatrixResponse`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |
| `appraisalId` | Long | PiiId (decrypt) |

### 14. `RegistrationDropdownDto`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |

### 15. `AppraisalDropdownDto`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |

### 16. `BranchDropdownResponse`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |

### 17. `SidbiSdeDropdownResponse`
| Field | Type | Decryption |
|---|---|---|
| `id` | Long | PiiId (decrypt) |

---

## URL Path / Query Parameters

All `@PathVariable` and `@RequestParam` `Long` values in API URLs must be encrypted. Examples:

```
GET  /api/users/{encryptedUserId}
GET  /api/registrations/{encryptedRegistrationId}
GET  /api/bse-recommendations/{encryptedId}
```

The backend `EncryptedIdResolver` and `EncryptedIdConverter` transparently decrypt these on the server side.

---

## Fields Deliberately NOT Encrypted (Plain Text)

These fields are intentionally kept as plain text per data protection policy:

| Field | Used In |
|---|---|
| `panNo` | `CreateIndustryAssociationRegistrationRequest`, `UpdateIndustryAssociationRegistrationRequest` |
| `gstNo` / `gstinOfAgency` / `gstinSidbi` | `VendorRequestDTO`, `BseSalaryRequest`, `DisbursementCapexRequest` |
| `accountNumber` | `VendorRequestDTO` |
| `ifscCode` | `VendorRequestDTO` |
| All `BigDecimal` financial amounts | Salary, disbursement, invoice DTOs |
| All `Boolean` flags | Eligibility, sustainability, approval DTOs |
| All `LocalDate` / `LocalDateTime` fields | Audit, date, timestamp fields |

---

## Backend Reference Files

| File | Purpose |
|---|---|
| `src/main/java/org/emat/util/PiiEncryptionService.java` | AES-256-GCM encryption engine |
| `src/main/java/org/emat/dto/serializer/PiiIdEncryptSerializer.java` | Long ID encryption serializer |
| `src/main/java/org/emat/dto/serializer/PiiIdDecryptDeserializer.java` | Long ID decryption deserializer |
| `src/main/java/org/emat/dto/serializer/PiiStringEncryptSerializer.java` | String PII encryption serializer |
| `src/main/java/org/emat/dto/serializer/PiiStringDecryptDeserializer.java` | String PII decryption deserializer |
| `src/main/java/org/emat/config/EncryptedIdConverter.java` | URL query param ID decryption |
| `src/main/java/org/emat/config/EncryptedIdResolver.java` | URL path variable ID decryption |
| `src/main/java/org/emat/controller/PiiEncryptionKeyController.java` | Returns AES key to frontend |
| `documentation/FE_BE_ENCRYPTION_CONTRACT.md` | JS Web Crypto reference implementation |
| `documentation/ENCRYPTION_TESTING_GUIDE.md` | Postman/cURL testing guide |
| `documentation/PII_DATA_PROTECTION.md` | Full data protection policy |
