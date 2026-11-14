# Forgot Password & Change Password — Frontend Integration Guide

This guide covers the two password flows. Only **forgot password** uses an emailed **6-digit OTP**:

| Flow | Who | Login needed? | Endpoints |
|---|---|---|---|
| **Forgot password** | User who can't log in | No | `POST /auth/forgot-password` → `POST /auth/verify-otp` → `POST /auth/reset-password` |
| **Change password** | Logged-in user | Yes (JWT) | `POST /users/change-password` (no OTP) |

**Base URL:** `{host}:8086/emat/v1` (for example `http://localhost:8086/emat/v1/auth/forgot-password`).

---

## 1. Encrypting fields (read this first)

Passwords in these requests are **not sent as plain text**. They are encrypted with the **same RSA public key used for login**. This works before login, so both flows use the same method.

| Field | Encryption |
|---|---|
| `newPassword`, `confirmPassword`, `currentPassword` | **RSA-OAEP / SHA-256** with the key from `GET /auth/public-key`, sent as **standard base64** (no `ENC:` prefix) |
| `email`, `otp`, `resetToken` | **Plain text**, not encrypted |

> These flows do **not** use the AES key from `GET /pii-encryption-key`.

### Get the public key

```http
GET /emat/v1/auth/public-key
```

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

### Helper (Web Crypto, the same code as login)

```js
const API = "http://localhost:8086/emat/v1";

let rsaKeyPromise = null;

async function getRsaKey() {
  if (!rsaKeyPromise) {
    rsaKeyPromise = (async () => {
      const res = await fetch(`${API}/auth/public-key`);
      const { data } = await res.json();
      const der = Uint8Array.from(atob(data.publicKey), c => c.charCodeAt(0));
      return crypto.subtle.importKey(
        "spki", der, { name: "RSA-OAEP", hash: "SHA-256" }, false, ["encrypt"]);
    })();
  }
  return rsaKeyPromise;
}

/** Encrypts a string with RSA-OAEP/SHA-256 and returns standard base64. */
async function rsaEncrypt(plainText) {
  const key = await getRsaKey();
  const cipher = await crypto.subtle.encrypt(
    { name: "RSA-OAEP" }, key, new TextEncoder().encode(plainText));
  return btoa(String.fromCharCode(...new Uint8Array(cipher)));
}

/** Call this if the server says the value "was encrypted with a mismatched public key". */
function resetRsaKey() { rsaKeyPromise = null; }
```

> The server key can change when the backend restarts. If you get a 400 containing `mismatched public key`, call `resetRsaKey()`, encrypt again and retry once.

---

## 2. Response formats

**Success:** always the standard wrapper:

```json
{ "status": 200, "message": "Human readable message", "data": { } }
```

**Error:** Problem Details. **Show `detail` to the user**; it is written to be user-facing.

```json
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "Invalid or expired OTP. 3 attempt(s) left.",
  "timestamp": "2026-11-09T10:15:30.123"
}
```

| HTTP status | Meaning |
|---|---|
| `200` | Success |
| `400` | Validation / OTP / token / password-rule problem: show `detail` |
| `401` / `403` | (Change password only) not logged in or session expired: send the user to login |
| `503` | The OTP email could not be sent: "Please try again later" |

---

## 3. Rules the UI should know

| Rule | Value |
|---|---|
| OTP format | 6 digits (`^\d{6}$`) |
| OTP valid for | **5 minutes** |
| Wait before "Resend OTP" | **60 seconds** |
| Wrong OTP attempts | **5**, then the OTP is cancelled and the user must request a new one |
| Reset token valid for (forgot flow) | **10 minutes**, single use |
| Requesting a new OTP | Cancels the previous OTP and reset token |

**New password rules** (check them in the UI too, so users see them early):

- 8 to 64 characters
- At least 1 uppercase letter, 1 lowercase letter, 1 digit and 1 special character
- No space at the start or end
- `newPassword` must equal `confirmPassword`
- Must be different from the current password

