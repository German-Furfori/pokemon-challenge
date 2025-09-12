FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /pokeapp

COPY --from=builder /app/target/pokeapp-0.0.1-SNAPSHOT.jar pokeapp.jar

ENTRYPOINT ["java", "-jar", "pokeapp.jar"]