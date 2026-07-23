# EMAT REST API Documentation

## Base URL
```
http://localhost:8080/emat
```

## Authentication
Currently, the API uses HTTP Basic Authentication. Headers are not required for testing but can be added for security.

## Response Format

### Success Response (2xx)
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "DIA",
  "isActive": true,
  "createdAt": "2026-07-23T10:30:00",
  "updatedAt": "2026-07-23T10:30:00"
}
```

### Error Response (4xx, 5xx)
```json
{
  "timestamp": "2026-07-23T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Username already exists",
  "path": "/emat/api/users"
}
```

## Health Check Endpoints

### 1. Application Health
```http
GET /emat/health HTTP/1.1
```

**Response:**
```json
{
  "status": "UP",
  "timestamp": "2026-07-23T10:30:00",
  "application": "EMAT"
}
```

**Status Codes:**
- `200 OK` - Application is running

---

### 2. Welcome Endpoint
```http
GET /emat/ HTTP/1.1
```

**Response:**
```json
{
  "message": "Welcome to EMAT API",
  "version": "1.0-SNAPSHOT"
}
```

**Status Codes:**
- `200 OK` - Success

---

## User Management Endpoints

### 3. Create User
Creates a new user in the system.

```http
POST /emat/api/users HTTP/1.1
Content-Type: application/json

{
  "username": "john_doe",
  "password": "SecurePassword123@",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "DIA"
}
```

**Request Body:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| username | String | Yes | Unique username (1-100 chars) |
| password | String | Yes | Plain password (will be encoded) |
| email | String | Yes | Unique email address |
| firstName | String | No | User's first name |
| lastName | String | No | User's last name |
| role | Enum | Yes | User role (see Role Enum section) |

**Success Response (201):**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "DIA",
  "isActive": true,
  "createdAt": "2026-07-23T10:30:00",
  "updatedAt": "2026-07-23T10:30:00"
}
```

**Error Responses:**
| Status | Message | Description |
|--------|---------|-------------|
| 400 | Username already exists | Username must be unique |
| 400 | Email already exists | Email must be unique |
| 400 | Bad Request | Missing required fields |

**cURL Example:**
```bash
curl -X POST http://localhost:8080/emat/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "SecurePassword123@",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "DIA"
  }'
```

---

### 4. Get All Users
Retrieves all users from the system.

```http
GET /emat/api/users HTTP/1.1
```

**Query Parameters:** None

**Success Response (200):**
```json
[
  {
    "id": 1,
    "username": "admin",
    "email": "admin@emat.com",
    "firstName": "Admin",
    "lastName": "User",
    "role": "DIA",
    "isActive": true,
    "createdAt": "2026-07-23T10:00:00",
    "updatedAt": "2026-07-23T10:00:00"
  },
  {
    "id": 2,
    "username": "bse_user",
    "email": "bse@emat.com",
    "firstName": "BSE",
    "lastName": "Analyst",
    "role": "BSE",
    "isActive": true,
    "createdAt": "2026-07-23T10:00:00",
    "updatedAt": "2026-07-23T10:00:00"
  }
]
```

**Status Codes:**
- `200 OK` - Success
- `500 Internal Server Error` - Server error

**cURL Example:**
```bash
curl http://localhost:8080/emat/api/users
```

---

### 5. Get User by ID
Retrieves a specific user by their ID.

```http
GET /emat/api/users/{id} HTTP/1.1
```

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| id | Long | Yes | User ID (numeric) |

**Success Response (200):**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "DIA",
  "isActive": true,
  "createdAt": "2026-07-23T10:30:00",
  "updatedAt": "2026-07-23T10:30:00"
}
```

**Error Responses:**
| Status | Message | Description |
|--------|---------|-------------|
| 400 | User not found with id: 999 | User ID doesn't exist |

**cURL Example:**
```bash
curl http://localhost:8080/emat/api/users/1
```

---

### 6. Get User by Username
Retrieves a specific user by their username.

```http
GET /emat/api/users/username/{username} HTTP/1.1
```

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| username | String | Yes | User's username |

**Success Response (200):**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "DIA",
  "isActive": true,
  "createdAt": "2026-07-23T10:30:00",
  "updatedAt": "2026-07-23T10:30:00"
}
```

**Error Responses:**
| Status | Message | Description |
|--------|---------|-------------|
| 400 | User not found with username: unknown | Username doesn't exist |

**cURL Example:**
```bash
curl http://localhost:8080/emat/api/users/username/john_doe
```

---

### 7. Update User
Updates an existing user's information.

```http
PUT /emat/api/users/{id} HTTP/1.1
Content-Type: application/json

{
  "username": "john_doe",
  "password": "NewPassword456@",
  "email": "john.updated@example.com",
  "firstName": "John",
  "lastName": "Updated",
  "role": "BSE"
}
```

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| id | Long | Yes | User ID to update |

**Request Body:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| username | String | Yes | Unique username |
| password | String | Yes | New password (will be encoded) |
| email | String | Yes | Email address |
| firstName | String | No | Updated first name |
| lastName | String | No | Updated last name |
| role | Enum | Yes | Updated role |

