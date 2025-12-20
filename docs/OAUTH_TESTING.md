# Testing OAuth2 Authentication

This guide explains how to test the API's OAuth2 Resource Server authentication using Google ID tokens.

## Prerequisites

- The application running locally on `http://localhost:8080`
- A Google account
- Access to your Google OAuth credentials

## Getting a Google ID Token

### Step 1: Open Google OAuth Playground

Navigate to [Google OAuth Playground](https://developers.google.com/oauthplayground).

### Step 2: Configure OAuth Credentials

1. Click the **gear icon (⚙️)** in the top-right corner
2. Check **"Use your own OAuth credentials"**
3. Enter your credentials:
   - **OAuth Client ID**: Your Google Client ID
   - **OAuth Client Secret**: Your Google Client Secret
4. Close the settings panel

### Step 3: Select Scopes

In the left panel under "Select & authorize APIs":

1. Scroll down to **"Google OAuth2 API v2"**
2. Select the following scopes:
   - `openid`
   - `email`
   - `profile`

Alternatively, manually enter these scopes in the input box:
```
openid email profile
```

### Step 4: Authorize

1. Click **"Authorize APIs"**
2. Sign in with your Google account
3. Grant the requested permissions

### Step 5: Exchange Code for Tokens

1. Click **"Exchange authorization code for tokens"**
2. In the response on the right, locate the `id_token` field
3. Copy the entire token value (it's a long JWT string)

## Testing the API

### Authenticated Request

Use the ID token in the `Authorization` header:

```bash
curl -X GET "http://localhost:8080/api/accounts/{iban}" \
     -H "Authorization: Bearer <your_id_token>"
```

Replace `<your_id_token>` with the token copied from the OAuth Playground.

### Example Response (Success)

```json
{
  "iban": "NL91ABNA0417164300",
  "name": "Main Account",
  "currency": "EUR"
}
```

### Example Response (Unauthorized)

If the token is missing, invalid, or expired:

```json
{
  "error": "Unauthorized",
  "status": 401
}
```

## Verifying Security Configuration

### Test 1: Request Without Token

```bash
curl -v http://localhost:8080/api/accounts/test
```

**Expected**: HTTP 401 Unauthorized

### Test 2: Request With Invalid Token

```bash
curl -v -H "Authorization: Bearer invalid_token" \
     http://localhost:8080/api/accounts/test
```

**Expected**: HTTP 401 Unauthorized

### Test 3: Public Endpoint Access

```bash
curl -v http://localhost:8080/api/authorization/callback
```

**Expected**: HTTP 200 OK (or appropriate response, not 401)

## Token Details

The Google ID token is a JWT containing claims such as:

| Claim     | Description                            |
|-----------|----------------------------------------|
| `sub`     | Unique Google user ID                  |
| `email`   | User's email address                   |
| `name`    | User's full name                       |
| `picture` | URL to user's profile picture          |
| `iss`     | Issuer (`https://accounts.google.com`) |
| `exp`     | Expiration timestamp                   |

## Troubleshooting

### Token Expired

Google ID tokens expire after 1 hour. If you receive a 401 error with a previously working token, obtain a new one from the OAuth Playground.

### Invalid Issuer

Ensure the token was issued by Google. The API validates that `iss` claim equals `https://accounts.google.com`.

### Wrong Token Type

Make sure you're using the `id_token`, not the `access_token`. The `access_token` is for accessing Google APIs, while the `id_token` contains user identity claims.

## Mobile App Integration

For mobile applications, use the Google Sign-In SDK to obtain the ID token:

### Android (Credential Manager)

```kotlin
val idToken = googleIdTokenCredential.idToken
// Use this token in Authorization header
```

### iOS (Google Sign-In)

```swift
let idToken = user.idToken?.tokenString
// Use this token in Authorization header
```

The mobile app should include the token in every API request:

```
Authorization: Bearer <id_token>
```
