# Frontend Integration Changes

## Scope

This handoff is based on the current code in `sidbi-emat`.

| Comparison | Baseline | Included changes |
|---|---|---|
| Uncommitted work | Current working tree versus `HEAD` (`a1495cd`) | Status split, Annexure V/VI, type changes, and related mappings/services |
| Branch-only commit | `sidbi-sameer` versus `origin/prod` (`a6cbf45`) | `address`, `grantProposedCapacityBuilding`, and `secretariatStaff` contract change |

All paths below use the application context path: `/emat/v1`.

## Frontend action summary

1. Replace `status` with `makerStatus` and `checkerStatus` for 10 modules.
2. Add Annexure V and Annexure VI grids to Industry Association Appraisal create, read, and update screens.
3. Change appraisal `termsAndConditions` from a string to a string array.
4. Change registration `selectionCriteria` from a string array to a string.
5. Change registration `secretariatStaff` from an object array to a plain string.
6. Add `address` and `grantProposedCapacityBuilding` to the applicable models/forms.
7. Treat the new `annexureVList[].id` and `annexureVIList[].id` response values as encrypted `ENC:...` strings.
8. Do not add a decryptor for the new annexure request objects; their requests do not contain `id`.
9. Preserve encrypted IDs when using them in URLs; the backend decrypts path/query IDs automatically.
10. No new API route is required. All new fields are carried by existing endpoints.

## 1. Breaking change: `status` becomes maker/checker status

### Contract change

| Direction | Old JSON | New JSON |
|---|---|---|
| Status update request | `{ "status": "APPROVED", "remark": "..." }` | `{ "makerStatus": "APPROVED", "checkerStatus": "REJECT", "remark": "..." }` |
| Read/update/status-update response | `{ "status": "APPROVED" }` | `{ "makerStatus": "APPROVED", "checkerStatus": "REJECT" }` |

Important rules:

- `makerStatus` and `checkerStatus` are both required in the status-update request.
- The old `status` field is removed from the request and response DTOs.
- The only currently defined values are `APPROVED`, `REJECT`, and `REVERT`; there is no `PENDING` value.
- `makerStatus` and `checkerStatus` are plain enum strings. They are not encrypted.
- `remark` is optional, but omitting it clears the existing remark during this status update.
- Existing response DTO `id` encryption is unchanged. Path `{id}` values must still be sent encrypted.

Suggested frontend types:

```ts
type WorkflowStatus = "APPROVED" | "REJECT" | "REVERT";

interface UpdateWorkflowStatusRequest {
  makerStatus: WorkflowStatus;
  checkerStatus: WorkflowStatus;
  remark?: string | null;
}

interface WorkflowStatusFields {
  makerStatus: WorkflowStatus | null;
  checkerStatus: WorkflowStatus | null;
}
```

### Affected endpoints

| Module | Method and path |
|---|---|
| Action Plan | `PATCH /emat/v1/action-plans/{id}/status` |
| BDSP | `PATCH /emat/v1/bdsp/{id}/status` |
| BDS Service Providers Onboarding | `PATCH /emat/v1/bds-service-providers-onboarding/{id}/status` |
| Bulk Broadcast | `PATCH /emat/v1/bulk-broadcast/{id}/status` |
| DIA 3C Info Series | `PATCH /emat/v1/dia-3c-info-series/{id}/status` |
| Discussion Forum | `PATCH /emat/v1/discussion-forum/{id}/status` |
| E-learning Module Content | `PATCH /emat/v1/elearning-module-content/{id}/status` |
| Latest Developments | `PATCH /emat/v1/latest-developments/{id}/status` |
| Pop-Ups | `PATCH /emat/v1/pop-ups/{id}/status` |
| Survey | `PATCH /emat/v1/surveys/{id}/status` |

The same response rename affects the list, detail, update, and status-update responses for these modules.

### Not changed

The following modules still use the old single `status` contract and must not be migrated with the 10 modules above:

- `DisbursementNoteCapacityBuildingIa`
- `DisbursementNoteCapacityBuildingIaOfficials`

## 2. Industry Association Appraisal changes

### Existing endpoints receiving the changes

