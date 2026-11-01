# API Changes: Fetch-by-ID endpoints moved from GET to POST

**Audience:** Frontend team
**Compared:** Production (`https://api.emat-prod.metaversedu.in/emat/v1`) vs. new build (`http://localhost:8086/emat/v1`)
**Date:** 2026-11-01

## Summary

Identifiers are no longer sent in the URL (path variables / query params). Every endpoint that
fetched a record by id has been changed from **`GET` with the id in the URL** to **`POST` with the
id in a JSON body**. The old GET URLs are **removed** and will return an error.

- 42 GET endpoints removed, replaced by 42 POST endpoints.
- No changes to response bodies - each endpoint returns exactly what it returned before.
- No other endpoints (create / update / delete / list) are affected.

## What the frontend needs to do

For every call in the table below:

1. Change the HTTP method from `GET` to `POST`.
2. Change the URL to the new path (the id is removed from the URL).
3. Send the id in the request body, with `Content-Type: application/json`:

```json
{ "id": "<encrypted id>" }
```

### Rules for `id`

- `id` is **required**. A missing/null `id` returns a 400 validation error.
- `id` must be the **encrypted** identifier string - the same value the backend returns in the `id`
  (or `registrationId`, `userId`, ...) field of its responses. Pass it back as-is.
  **Plain numeric ids are rejected.**
- Swagger shows `id` as `integer (int64)` because that is the backend type; on the wire it is a
  **string**.
- The body field is always named `id`, regardless of what the old path variable was called
  (`registrationId`, `userId`, `activityId`, `branchId`, ...).

### Example

Before:
```http
GET /emat/v1/vendor/user/{userId}
```

After:
```http
POST /emat/v1/vendor/user
Content-Type: application/json

{ "id": "<encrypted userId>" }
```

## Endpoint mapping

All paths are relative to `/emat/v1`. "Body `id`" is the value that used to be in the URL.

### Action Plans
| Old | New | Body `id` |
|---|---|---|
| `GET /action-plans/{id}` | `POST /action-plans/get` | action plan id |

### Activity
| Old | New | Body `id` |
|---|---|---|
| `GET /activity/{activityId}` | `POST /activity/get` | activityId |
| `GET /activity/{activityId}/status` | `POST /activity/status` | activityId |
| `GET /activity/{activityId}/status/history` | `POST /activity/status/history` | activityId |

### BDS Service Providers Onboarding
| Old | New | Body `id` |
|---|---|---|
| `GET /bds-service-providers-onboarding/{id}` | `POST /bds-service-providers-onboarding/get` | record id |

### BDSP
| Old | New | Body `id` |
|---|---|---|
| `GET /bdsp/{id}` | `POST /bdsp/get` | record id |

### BSE Attendance
| Old | New | Body `id` |
|---|---|---|
| `GET /bse-attendance/{id}` | `POST /bse-attendance/get` | attendance id |
| `GET /bse-attendance/recommendation/{recommendationId}` | `POST /bse-attendance/recommendation` | recommendationId |

### BSE Attendance Manual Request
| Old | New | Body `id` |
|---|---|---|
| `GET /bse-attendance-manual-request/{id}` | `POST /bse-attendance-manual-request/get` | request id |
| `GET /bse-attendance-manual-request/recommendation/{recommendationId}` | `POST /bse-attendance-manual-request/recommendation` | recommendationId |

### BSE Recommendations
| Old | New | Body `id` |
|---|---|---|
| `GET /bse-recommendations/{id}` | `POST /bse-recommendations/get` | recommendation id |
| `GET /bse-recommendations/registration/{registrationId}` | `POST /bse-recommendations/registration` | registrationId |
| `GET /bse-recommendations/user/{userId}/selected` | `POST /bse-recommendations/user/selected` | userId |

### BSE Salary
| Old | New | Body `id` |
|---|---|---|
| `GET /bse-salary/{id}` | `POST /bse-salary/get` | salary id |

### Bulk Broadcast
| Old | New | Body `id` |
|---|---|---|
| `GET /bulk-broadcast/{id}` | `POST /bulk-broadcast/get` | broadcast id |

### DIA 3C Info Series
| Old | New | Body `id` |
|---|---|---|
| `GET /dia-3c-info-series/{id}` | `POST /dia-3c-info-series/get` | record id |

### Disbursement Capex
| Old | New | Body `id` |
|---|---|---|
| `GET /disbursement-capex/{id}` | `POST /disbursement-capex/get` | record id |
| `GET /disbursement-capex/registration/{registrationId}` | `POST /disbursement-capex/registration` | registrationId |

### Disbursement Note - Capacity Building IA
| Old | New | Body `id` |
|---|---|---|
| `GET /disbursement-note-capacity-building-ia/{id}` | `POST /disbursement-note-capacity-building-ia/get` | record id |
| `GET /disbursement-note-capacity-building-ia/registration/{registrationId}` | `POST /disbursement-note-capacity-building-ia/registration` | registrationId |

### Disbursement Note - Capacity Building IA Officials
| Old | New | Body `id` |
|---|---|---|
| `GET /disbursement-note-capacity-building-ia-officials/{id}` | `POST /disbursement-note-capacity-building-ia-officials/get` | record id |
| `GET /disbursement-note-capacity-building-ia-officials/registration/{registrationId}` | `POST /disbursement-note-capacity-building-ia-officials/registration` | registrationId |

