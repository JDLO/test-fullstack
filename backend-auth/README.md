# Backend Auth

## Versión
- Java 8
- Spring Boot 2.7.18
- Maven 3.8.6 (Docker) / Maven 3.6+ (local)

## Ejecutar con Docker Compose (desde la raíz del proyecto)
```bash
sudo docker compose up --build
```

## Ejecutar localmente

**Requisito:** Java 8 y Maven instalados.

```bash
cd backend-auth
mvn dependency:resolve
mvn spring-boot:run
```

Backend disponible en http://localhost:8080

## Endpoints
- `POST /api/auth/login` — Login con credenciales
- `GET /api/auth/sso` — Iniciar SSO
- `GET /api/auth/sso/callback?code=...` — Callback SSO

## Credenciales de prueba
- Email: `admin@admin.com`
- Password: `somepassword123`