**Success Response (200):**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john.updated@example.com",
  "firstName": "John",
  "lastName": "Updated",
  "role": "BSE",
  "isActive": true,
  "createdAt": "2026-07-23T10:30:00",
  "updatedAt": "2026-07-23T11:00:00"
}
```

**Error Responses:**
| Status | Message | Description |
|--------|---------|-------------|
| 400 | User not found with id: 999 | User ID doesn't exist |
| 400 | Bad Request | Invalid role |

**cURL Example:**
```bash
curl -X PUT http://localhost:8080/emat/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "NewPassword456@",
    "email": "john.updated@example.com",
    "firstName": "John",
    "lastName": "Updated",
    "role": "BSE"
  }'
```

---

### 8. Toggle User Status
Activates or deactivates a user.

```http
PATCH /emat/api/users/{id}/toggle-status HTTP/1.1
```

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| id | Long | Yes | User ID to toggle |

**Success Response (200):**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "DIA",
  "isActive": false,
  "createdAt": "2026-07-23T10:30:00",
  "updatedAt": "2026-07-23T11:00:00"
}
```

**Error Responses:**
| Status | Message | Description |
|--------|---------|-------------|
| 400 | User not found with id: 999 | User ID doesn't exist |

**Note:** If `isActive` was `true`, it becomes `false`, and vice versa.

**cURL Example:**
```bash
curl -X PATCH http://localhost:8080/emat/api/users/1/toggle-status
```

---

### 9. Delete User
Permanently removes a user from the system.

```http
DELETE /emat/api/users/{id} HTTP/1.1
```

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| id | Long | Yes | User ID to delete |

**Success Response (204):**
```
(No content returned)
```

**Error Responses:**
| Status | Message | Description |
|--------|---------|-------------|
| 400 | User not found with id: 999 | User ID doesn't exist |

**cURL Example:**
```bash
curl -X DELETE http://localhost:8080/emat/api/users/1
```

---

## Role Enum

Available roles in the system:

| Role | Description |
|------|-------------|
| DIA | Department Internal Audit |
| BSE | Bombay Stock Exchange |
| GT_FIELD_TEAM | Ground Team Field |
| GT_PMU | Ground Team PMU |
| SIDBI_SDE | SIDBI Software Development Engineer |
| SIDBI_RO | SIDBI Regional Officer |
| SIDBI_HO_MAKER | SIDBI Head Office Maker |
| SIDBI_HO_CHECKER | SIDBI Head Office Checker |
| CLUSTER_EXPERT | Cluster Expert |

---

## HTTP Status Codes

| Code | Meaning | Description |
|------|---------|-------------|
| 200 | OK | Request succeeded |
| 201 | Created | Resource created successfully |
| 204 | No Content | Request succeeded, no content returned |
| 400 | Bad Request | Invalid request parameters |
| 401 | Unauthorized | Authentication required |
| 403 | Forbidden | Access denied |
| 404 | Not Found | Resource not found |
| 500 | Internal Server Error | Server error occurred |

---

## Request/Response Examples

### Complete Create User Example

**Request:**
```bash
curl -X POST http://localhost:8080/emat/api/users \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "username": "alice_smith",
    "password": "AliceSecure@123",
    "email": "alice.smith@emat.com",
    "firstName": "Alice",
    "lastName": "Smith",
    "role": "SIDBI_RO"
  }'
```

**Response (201):**
```json
{
  "id": 6,
  "username": "alice_smith",
  "email": "alice.smith@emat.com",
  "firstName": "Alice",
  "lastName": "Smith",
  "role": "SIDBI_RO",
  "isActive": true,
  "createdAt": "2026-07-23T14:25:30",
  "updatedAt": "2026-07-23T14:25:30"
}
```

---

## Testing with Postman

### Import Collection

1. Open Postman
2. Create new requests:
   - `GET /emat/health`
   - `POST /emat/api/users`
   - `GET /emat/api/users`
   - `GET /emat/api/users/{id}`
   - `GET /emat/api/users/username/{username}`
   - `PUT /emat/api/users/{id}`
   - `PATCH /emat/api/users/{id}/toggle-status`
   - `DELETE /emat/api/users/{id}`

3. Set Base URL: `http://localhost:8080/emat`
4. Add body as JSON for POST/PUT requests
5. Execute requests

---

## Rate Limiting

Currently, no rate limiting is configured. In production, implement:
- Spring Cloud RateLimiter
- Bucket4j
- Custom interceptor

---

## Pagination (Future Enhancement)

Recommended for future implementation:
```http
GET /emat/api/users?page=0&size=20&sort=id,desc
```

---

## Filtering (Future Enhancement)

Recommended for future implementation:
```http
GET /emat/api/users?role=DIA&active=true
```

---

## Swagger/OpenAPI Documentation (Future)

Add to pom.xml for auto-generated API docs:
```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.0.0</version>
</dependency>
```

Access at: `http://localhost:8080/emat/swagger-ui.html`

---

## API Versioning (Future)

Implement versioning:
- `/emat/v1/api/users` - Version 1
- `/emat/v2/api/users` - Version 2

---

**API Version:** 1.0
**Last Updated:** July 23, 2026
**Status:** Production Ready

