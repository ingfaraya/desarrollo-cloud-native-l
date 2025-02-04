# Dockerfile para el servicio de microservicios

# Etapa de construcción
FROM eclipse-temurin:21-jdk AS buildstage

# Instalar Maven para la construcción
RUN apt-get update && apt-get install -y maven

WORKDIR /app

# Copiar archivos necesarios para la construcción
COPY pom.xml .
COPY src /app/src
COPY Wallet_LEGX51JUNINIVTKL /app/oracle_wallet

# Configurar la variable de entorno para Oracle Wallet
ENV TNS_ADMIN=/app/oracle_wallet

# Construir el proyecto con Maven
RUN mvn clean package -DskipTests=true

# Etapa de producción
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copiar el archivo JAR generado desde la etapa de construcción
COPY --from=buildstage /app/target/microservicio-0.0.1-SNAPSHOT.jar /app/microservicio.jar
COPY Wallet_LEGX51JUNINIVTKL /app/oracle_wallet

# Configurar la variable de entorno para Oracle Wallet
ENV TNS_ADMIN=/app/oracle_wallet

# Exponer el puerto de la aplicación
EXPOSE 8081

# Definir el comando de entrada para ejecutar la aplicación
ENTRYPOINT [ "java", "-jar", "/app/microservicio.jar" ]
