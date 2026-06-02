# test-fullstack

## Versiones

### Backend (`backend-auth/`)
- Java 8
- Spring Boot 2.7.18
- Maven 3.8.6 (en Docker) / Maven 3.6+ (local)

### Frontend (`frontend-auth/`)
- Node 18
- Angular 16.2.x
- TypeScript 5.1.x
- Nginx (Alpine)

---

## Ejecutar con Docker Compose

**Requisito:** Tener Docker y Docker Compose instalados.

```bash
# Si el usuario no está en el grupo docker, añadirlo:
sudo usermod -aG docker $USER
# Cerrar sesión y volver a entrar, o ejecutar: newgrp docker

# Construir y levantar los servicios:
sudo docker compose up --build

# Para detener:
sudo docker compose down
```

- Frontend: http://localhost:4200
- Backend: http://localhost:8080

---

## Ejecutar localmente (sin Docker)

### Backend

**Requisito:** Tener Java 8 y Maven instalados.

```bash
cd backend-auth

# Descargar dependencias con Maven
mvn dependency:resolve

# Ejecutar la aplicación
mvn spring-boot:run
```

Backend disponible en http://localhost:8080

### Frontend

**Requisito:** Tener Node 18 y npm instalados.

```bash
cd frontend-auth

# Descargar dependencias con npm
npm install

# Ejecutar en modo desarrollo
ng serve

# Si ng no está instalado globalmente, usar:
npx ng serve
```

Frontend disponible en http://localhost:4200

---

## Credenciales de prueba

- Email: `admin@admin.com`
- Password: `password123`
