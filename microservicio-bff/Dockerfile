# Dockerfile para el servicio de microservicio-bffs

# Etapa de construcción
FROM eclipse-temurin:22-jdk AS buildstage

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src

EXPOSE 8082

ENTRYPOINT [ "java", "-jar", "/app/microservicio-bff.jar" ]