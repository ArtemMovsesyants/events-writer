FROM eclipse-temurin:21-jdk as builder

WORKDIR /app

COPY . .

RUN ./gradlew clean bootJar -x test

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /app/build/libs/events-writer.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
