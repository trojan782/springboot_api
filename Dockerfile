FROM eclipse-temurin:17-jdk as builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

COPY src src

# execute permission to gradlew
RUN chmod +x gradlew

RUN ./gradlew build -x test

# runtime image

FROM eclipse-temurine:17-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]