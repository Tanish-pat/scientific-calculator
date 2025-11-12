# FROM openjdk:17-jdk-alpine
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Copy the built JAR file into the container
COPY target/scientific-calculator-1.0-SNAPSHOT.jar /app/calculator.jar
ENTRYPOINT ["java", "-jar", "/app/calculator.jar"]
