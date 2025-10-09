# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine
WORKDIR /app

COPY target/scientific-calculator-1.0-SNAPSHOT.jar /app/calculator.jar
ENTRYPOINT ["java", "-jar", "/app/calculator.jar"]