```js
function passwordProblems(pwd, confirm) {
  const p = [];
  if (pwd.length < 8 || pwd.length > 64) p.push("8-64 characters");
  if (!/[A-Z]/.test(pwd)) p.push("an uppercase letter");
  if (!/[a-z]/.test(pwd)) p.push("a lowercase letter");
  if (!/\d/.test(pwd)) p.push("a digit");
  if (!/[^A-Za-z0-9\s]/.test(pwd)) p.push("a special character");
  if (pwd !== pwd.trim()) p.push("no leading/trailing spaces");
  if (pwd !== confirm) p.push("passwords must match");
  return p; // empty array = OK
}
```

---

## 4. Forgot password flow (no login)

```
 [Login page] --"Forgot password?"--> Screen 1: Enter email
        |  POST /auth/forgot-password
        v
 Screen 2: Enter 6-digit OTP   (60s resend timer, 5 min expiry)
        |  POST /auth/verify-otp  -> resetToken
        v
 Screen 3: New password + confirm
        |  POST /auth/reset-password (with resetToken)
        v
 "Password reset successfully" -> back to Login
```

Keep `email` (plain, in memory) and `resetToken` in component/page state only. **Do not** put them in `localStorage` or the URL.

### Step 1 — Request OTP

```http
POST /emat/v1/auth/forgot-password
Content-Type: application/json
```

```json
{ "email": "user@example.com" }
```

**Response 200** (always the same, see note):

```json
{ "status": 200, "message": "If the email is registered, an OTP has been sent to it.", "data": null }
```

> **Important:** For security the server returns the **same 200 response** whether or not the email is registered, and also when "Resend" is pressed within 60 seconds (in that case no new email is sent).
> - Always move to the OTP screen and show: *"If this email is registered, you will receive an OTP shortly."*
> - **Show your own 60-second countdown** before enabling "Resend OTP". The server won't report it.

Possible errors: `400 "Email is required"`, `503` (email failed).

### Step 2 — Verify OTP

```http
POST /emat/v1/auth/verify-otp
Content-Type: application/json
```

```json
{
  "email": "user@example.com",
  "otp": "482913"
}
```

**Response 200:**

```json
{
  "status": 200,
  "message": "OTP verified successfully",
  "data": {
    "resetToken": "q8Jx3...base64url...Zk",
    "expiresInSeconds": 600
  }
}
```

Save `resetToken` and move to Screen 3. The OTP is now used up; the token lasts 10 minutes.

### Step 3 — Set new password

```http
POST /emat/v1/auth/reset-password
Content-Type: application/json
```

```json
{
  "email": "user@example.com",
  "resetToken": "q8Jx3...base64url...Zk",
  "newPassword": "<rsaEncrypt('NewPass@123')>",
  "confirmPassword": "<rsaEncrypt('NewPass@123')>"
}
```

**Response 200:**

```json
{ "status": 200, "message": "Password reset successfully. Please log in with your new password.", "data": null }
```

Show a success message and go to the login page.

`resetToken` is required. The OTP itself is **not** accepted by this endpoint; always call `verify-otp` first.

**If the new password breaks a rule** (e.g. same as the current one), the server returns 400 and the **reset token stays valid**. Let the user fix the password and submit again on the same screen.

### Forgot-password error messages (`detail`)

| `detail` | Step | What to do in the UI |
|---|---|---|
| `Email is required` | 1, 2, 3 | Highlight the email field |
| `OTP is required` | 2 | Highlight the OTP field |
| `Invalid or expired OTP` | 2 | Show the message; offer "Resend OTP" |
| `Invalid or expired OTP. N attempt(s) left.` | 2 | Show the message with the remaining count |
| `Too many incorrect attempts. Please request a new OTP.` | 2 | Clear the OTP field; enable "Resend OTP" |
| `Reset token is required. Please verify the OTP first.` | 3 | Frontend bug: token missing from state |
| `Invalid or expired reset token. Please verify the OTP again.` | 3 | Send the user back to Step 1 (request a new OTP) |
| `New password and confirm password do not match` | 3 | Highlight confirm field |
| `Password must be between 8 and 64 characters` | 3 | Show the password rules |
| `Password must contain at least one uppercase letter, one lowercase letter, one digit and one special character` | 3 | Show the password rules |
| `Password must not start or end with a space` | 3 | Show the password rules |
| `New password must be different from the current password` | 3 | Ask for a different password (token still valid) |
| `Unable to send the OTP email right now. Please try again later.` (503) | 1 | Show the message; allow retry right away |

