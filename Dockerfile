FROM openjdk:11

WORKDIR /app

COPY gradle/ gradle
COPY gradlew build.gradle.kts settings.gradle.kts ./

COPY src ./src

CMD ["./gradlew", "bootRun"]