| Method | Path | Change |
|---|---|---|
| `POST` | `/emat/v1/industry-association-appraisals` | Accepts the new appraisal fields |
| `PUT` | `/emat/v1/industry-association-appraisals/{id}` | Accepts the new fields; annexure and terms lists have replace semantics |
| `GET` | `/emat/v1/industry-association-appraisals/{id}` | Returns the new fields |
| `GET` | `/emat/v1/industry-association-appraisals/registration/{registrationId}` | Returns the new fields |
| `GET` | `/emat/v1/industry-association-appraisals` | Returns the new fields for every appraisal |
| `GET` | `/emat/v1/industry-association-appraisals/search` | Returns the new fields for every result |
| `PATCH` | `/emat/v1/industry-association-appraisals/{id}/approve` | Response includes the new fields |
| `DELETE` | `/emat/v1/industry-association-appraisals/{id}` | Deletes the appraisal and its child annexure rows |

### `termsAndConditions`: string to string array

| Direction | Old type | New type |
|---|---|---|
| Create request | `string` | `string[]` |
| Update request | `string` | `string[]` |
| Response | `string` | `string[]` |

Old payload:

```json
{
  "termsAndConditions": "First condition, Second condition"
}
```

New payload:

```json
{
  "termsAndConditions": [
    "First condition",
    "Second condition"
  ]
}
```

Frontend rules:

- Do not send a string for this field.
- On update, omitted/`null` leaves the existing list unchanged.
- On update, `[]` clears all terms.
- Backend ordering is not guaranteed because the collection has no explicit order column. Preserve display order in frontend state when possible.

### New `annexureVList`

Create and update request row:

```ts
interface AnnexureVRequestRow {
  snNo: number;
  particulars: string;
  totalCost: number;
  sidbiSupport: number;
}
```

Response row:

```ts
interface AnnexureVResponseRow {
  id: string; // "ENC:..." encrypted by backend
  snNo: number;
  particulars: string;
  totalCost: number;
  sidbiSupport: number;
}
```

Field details:

| Field | JSON type | Encryption | Notes |
|---|---|---|---|
| `id` | string | Encrypted response value | Not accepted in create/update request DTOs |
| `snNo` | number | Plain | Suggested display/order field |
| `particulars` | string | Plain | Database maximum 500 characters |
| `totalCost` | number | Plain | Decimal amount |
| `sidbiSupport` | number | Plain | Decimal amount |

### New `annexureVIList`

Create and update request row:

```ts
interface AnnexureVIRequestRow {
  section: string;
  sectionNote: string;
  indicativeItem: string;
  numbers: number;
  make: string;
  maximumCost: number;
  maximumCostUnit: string;
}
```

Response row:

```ts
interface AnnexureVIResponseRow {
  id: string; // "ENC:..." encrypted by backend
  section: string;
  sectionNote: string;
  indicativeItem: string;
  numbers: number;
  make: string;
  maximumCost: number;
  maximumCostUnit: string;
}
```

The API field name is exactly `numbers`, not `number`.

| Field | JSON type | Encryption | Notes |
|---|---|---|---|
| `id` | string | Encrypted response value | Not accepted in create/update request DTOs |
| `section` | string | Plain | Database maximum 200 characters |
| `sectionNote` | string | Plain | Database maximum 500 characters |
| `indicativeItem` | string | Plain | Database maximum 300 characters |
| `numbers` | number | Plain | Integer quantity |
| `make` | string | Plain | Database maximum 300 characters |
| `maximumCost` | number | Plain | Decimal amount |
| `maximumCostUnit` | string | Plain | Database maximum 50 characters |

### Parent request/response additions

Create request:

```json
{
  "termsAndConditions": ["Condition one"],
  "annexureVList": [
    {
      "snNo": 1,
      "particulars": "Item",
      "totalCost": 100000.00,
      "sidbiSupport": 50000.00
    }
  ],
  "annexureVIList": [
    {
      "section": "Hard Interventions",
      "sectionNote": "Note",
      "indicativeItem": "Item",
      "numbers": 2,
      "make": "Make",
      "maximumCost": 100000.00,
      "maximumCostUnit": "each"
    }
  ]
}
```

