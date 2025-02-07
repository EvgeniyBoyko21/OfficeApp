
FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test --no-daemon
RUN ls -la /app/build/libs/
FROM openjdk:17-jdk-slim
WORKDIR /OfficeApp
COPY --from=build /app/build/libs/OfficeApp-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]