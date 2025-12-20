# Pocket Wise

This is a Spring Boot application that integrates with Open Banking provider to provide account and transaction data for personal spending insights.

_Important: Encrypted configuration properties are managed via Jasypt. A Jasypt encryptor password is required at runtime. You must request this password from the project developers._

### Prerequisites
- Java 21
- Maven 3.9+
- Network access to SIBS sandbox (for the `sandbox` profile)

### Run from IntelliJ IDEA
- Open Run/Debug Configuration for `Application`.
- VM options: `-Djasypt.encryptor.password=YOUR_PASSWORD`
- Copy the certificate `(private-key.pem)` to the `src/main/resources` folder. (ask the admin)
- Rename the [.application-template.yaml](src/main/resources/.application-template.yaml) to `application.yaml` and change the necessary properties.
- Run.

### Expose with ngrok

To expose the application publicly (useful for OAuth callbacks, webhook testing, or Enable Banking redirects):

1. **Install ngrok** (if not already installed):
   ```bash
   brew install ngrok
   ```

2. **Authenticate ngrok** (Get auth token at [ngrok.com/dashboard](https://dashboard.ngrok.com/get-started/your-authtoken)):
   ```bash
   ngrok config add-authtoken YOUR_NGROK_AUTH_TOKEN
   ```

3. **Start ngrok tunnel**:
   ```bash
   ngrok http --url=convenient-judi-spectrochemical.ngrok-free.dev 8080
   ```

4. **Copy the public URL** from the ngrok output:
   ```
   Forwarding  https://convenient-judi-spectrochemical.ngrok-free.dev -> http://localhost:8080
   ```

   Use this HTTPS URL for OAuth redirect URIs or webhook endpoints. Note that the URL changes each time ngrok restarts (unless you have a paid plan with reserved domains).

## API Docs
When the app is running, OpenAPI UI is usually available at:

`https://convenient-judi-spectrochemical.ngrok-free.dev/swagger-ui/index.html`

## Troubleshooting
- Error about "Jasypt encryptor password is not set" or failures decrypting `ENC(...)`:
    - Ensure you passed the password via `-Djasypt.encryptor.password` or `JASYPT_ENCRYPTOR_PASSWORD`.
- ngrok tunnel not connecting:
    - Verify the Spring Boot app is running on port 8080
    - Check your ngrok auth token is configured correctly
    - Ensure no firewall is blocking ngrok