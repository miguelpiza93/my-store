FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package

# Utiliza una imagen base de Java 17
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=build /app/target/my-store-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]