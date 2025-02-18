# Dockerfile para el servicio de kafkas

# Etapa de construcción
FROM eclipse-temurin:22-jdk AS buildstage

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src

RUN mvn clean package -Dmaven.test.skip=true

# Etapa de producción
FROM eclipse-temurin:22-jdk

COPY --from=buildstage /app/target/kafka-0.0.1-SNAPSHOT.jar /app/kafka.jar

EXPOSE 8081

ENTRYPOINT [ "java", "-jar", "/app/kafka.jar" ]