### Discussion Forum
| Old | New | Body `id` |
|---|---|---|
| `GET /discussion-forum/{id}` | `POST /discussion-forum/get` | record id |

### E-learning Module Content
| Old | New | Body `id` |
|---|---|---|
| `GET /elearning-module-content/{id}` | `POST /elearning-module-content/get` | record id |

### Eligibility Matrix
| Old | New | Body `id` |
|---|---|---|
| `GET /eligibility-matrix/{id}` | `POST /eligibility-matrix/get` | record id |
| `GET /eligibility-matrix/registration/{registrationId}` | `POST /eligibility-matrix/registration` | registrationId |

### Industry Association Appraisals
| Old | New | Body `id` |
|---|---|---|
| `GET /industry-association-appraisals/{id}` | `POST /industry-association-appraisals/get` | appraisal id |
| `GET /industry-association-appraisals/registration/{registrationId}` | `POST /industry-association-appraisals/registration` | registrationId |
| `GET /industry-association-appraisals/registration/{registrationId}/dia-report` | `POST /industry-association-appraisals/registration/dia-report` | registrationId |
| `GET /industry-association-appraisals/{id}/appraisal-format` | `POST /industry-association-appraisals/appraisal-format` | appraisal id |

> `dia-report` and `appraisal-format` still return **HTML**. If these were opened directly in a
> browser tab / `<a href>` / `window.open`, that no longer works (a browser can't send a POST body
> from a link). Fetch the HTML with a POST call and render it yourself (e.g. into a new window
> or an iframe via a Blob URL).

### Industry Association Registrations
| Old | New | Body |
|---|---|---|
| `GET /industry-association-registrations/{id}` | `POST /industry-association-registrations/get` | `{ "id": registration id }` |
| `GET /industry-association-registrations/{registrationId}/stage-history` | `POST /industry-association-registrations/stage-history` | `{ "id": registrationId }` |
| `GET /industry-association-registrations/stage/{stageId}` | `POST /industry-association-registrations/stage` | `{ "id": stageId }` |
| `GET /industry-association-registrations/dropdown?stageId=&state=&createdBy=&district=` | `POST /industry-association-registrations/dropdown` | see below |

**Dropdown (different body):** the four query params move into the body. The body and every
field in it are **optional** - send `{}` (or no body) to get the unfiltered list.

```json
{
  "stageId": "<encrypted stageId>",
  "state": "Maharashtra",
  "createdBy": "user@example.com",
  "district": "Pune"
}
```

- `stageId` must be the **encrypted** stage id (previously it was a plain query string).
- `state`, `createdBy`, `district` are plain strings, same as before.

### Latest Developments
| Old | New | Body `id` |
|---|---|---|
| `GET /latest-developments/{id}` | `POST /latest-developments/get` | record id |

### Pop-ups
| Old | New | Body `id` |
|---|---|---|
| `GET /pop-ups/{id}` | `POST /pop-ups/get` | pop-up id |

### SIDBI SDE
| Old | New | Body `id` |
|---|---|---|
| `GET /sidbi-sde/dropdown?branchId=` | `POST /sidbi-sde/dropdown` | **branchId** |

> Note the field rename: the old query param was `branchId`; the body field is `id`.

### Surveys
| Old | New | Body `id` |
|---|---|---|
| `GET /surveys/{id}` | `POST /surveys/get` | survey id |

### Sustainability Matrix
| Old | New | Body `id` |
|---|---|---|
| `GET /sustainability-matrix/{id}` | `POST /sustainability-matrix/get` | record id |
| `GET /sustainability-matrix/appraisal/{appraisalId}` | `POST /sustainability-matrix/appraisal` | appraisalId |

### Vendor
| Old | New | Body `id` |
|---|---|---|
| `GET /vendor/{id}` | `POST /vendor/get` | vendor id |
| `GET /vendor/user/{userId}` | `POST /vendor/user` | userId |

## New request schemas

```text
IdRequest
  id: string (encrypted Long)   required

RegistrationDropdownRequest     (whole body optional)
  stageId:   string (encrypted Long)   optional
  state:     string                    optional
  createdBy: string                    optional
  district:  string                    optional
```

## Frontend checklist

- [ ] Update all 42 calls listed above (method, URL, body).
- [ ] Send the encrypted id string from earlier responses - never a raw number.
- [ ] `/sidbi-sde/dropdown`: send `branchId` as `id`.
- [ ] `/industry-association-registrations/dropdown`: move query params into the body; encrypt `stageId`.
- [ ] `dia-report` / `appraisal-format`: replace link/`window.open` navigation with a POST fetch + render.
- [ ] Check any caching, prefetch or retry logic that assumed these calls were GETs (POST
      responses are not cached by the browser or by HTTP-cache libraries by default).

## Not relevant to frontend

The local Swagger also lists `POST /testing/encrypt` and `POST /testing/decrypt`
(`encryption-test-controller`). These are developer-only helpers that exist only under the
`local` profile; they are **not** deployed to production and must not be used by the frontend.