---

## 5. Change password flow (logged in)

```
 [Profile / Settings] -> "Change password" screen
   Fields: Email, Current password, New password, Confirm password
        |
        | click "Change password" -> POST /users/change-password
        v
 "Password changed successfully"
```

No OTP is used. The call needs the logged-in session, exactly like every other protected API: the `Authorization: Bearer <token>` header **or** the auth cookie (`credentials: "include"`). The `email` must be the logged-in user's registered email.

```http
POST /emat/v1/users/change-password
Authorization: Bearer <jwt>
Content-Type: application/json
```

```json
{
  "email": "user@example.com",
  "currentPassword": "<rsaEncrypt('OldPass@123')>",
  "newPassword": "<rsaEncrypt('NewPass@456')>",
  "confirmPassword": "<rsaEncrypt('NewPass@456')>"
}
```

**Response 200:**

```json
{ "status": 200, "message": "Password changed successfully", "data": null }
```

Show a success message and clear the form. The user stays logged in. (Optionally log them out and ask them to log in again with the new password.)

### Change-password error messages (`detail`)

| `detail` | What to do in the UI |
|---|---|
| `Email is required` / `Current password is required` | Highlight the field |
| `Email does not match your account` | Highlight the email field |
| `Current password is incorrect` | Highlight current password |
| `New password and confirm password do not match` | Highlight confirm field |
| `Password must be between 8 and 64 characters` / `Password must contain ...` / `Password must not start or end with a space` | Show the password rules |
| `New password must be different from the current password` | Ask for a different password |
| `This account is inactive` | Tell the user to contact the administrator |
| HTTP 401 / 403 | Session expired: redirect to login |

---

## 6. Full example (forgot password)

```js
async function post(path, body, opts = {}) {
  const res = await fetch(`${API}${path}`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...(opts.headers || {}) },
    credentials: "include",
    body: body ? JSON.stringify(body) : undefined,
  });
  const json = await res.json().catch(() => ({}));
  if (!res.ok) throw new Error(json.detail || "Something went wrong");
  return json;
}

// Screen 1
await post("/auth/forgot-password", { email });

// Screen 2
const { data } = await post("/auth/verify-otp", { email, otp });
const resetToken = data.resetToken;

// Screen 3
await post("/auth/reset-password", {
  email,
  resetToken,
  newPassword: await rsaEncrypt(newPassword),
  confirmPassword: await rsaEncrypt(confirmPassword),
});
```

## 7. Full example (change password)

```js
const auth = { headers: { Authorization: `Bearer ${jwt}` } }; // or rely on the auth cookie

await post("/users/change-password", {
  email,
  currentPassword: await rsaEncrypt(currentPassword),
  newPassword: await rsaEncrypt(newPassword),
  confirmPassword: await rsaEncrypt(confirmPassword),
}, auth);
```

---

## 8. Frontend checklist

- [ ] "Forgot password?" link on the login page opens the email screen
- [ ] Password fields are RSA-encrypted with `/auth/public-key`; email, OTP and reset token are sent as plain text
- [ ] On a `mismatched public key` error: refetch the key, encrypt again, retry once
- [ ] OTP input accepts exactly 6 digits (numeric keyboard on mobile)
- [ ] 60-second countdown before "Resend OTP" (forgot password)
- [ ] Show the 5-minute OTP validity to the user
- [ ] After `forgot-password`, always go to the OTP screen with the neutral "If this email is registered..." message
- [ ] Keep `resetToken` in memory only; on `Invalid or expired reset token` go back to step 1
- [ ] Live password-rule checklist under the new password field
- [ ] Show the server's `detail` text for every 400 / 503
- [ ] Change password: send the logged-in user's email; on 401/403 redirect to login
- [ ] Clear all password/OTP fields after success

## 9. Testing tips (backend team)

- The OTP arrives by email. The backend needs SMTP configured (`MAIL_USERNAME`, `MAIL_PASSWORD`, optional `MAIL_FROM`); otherwise OTP requests return **503**.
- Each user can only request a new OTP once per 60 seconds; wait between tests.
- Swagger UI (`/emat/v1/swagger-ui.html`) lists all five endpoints under *Pre-authentication* and *user-controller*.
