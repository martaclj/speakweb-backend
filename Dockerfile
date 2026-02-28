# Etapa 1: Build con Maven y Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
# para evitar copiar todo
COPY src ./src
#COPY . .

RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera para ejecutar la app
FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=build /app/target/speakweb-backend-0.0.1-SNAPSHOT.jar app.jar

# Creación de carpeta de uploads para guardar fotos
RUN mkdir -p uploads

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]