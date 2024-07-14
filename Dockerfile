# BUILD
FROM gradle:8.9.0-jdk21 AS build

WORKDIR /home/gradle/src

COPY --chown=gradle:gradle . .

RUN gradle build --no-daemon -x test

# RUN
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/contacts-api-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
