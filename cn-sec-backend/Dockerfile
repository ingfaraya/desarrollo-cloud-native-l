# Etapa de construcción
FROM eclipse-temurin:17-jdk AS buildstage

RUN apt-get update && apt-get install -y maven

WORKDIR /app

# Copiar los archivos necesarios
COPY pom.xml .
COPY src /app/src

# Compilar y empaquetar el proyecto
RUN mvn clean package -DskipTests=true

# Verificar el contenido del directorio target
RUN ls -l /app/target

# Etapa de producción
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copiar el JAR generado
COPY --from=buildstage /app/target/ejemplo-0.0.1-SNAPSHOT.jar /app/ejemplo.jar

# Configurar el puerto y el punto de entrada
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/ejemplo.jar"]