Response shape:

```json
{
  "termsAndConditions": ["Condition one"],
  "annexureVList": [
    {
      "id": "ENC:<base64url-value>",
      "snNo": 1,
      "particulars": "Item",
      "totalCost": 100000.00,
      "sidbiSupport": 50000.00
    }
  ],
  "annexureVIList": [
    {
      "id": "ENC:<base64url-value>",
      "section": "Hard Interventions",
      "sectionNote": "Note",
      "indicativeItem": "Item",
      "numbers": 2,
      "make": "Make",
      "maximumCost": 100000.00,
      "maximumCostUnit": "each"
    }
  ]
}
```

### Annexure update behavior

On `PUT /emat/v1/industry-association-appraisals/{id}`:

| Sent value | Backend behavior |
|---|---|
| Field omitted or `null` | Existing rows are not changed |
| `[]` | Existing rows are deleted |
| Non-empty array | Existing rows are deleted and the submitted rows are inserted again |

Consequences for frontend:

- Send the complete desired list, not a partial diff.
- Do not send `null` when the user cleared the grid; send `[]` to clear it.
- Do not use annexure `id` as a stable frontend key. IDs are regenerated on every non-empty update.
- Do not send the response `id` back in either annexure request object.
- Empty lists are returned as `[]`, not `null`.
- Response ordering is not guaranteed. Sort Annexure V using `snNo`; Annexure VI has no guaranteed ordering.
- These are JSON scalar fields, not multipart/file fields.

## 3. Industry Association Registration changes

### `selectionCriteria`: string array to string

| Direction | Old type | New type |
|---|---|---|
| Create request | `string[]` | `string` |
| Update request | `string[]` | `string` |
| Response | `string[]` | `string` |

Old payload:

```json
{
  "selectionCriteria": ["Criterion one", "Criterion two"]
}
```

New payload:

```json
{
  "selectionCriteria": "Criterion one, Criterion two"
}
```

`selectionCriteria` is plain text and is not encrypted. The database column has a 2000-character maximum, but the DTO currently has no bean validation, so the frontend should enforce the limit.

The affected endpoints are:

- `POST /emat/v1/industry-association-registrations`
- `PUT /emat/v1/industry-association-registrations/{id}`
- `GET /emat/v1/industry-association-registrations/{id}`
- `GET /emat/v1/industry-association-registrations`

### Branch-only additions/changes

These are already committed in branch commit `9606ce5`, but are not in `origin/prod`:

| DTOs | Field | Old contract | New contract | Encryption |
|---|---|---|---|---|
| Registration create/update/response | `address` | Missing | `string` | Plain |
| Appraisal create/update/response | `grantProposedCapacityBuilding` | Missing | `number` (`BigDecimal`) | Plain |
| Registration create/update/response | `secretariatStaff` | `SecretariatStaffDto[]` | `string` | Plain |

Old `secretariatStaff` item:

```ts
interface SecretariatStaffDto {
  name: string;
  contact: string; // encrypted in the old request/response DTO
  email: string;   // encrypted in the old request/response DTO
}
```

New contract:

```json
{
  "secretariatStaff": "Staff details"
}
```

Frontend actions:

- Remove the nested staff editor/table and replace it with one text field/textarea.
- The replacement string can contain contact/email details, but the backend no longer encrypts the nested `contact` and `email` values separately.
- Add `address` to the registration model and form.
- Add `grantProposedCapacityBuilding` to the appraisal model and form.
- `infrastructureType` remains plain; the registration database length changes from 200 to 500 characters, while the appraisal field remains 200 characters.

## 4. Encryption/decryption field list for these changes

### New encrypted fields in the current changes

| Location | Field | Backend response | Backend request decryption | Frontend action |
|---|---|---|---|---|
| `annexureVList[]` in `IndustryAssociationAppraisalResponse` | `id` | AES-GCM encrypted `ENC:...` string | None; request row has no `id` | Model as string; do not send it in the create/update row |
| `annexureVIList[]` in `IndustryAssociationAppraisalResponse` | `id` | AES-GCM encrypted `ENC:...` string | None; request row has no `id` | Model as string; do not send it in the create/update row |

