# Paso 1: Usar una imagen de Maven con Java 8 para compilar el proyecto
FROM maven:3.6.3-openjdk-8-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvc clean package -DskipTests

# Paso 2: Usar una imagen ligera de JRE 8 para ejecutar la aplicación
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]