# Pocket Wise

This is a Spring Boot application that integrates with Open Banking provider to provide account and transaction data for personal spending insights.

Important: Encrypted configuration properties are managed via Jasypt. A Jasypt encryptor password is required at runtime. You must request this password from the project developers.

## Prerequisites
- Java 21
- Maven 3.9+
- Network access to SIBS sandbox (for the `sandbox` profile)

## Run the application (Maven)

Execute the following command from the project root:

`mvn -Dspring-boot.run.profiles=sandbox -Djasypt.encryptor.password=YOUR_PASSWORD spring-boot:run`

## Run from IDE (IntelliJ IDEA)
- Open Run/Debug Configuration for `Application`.
- VM options: `-Djasypt.encryptor.password=YOUR_PASSWORD`
- Run.

## API Docs
When the app is running, OpenAPI UI is usually available at:

`http://localhost:8080/swagger-ui/index.html`

## Troubleshooting
- Error about "Jasypt encryptor password is not set" or failures decrypting `ENC(...)`:
- Ensure you passed the password via `-Djasypt.encryptor.password` or `JASYPT_ENCRYPTOR_PASSWORD`.
