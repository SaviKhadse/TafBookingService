# Use an OpenJDK 17 image as the base
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY build/libs/taf-booking-service.jar app.jar

# Expose the port your application runs on (optional but recommended)
EXPOSE 8084

# Set the command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]