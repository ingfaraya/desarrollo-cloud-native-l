#Compilación
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

COPY --from=buildstage /app/target/cons-0.0.1-SNAPSHOT.jar /app/app.jar

COPY Wallet_LEGX51JUNINIVTKL /app/wallet

ENV TNS_ADMIN=/app/wallet
EXPOSE 8082

ENTRYPOINT [ "java", "-jar","/app/app.jar" ]
