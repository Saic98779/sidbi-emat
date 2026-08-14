# Industry Association Registration API

Base path: `/industry-association-registrations`

REST endpoints for managing Industry Association Registration records — creation, retrieval, updates, soft deletion, SIDBI approval workflow, and search.

---

## Table of Contents

1. [Create Registration](#1-create-registration)
2. [Get Registration by UUID](#2-get-registration-by-uuid)
3. [Get All Registrations](#3-get-all-registrations)
4. [Update Registration](#4-update-registration)
5. [Delete Registration](#5-delete-registration)
6. [Approve/Reject Registration (SIDBI)](#6-approvereject-registration-sidbi)
7. [Search Registrations](#7-search-registrations)
8. [Roles Reference](#roles-reference)

---

## 1. Create Registration

Creates a new Industry Association Registration.

| | |
|---|---|
| **Method** | `POST` |
| **Path** | `/industry-association-registrations` |
| **Allowed Roles** | `GT_FIELD_TEAM`, `BSE`, `MANPOWER_AGENCY`, `SIDBI_HO_MAKER`, `SIDBI_RO` |

### Request Body
`CreateIndustryAssociationRegistrationRequest` (JSON)

### Response
- **201 Created** — Returns the created `IndustryAssociationRegistrationResponse` object.

### Example

```http
POST /industry-association-registrations
Content-Type: application/json
Authorization: Bearer <token>

{
  // fields per CreateIndustryAssociationRegistrationRequest
}
```


HTTP/ 201 Created
{
  // IndustryAssociationRegistrationResponse fields
}
```

---

## 2. Get Registration by UUID

Retrieves a single registration by its unique identifier.

| | |
|---|---|
| **Method** | `GET` |
| **Path** | `/industry-association-registrations/{uuid}` |
| **Allowed Roles** | `GT_FIELD_TEAM`, `GT_PMU`, `BSE`, `MANPOWER_AGENCY`, `SIDBI_SDE`, `SIDBI_RO`, `SIDBI_HO_MAKER`, `SIDBI_HO_CHECKER`, `CLUSTER_EXPERT` |

### Path Parameters
| Name | Type | Description |
|---|---|---|
| `uuid` | `string` | Unique identifier of the registration |

### Response
- **200 OK** — Returns the matching `IndustryAssociationRegistrationResponse` object.

### Example

```http
GET /industry-association-registrations/9f8c3b2a-1234-4a5b-9c6d-abcdef123456
Authorization: Bearer <token>
```

---

## 3. Get All Registrations

Retrieves all active registrations.

| | |
|---|---|
| **Method** | `GET` |
| **Path** | `/industry-association-registrations` |
| **Allowed Roles** | `GT_FIELD_TEAM`, `GT_PMU`, `BSE`, `MANPOWER_AGENCY`, `SIDBI_SDE`, `SIDBI_RO`, `SIDBI_HO_MAKER`, `SIDBI_HO_CHECKER`, `CLUSTER_EXPERT` |

### Response
- **200 OK** — Returns a JSON array of `IndustryAssociationRegistrationResponse` objects.

### Example

```http
GET /industry-association-registrations
Authorization: Bearer <token>
```


HTTP/1.1 200 OK
[
  { /* IndustryAssociationRegistrationResponse */ },
  { /* IndustryAssociationRegistrationResponse */ }
]
```

---

## 4. Update Registration

Updates an existing registration.

| | |
|---|---|
| **Method** | `PUT` |
| **Path** | `/industry-association-registrations/{uuid}` |
| **Allowed Roles** | `GT_FIELD_TEAM`, `BSE`, `MANPOWER_AGENCY`, `SIDBI_HO_MAKER`, `SIDBI_RO` |

### Path Parameters
| Name | Type | Description |
|---|---|---|
| `uuid` | `string` | Unique identifier of the registration |

### Request Body
`UpdateIndustryAssociationRegistrationRequest` (JSON)

### Response
- **200 OK** — Returns the updated `IndustryAssociationRegistrationResponse` object.

---

## 5. Delete Registration

Soft-deletes a registration (marks it as inactive rather than removing it).

| | |
|---|---|
| **Method** | `DELETE` |
| **Path** | `/industry-association-registrations/{uuid}` |
| **Allowed Roles** | `SIDBI_HO_MAKER`, `SIDBI_RO` |

### Path Parameters
| Name | Type | Description |
|---|---|---|
| `uuid` | `string` | Unique identifier of the registration |

### Response
- **204 No Content** — Registration successfully marked inactive.

---

## 6. Approve/Reject Registration (SIDBI)

Approves or rejects a registration as part of the SIDBI review workflow.

| | |
|---|---|
| **Method** | `PATCH` |
| **Path** | `/industry-association-registrations/{uuid}/approve` |
| **Allowed Roles** | `SIDBI_SDE`, `SIDBI_RO`, `SIDBI_HO_CHECKER`, `SIDBI_HO_MAKER` |

### Path Parameters
| Name | Type | Description |
|---|---|---|
| `uuid` | `string` | Unique identifier of the registration |

### Request Body
`ApprovalRequest` (JSON)

### Notes
- The authenticated username (from the `Authentication` principal) is recorded as the approver.

### Response
- **200 OK** — Returns the updated `IndustryAssociationRegistrationResponse` object.

---

## 7. Search Registrations

Searches registrations filtered by state, district, and SIDBI approval status.

| | |
|---|---|
| **Method** | `GET` |
| **Path** | `/industry-association-registrations/search` |
| **Allowed Roles** | `GT_FIELD_TEAM`, `GT_PMU`, `BSE`, `MANPOWER_AGENCY`, `SIDBI_SDE`, `SIDBI_RO`, `SIDBI_HO_MAKER`, `SIDBI_HO_CHECKER`, `CLUSTER_EXPERT` |

### Query Parameters
| Name | Type | Required | Description |
|---|---|---|---|
| `state` | `string` | Yes | State where the Industry Association is registered |
| `district` | `string` | Yes | District where the Industry Association is registered |
| `isSidbeApproved` | `boolean` | Yes | SIDBI approval status (`true` = approved, `false` = not approved) |

### Response
- **200 OK** — Returns a JSON array of matching `IndustryAssociationRegistrationResponse` objects.

### Example

```http
GET /industry-association-registrations/search?state=Odisha&district=Khordha&isSidbeApproved=true
Authorization: Bearer <token>
```

---

## Roles Reference

| Role | Description (inferred from usage) |
|---|---|
| `GT_FIELD_TEAM` | Field-level user who can create/update registrations |
| `GT_PMU` | Program management unit — read access |
| `BSE` | Can create/update registrations |
| `MANPOWER_AGENCY` | Can create/update registrations |
| `SIDBI_SDE` | SIDBI staff — read and approval access |
| `SIDBI_RO` | SIDBI regional office — full CRUD, delete, and approval access |
| `SIDBI_HO_MAKER` | SIDBI head office maker — full CRUD, delete, and approval access |
| `SIDBI_HO_CHECKER` | SIDBI head office checker — read and approval access |
| `CLUSTER_EXPERT` | Read-only access |

---

## Notes

- All endpoints require authentication and are protected by role-based access control (`@PreAuthorize`).
- All request/response bodies are JSON.
- `uuid` path variables identify a specific registration record.
- The delete operation is a **soft delete**; the record is marked inactive rather than physically removed.
