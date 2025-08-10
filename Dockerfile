# Этап 1: Сборка приложения
FROM openjdk:17-jdk-slim AS builder
WORKDIR /app
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src/main/java ./src/main/java
COPY src/main/resources ./src/main/resources
COPY gradlew .
COPY gradle/wrapper/ ./gradle/wrapper/
RUN chmod +x gradlew
RUN ./gradlew build --no-daemon
RUN ./gradlew test --no-daemon

# Этап 2: Запуск приложения
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/build/libs/ConversionMicroservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]