There are **no newly added request-side decrypt fields** in the current uncommitted diff. All other new fields, including `makerStatus`, `checkerStatus`, `termsAndConditions`, `selectionCriteria`, `secretariatStaff`, and all annexure business values, are plain JSON values.

### Existing Industry Association fields the frontend must continue to handle

#### `CreateIndustryAssociationAppraisalRequest`

| Fields | Request handling |
|---|---|
| `registrationId`, `stageId` | Frontend AES-encrypts; backend decrypts to `Long` |
| `apexHolderMobile`, `apexHolderEmail`, `nodalMobile`, `nodalEmail` | Frontend AES-encrypts; backend decrypts to plain `String` |

#### `UpdateIndustryAssociationAppraisalRequest`

| Fields | Request handling |
|---|---|
| `stageId` | Frontend AES-encrypts; backend decrypts to `Long` |
| `apexHolderMobile`, `apexHolderEmail`, `nodalMobile`, `nodalEmail` | Frontend AES-encrypts; backend decrypts to plain `String` |

#### `IndustryAssociationAppraisalResponse`

| Fields | Response handling |
|---|---|
| `id`, `registrationId`, `sidbeApprovedByUserId` | Backend AES-encrypts; frontend receives `ENC:...` strings |
| `apexHolderMobile`, `apexHolderEmail`, `nodalMobile`, `nodalEmail` | Backend AES-encrypts; frontend decrypts for display using the established AES helper |

#### `CreateIndustryAssociationRegistrationRequest`

| Fields | Request handling |
|---|---|
| `apexHolderMobile`, `apexHolderEmail`, `nodalMobile`, `nodalEmail`, `email` | Frontend AES-encrypts; backend decrypts to plain `String` |
| `sidbeApprovedByUserId`, `stageId` | Frontend AES-encrypts; backend decrypts to `Long` |

#### `UpdateIndustryAssociationRegistrationRequest`

| Fields | Request handling |
|---|---|
| `apexHolderMobile`, `apexHolderEmail`, `nodalMobile`, `nodalEmail`, `email` | Frontend AES-encrypts; backend decrypts to plain `String` |
| `stageId` | Frontend AES-encrypts; backend decrypts to `Long` |

#### `IndustryAssociationRegistrationResponse`

| Fields | Response handling |
|---|---|
| `id`, `sidbeApprovedByUserId` | Backend AES-encrypts; frontend receives `ENC:...` strings |
| `apexHolderMobile`, `apexHolderEmail`, `nodalMobile`, `nodalEmail`, `email` | Backend AES-encrypts; frontend decrypts for display using the established AES helper |
| `address`, `secretariatStaff`, `selectionCriteria` | Plain text; do not encrypt |

### Important correction to the previous handoff

The current DTO code requires encrypted `stageId` values. The previous statement that DTO-body `stageId` values should be sent as plain numbers is no longer valid.

`stageId` is request-encrypted in:

- `CreateIndustryAssociationAppraisalRequest`
- `UpdateIndustryAssociationAppraisalRequest`
- `CreateIndustryAssociationRegistrationRequest`
- `UpdateIndustryAssociationRegistrationRequest`
- `ApprovalRequest`
- `SustainabilityMatrixRequest`
- `EligibilityMatrixDto`

`StageResponse.id`, `StageHistoryResponse.id`, and `StageHistoryResponse.registrationId` are still plain numbers because those response DTOs do not use the encryption serializers. Frontend must encrypt a stage number before placing it in one of the request fields above.

### Global ID and path rules

- Wire format: `ENC:<url-safe-base64(iv || ciphertext || GCM-tag)>`.
- Algorithm: AES-256-GCM.
- Obtain the established key through `GET /emat/v1/pii-encryption-key` after login and use the existing frontend encryption helper.
- The local and production profiles currently enable strict encryption, so plain protected request values are rejected.
- All backend `Long` path/query IDs are decrypted by the backend; pass response IDs as-is in URLs.
- File API query IDs (`stageId`, optional `registrationId`) remain encrypted.
- For the full application-wide inventory, use `../ENCRYPT_DECRYPT_FIELDS_FOR_FRONTEND.md` and `FE_BE_ENCRYPTION_CONTRACT.md`.

