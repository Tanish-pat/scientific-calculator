# FROM eclipse-temurin:17-jdk-alpine
FROM alpine:17-jdk-alpine
WORKDIR /app
# Copy the built JAR file into the container
COPY target/scientific-calculator-1.0-SNAPSHOT.jar /app/my_calculator.jar
ENTRYPOINT ["java", "-jar", "/app/my_calculator.jar"]
