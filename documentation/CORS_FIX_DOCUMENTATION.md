# CORS Configuration Fix - EMAT Application

## Problem
The Swagger UI was unable to make API requests to the `/users/login` endpoint due to CORS (Cross-Origin Resource Sharing) policy restrictions. The error was:

```
Failed to fetch.
Possible Reasons:
- CORS
- Network Failure
- URL scheme must be "http" or "https" for CORS request.
```

## Solution Implemented

### 1. Created CorsConfig.java
A new configuration file was created at:
`src/main/java/org/emat/config/CorsConfig.java`

This configuration:
- Allows all origins (via wildcard patterns)
- Allows all common HTTP methods (GET, POST, PUT, DELETE, OPTIONS, PATCH)
- Allows all headers
- Enables credentials (cookies, authorization headers)
- Exposes Authorization and Content-Type headers
- Caches preflight requests for 1 hour

### 2. Updated SecurityConfig.java
Modified the `SecurityFilterChain` bean to include CORS configuration:
- Added import for `org.springframework.web.cors.CorsConfigurationSource`
- Added `CorsConfigurationSource` parameter to the `securityFilterChain` method
- Added `.cors(cors -> cors.configurationSource(corsConfigurationSource))` to enable CORS

### 3. Fixed OpenApiConfig.java
Corrected the SecurityScheme type from `Type.HTTPS` to `Type.HTTP` for proper JWT bearer authentication in Swagger UI.

## Files Modified

1. **src/main/java/org/emat/config/CorsConfig.java** (NEW)
   - Configures CORS policies for the application

2. **src/main/java/org/emat/config/SecurityConfig.java** (MODIFIED)
   - Added CORS support to Spring Security filter chain

3. **src/main/java/org/emat/config/OpenApiConfig.java** (MODIFIED)
   - Fixed SecurityScheme type for proper Swagger authentication

## Next Steps

### 1. Rebuild and Restart the Application
```bash
mvn clean package
mvn spring-boot:run
```

Or if running via IDE, restart the application.

### 2. Test the Login Endpoint

#### Via Swagger UI:
1. Navigate to: `http://api.emat.metaversedu.in/emat/swagger-ui.html`
2. Find the `/users/login` endpoint
3. Click "Try it out"
4. Enter credentials:
   ```json
   {
     "username": "admin",
     "password": "Password@123"
   }
   ```
5. Click "Execute"
6. You should now receive a successful response with a JWT token

#### Via cURL:
```bash
curl -X 'POST' \
  'http://api.emat.metaversedu.in/emat/v1/users/login' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "admin",
  "password": "Password@123"
}'
```

### 3. Production Considerations

⚠️ **IMPORTANT**: The current CORS configuration allows ALL origins (`*`), which is suitable for development but NOT recommended for production.

For production, update `CorsConfig.java` to specify exact allowed origins:

```java
// Replace this line:
configuration.setAllowedOriginPatterns(Arrays.asList("*"));

// With specific origins:
configuration.setAllowedOrigins(Arrays.asList(
    "https://api.emat.metaversedu.in",
    "https://emat.metaversedu.in",
    "https://your-frontend-domain.com"
));
```

## Verification Checklist

- [x] CORS configuration created
- [x] SecurityConfig updated with CORS support
- [x] OpenApiConfig fixed
- [x] Project compiles successfully
- [ ] Application restarted with new configuration
- [ ] Login endpoint tested via Swagger UI
- [ ] CORS headers verified in browser network tab

## How CORS Works

When a browser makes a cross-origin request:

1. **Preflight Request**: Browser sends an OPTIONS request to check if the actual request is allowed
2. **CORS Headers**: Server responds with headers indicating what's allowed:
   - `Access-Control-Allow-Origin`: Which origins can access
   - `Access-Control-Allow-Methods`: Which HTTP methods are allowed
   - `Access-Control-Allow-Headers`: Which headers can be sent
   - `Access-Control-Allow-Credentials`: Whether credentials are allowed

3. **Actual Request**: If preflight succeeds, browser sends the actual request

Our configuration handles all of this automatically.

## Troubleshooting

If you still encounter CORS issues after restarting:

1. **Clear browser cache**: Hard refresh (Ctrl+Shift+R)
2. **Check browser console**: Look for specific CORS error messages
3. **Verify configuration**: Ensure the application restarted with new config
4. **Check network tab**: Inspect the response headers to see if CORS headers are present
5. **Test with cURL**: Bypass browser to verify server is responding correctly

## Additional Resources

- [Spring CORS Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-cors)
- [MDN CORS Guide](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS)
- [Spring Security CORS](https://docs.spring.io/spring-security/reference/servlet/integrations/cors.html)

