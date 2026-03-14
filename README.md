# JWT Auth Demo (Spring Boot) - with role-based protected endpoints

## What was added
- A `/api/protected` controller with two endpoints:
  - `GET /api/protected/user` — accessible to ROLE_USER or ROLE_ADMIN
  - `GET /api/protected/admin` — accessible only to ROLE_ADMIN
- `DataInitializer` that creates two demo users on startup:
  - `user` / `password` (ROLE_USER)
  - `admin` / `adminpass` (ROLE_ADMIN)

## Run
1. Build: `mvn clean package`
2. Run: `mvn spring-boot:run` or run the `JwtAuthDemoApplication` main class.

## Test via curl
1. Login as user:
```bash
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"username":"user","password":"password"}'
```
2. Use token to call user endpoint:
```bash
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/api/protected/user
```
3. Try admin endpoint as user (should be 403):
```bash
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/api/protected/admin
```
4. Login as admin:
```bash
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"username":"admin","password":"adminpass"}'
```
5. Call admin endpoint with admin token:
```bash
curl -H "Authorization: Bearer <ADMIN_TOKEN>" http://localhost:8080/api/protected/admin
```

## H2 console
Open `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:jwt-demo-db`, user `sa`, password blank).

## Notes
- This is a demo. Do NOT use `jwt.secret` from `application.properties` in production.
- Tokens expire after 1 hour (configured in `application.properties`).
