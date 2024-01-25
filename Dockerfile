FROM amazoncorretto:19 AS builder
WORKDIR /app
COPY gradle/ gradle/
COPY gradlew build.gradle settings.gradle ./
RUN chmod +x ./gradlew
RUN ./gradlew --no-daemon dependencies
COPY src/ src/
RUN ./gradlew --no-daemon build

FROM amazoncorretto:19
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "/app/account-service.jar"]
COPY --from=builder /app/build/libs/account-service-*.jar account-service.jar