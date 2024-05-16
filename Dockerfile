# Utiliza una imagen base de Maven para compilar el proyecto
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Set Arg Values
ARG SPRING_DATASOURCE_URL
ARG POSTGRES_USER
ARG POSTGRES_PASSWORD

# Set environment variables
ENV SPRING_DATASOURCE_URL=$SPRING_DATASOURCE_URL
ENV POSTGRES_USER=$POSTGRES_USER
ENV POSTGRES_PASSWORD=$POSTGRES_PASSWORD

RUN apt-get update && apt-get install -y gettext-base
RUN envsubst < /app/src/main/resources/application.yml > /app/application-substituted.yml

# Move the substituted properties file to replace the original
RUN mv /app/application-substituted.yml /app/src/main/resources/application.yml

RUN mvn package -DskipTests

# Utiliza una imagen base de Java 17
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=build /app/target/my-store-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