## 5. Frontend implementation checklist

- [ ] Update generated/API models and TypeScript interfaces for `makerStatus` and `checkerStatus`.
- [ ] Update all 10 status forms to send both statuses.
- [ ] Remove reads and writes of the old `status` field for those 10 modules.
- [ ] Keep the two disbursement capacity-building modules on `status` until their backend contract changes.
- [ ] Add `annexureVList` and `annexureVIList` to appraisal create/update payloads and all appraisal response models.
- [ ] Implement Annexure V and VI grids using the fields and types listed above.
- [ ] Treat both annexure lists as full-list replacement on update.
- [ ] Do not submit annexure response IDs.
- [ ] Convert `termsAndConditions` handling to an array.
- [ ] Convert `selectionCriteria` handling to a string.
- [ ] Convert `secretariatStaff` from nested rows to a single text value.
- [ ] Add `address` and `grantProposedCapacityBuilding` to the relevant forms.
- [ ] Keep new business fields as plain JSON.
- [ ] Continue AES-encrypting the existing protected request IDs and PII strings.
- [ ] Correct `stageId` request handling: encrypt it; do not send a plain number.
- [ ] Treat encrypted IDs as strings and pass them unchanged in URLs.
- [ ] Add success, 400, and legacy-null response tests before rollout.

## 6. Backend items to confirm before frontend release

These are not frontend tasks, but they can block or invalidate testing:

- The six new annexure request/response DTO files are currently untracked, and the two annexure entities are staged as earlier stub versions. They must be correctly staged before commit.
- Database DDL/data migration is required for Annexure V/VI, maker/checker status, terms-and-conditions storage, and scalar `selectionCriteria`.
- Existing `STATUS` data must be mapped deliberately to `MAKER_STATUS`/`CHECKER_STATUS`; otherwise legacy response statuses may be null.
- Existing terms, selection-criteria, and secretariat-staff data needs a migration strategy.
- `approvedDate` currently changes only from `makerStatus`; confirm whether checker approval should affect it.
- Confirm valid maker/checker combinations and whether a `PENDING` state is required.
- New annexure DTO fields do not have bean validation for required values or maximum lengths; oversized database values may currently result in a server error.
- Confirm the security requirement for the new plain `secretariatStaff` string before release.
- Annexure lists use eager loading and can enlarge list/search responses; confirm performance before production rollout.

## Backend source references

- `src/main/java/org/emat/dto/AnnexureVResponse.java:17`
- `src/main/java/org/emat/dto/AnnexureVIResponse.java:17`
- `src/main/java/org/emat/dto/CreateIndustryAssociationAppraisalRequest.java:68`
- `src/main/java/org/emat/dto/UpdateIndustryAssociationAppraisalRequest.java:67`
- `src/main/java/org/emat/dto/IndustryAssociationAppraisalResponse.java:75`
- `src/main/java/org/emat/mapper/IndustryAssociationAppraisalMapper.java:340`
- `src/main/java/org/emat/controller/IndustryAssociationAppraisalController.java:27`
- `src/main/java/org/emat/dto/CreateIndustryAssociationRegistrationRequest.java:38`
- `src/main/java/org/emat/dto/UpdateIndustryAssociationRegistrationRequest.java:32`
- `src/main/java/org/emat/dto/IndustryAssociationRegistrationResponse.java:33`
- `src/main/java/org/emat/dto/SecretariatStaffDto.java:17`
- `src/main/java/org/emat/dto/serializer/PiiIdEncryptSerializer.java:28`
- `src/main/java/org/emat/dto/serializer/PiiIdDecryptDeserializer.java:29`
- `src/main/java/org/emat/dto/serializer/PiiStringEncryptSerializer.java:31`
- `src/main/java/org/emat/dto/serializer/PiiStringDecryptDeserializer.java:31`
- `src/main/java/org/emat/config/EncryptedIdResolver.java:32`
- `src/main/java/org/emat/util/PiiEncryptionService.java:145`
- `src/main/java/org/emat/enums/Status.java:3`
