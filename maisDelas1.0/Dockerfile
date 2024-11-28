FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml /app
RUN mvn dependency:go-offline -B
COPY src /app/src


RUN mvn clean install -DskipTests


FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/api-0.0.1-SNAPSHOT.jar /app/api.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/api.jar"]
