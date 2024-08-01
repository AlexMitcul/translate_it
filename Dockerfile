# Use a Maven base image for building the application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven POM file and download dependencies
COPY ./pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY ./src/ ./src
RUN mvn package

# Use a lightweight image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/translate_it-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
