# Stage 1: Build Spring Boot App

FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first
COPY pom.xml .

# Download dependencies (used cache)
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build JAR file (old build file removed test file does not run)
RUN mvn clean package -DskipTests


# Stage 2: Run Spring Boot App
# to run docker application use Java 17 environment
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Spring Boot port ( container ke andar application 8085 port par listen karne wali hai.)
EXPOSE 8085

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"]