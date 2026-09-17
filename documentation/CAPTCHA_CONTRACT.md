# EMAT CAPTCHA Contract

Backend-managed CAPTCHA protecting the login endpoint (`POST /users/login`). Generation and
validation are handled **entirely on the backend** - no external CAPTCHA provider is used.

## 1. Flow

1. Frontend calls `GET /captcha` (public, no auth) to obtain a CAPTCHA id and image.
2. Frontend renders the image (a `data:` URI) inside the login form and prompts the user to type the
   characters.
3. Frontend submits `POST /users/login` including the `captchaId` and the user-entered `captchaAnswer`.
4. Backend validates the answer **before** authenticating the credentials. A CAPTCHA is **single-use**
   - it is consumed (invalidated) on the first validation attempt, success or failure.
5. On success the normal login payload is returned. On captcha failure the request is rejected with
   `400 Bad Request` so the frontend should fetch a fresh CAPTCHA.

## 2. Endpoints

### GET /emat/v1/captcha

```json
{
  "status": 200,
  "message": "Captcha generated successfully",
  "data": {
    "captchaId": "3b6c0f1e-9a24-4c5d-8f10-abcdef123456",
    "image": "data:image/png;base64,iVBORw0KGgo...",
    "expiresInSeconds": 300
  }
}
```

- `captchaId`: opaque id; echo it back in the login request.
- `image`: PNG encoded as a data URI - use directly as `src` of an `<img>` (or in a canvas).
- `expiresInSeconds`: the CAPTCHA becomes invalid after this long (default `300`);

### POST /emat/v1/users/login

```json
{
  "username": "ravikant.rai@in.gt.com",
  "password": "ENC:....",
  "captchaId": "3b6c0f1e-9a24-4c5d-8f10-abcdef123456",
  "captchaAnswer": "AbC4k"
}
```

- `password` follows the existing PII encryption contract (`ENC:...`), unchanged.
- `captchaId` / `captchaAnswer` are **plain text** (not PII).
- Answer comparison is case-insensitive (whitespace trimmed).

### Failure responses

| Case | HTTP | `detail` |
|---|---|---|
| Missing/blank captcha fields | 400 | `Invalid or expired captcha...` |
| Wrong answer | 400 | `Invalid or expired captcha...` |
| Expired captcha (past `expiresInSeconds`) | 400 | `Invalid or expired captcha...` |
| Reused captcha id | 400 | `Invalid or expired captcha...` |

The message does not distinguish the failure reason (defense-in-depth).

## 3. Frontend example

```js
async function loadCaptcha() {
  const res = await fetch("/emat/v1/captcha");
  const { data } = await res.json();
  document.getElementById("captchaImg").src = data.image;
  return data.captchaId;
}

async function submitLogin(captchaId) {
  const answer = document.getElementById("captchaInput").value;
  const body = {
    username: form.username.value,
    password: await encryptPii(form.password.value, SECRET_KEY), // existing contract
    captchaId,
    captchaAnswer: answer,
  };
  const res = await fetch("/emat/v1/users/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  });
  if (res.status === 400) {
    // invalid/expired captcha - refresh the image and let the user retry
    captchaId = await loadCaptcha();
    showError("Invalid or expired captcha. Please try again.");
  }
}
```

## 4. Configuration

All values are in `application.yaml`, overridable per environment via profile properties or env vars:

| Property | Default | Notes |
|---|---|---|
| `security.captcha.enabled` (`SECURITY_CAPTCHA_ENABLED`) | `true` | Set `false` to bypass captcha (development/test/CI only). |
| `security.captcha.length` | `5` | Characters per captcha (`4..8`). |
| `security.captcha.expiry-in-seconds` | `300` | Time-to-live; minimum `30`. |
| `security.captcha.image.width` | `180` | Image width in px. |
| `security.captcha.image.height` | `60` | Image height in px. |

When `security.captcha.enabled=false`, login works without `captchaId`/`captchaAnswer`.

## 5. Security notes

- The expected answer is stored **only server-side** (in-memory Caffeine cache, TTL-bound) as a
  SHA-256 digest - it is never returned to the client.
- The CAPTCHA is consumed atomically, so a successful image/answer pair cannot be replayed.
- Max ~10,000 outstanding (unused) captchas are retained; entries expire automatically.
- The store is per application instance. For a multi-node deployment, share the store (e.g. Redis)
  or use sticky sessions so a CAPTCHA generated on one node is validated on the same node.
- The image is rendered locally with Java2D (no third-party rendering dependency), with rotated
  characters, noise lines and dot noise to hinder OCR.