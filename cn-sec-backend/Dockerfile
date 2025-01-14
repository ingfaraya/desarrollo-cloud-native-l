# Dockerfile para el servicio de cn-sec-backends

# Etapa de construcción
FROM eclipse-temurin:22-jdk AS buildstage

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src

RUN mvn clean package -Dmaven.test.skip=true

EXPOSE 8082

ENTRYPOINT [ "java", "-jar", "/app/cn-sec-backend.jar" ]