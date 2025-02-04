# Compilación
FROM eclipse-temurin:21-jdk AS buildstage 

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src
COPY Wallet_LEGX51JUNINIVTKL /app/wallet

ENV TNS_ADMIN=/app/wallet

RUN mvn clean package

# Ejecución
FROM eclipse-temurin:21-jdk

COPY --from=buildstage /app/target/prod-cons-0.0.1-SNAPSHOT.jar /app/app.jar

# Creación de directorio que almacena los json
RUN mkdir -p /app/data/json

COPY Wallet_LEGX51JUNINIVTKL /app/wallet

ENV TNS_ADMIN=/app/wallet
EXPOSE 8087

ENTRYPOINT [ "java", "-jar","/app/app.jar" ]

