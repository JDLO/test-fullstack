# Frontend Auth

## Versión
- Node 18
- Angular 16.2.x
- TypeScript 5.1.x
- Nginx (Alpine, en Docker)

## Ejecutar con Docker Compose (desde la raíz del proyecto)
```bash
sudo docker compose up --build
```

Frontend disponible en http://localhost:4200

## Ejecutar localmente

**Requisito:** Node 18 y npm instalados.

```bash
cd frontend-auth
npm install
ng serve
# O si ng no está instalado globalmente: npx ng serve
```

Frontend disponible en http://localhost:4200

## Compilar para producción
```bash
ng build
```

Los archivos generados se guardan en `dist/frontend-auth/`.
