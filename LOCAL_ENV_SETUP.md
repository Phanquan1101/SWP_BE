# Local Environment Setup

`.env.example` is a template only. Spring Boot does not auto-load `.env` out of the box.

Recommended minimal local approach for this project:

1. Copy `.env.example` to your own local values file (for reference only), then set the same keys in your shell or IDE run configuration.
2. Export env vars before running:
   - `PORT`
   - `DB_URL`
   - `DB_USER`
   - `DB_PASSWORD`
   - `JWT_SECRET`
3. Run:
   - `mvn spring-boot:run`

Example PowerShell session:

```powershell
$env:PORT="8080"
$env:DB_URL="jdbc:mysql://localhost:3306/waste_platform?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true"
$env:DB_USER="root"
$env:DB_PASSWORD="your_password"
$env:JWT_SECRET="your_local_jwt_secret_at_least_32_chars"
mvn spring-boot:run
```

Do not commit real secrets to git.
