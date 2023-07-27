# Use a base image with JDK 17 and Maven pre-installed
FROM maven:3.8.3-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY ./ /app/

# Build the Maven project
RUN mvn clean install -DskipTests

# Use a lightweight base image with JRE 17 to run the application
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the first stage
COPY --from=build /app/target/match-*.jar /app/match.jar

# Expose the port on which the application will listen (adjust if needed)
EXPOSE 8080

# Command to run the application when the container starts
CMD ["java", "-jar", "match.jar"]
