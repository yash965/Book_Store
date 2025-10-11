# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:24-jdk-jammy

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper and the project description file
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies (this is cached)
RUN ./mvnw dependency:go-offline

# Copy the rest of the application's source code
COPY src ./src

# Package the application
RUN ./mvnw package -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# The command to run the application
# IMPORTANT: Change 'your-app-name-0.0.1-SNAPSHOT.jar' to your actual JAR file name
ENTRYPOINT ["java", "-jar", "target/your-app-name-0.0.1-SNAPSHOT.jar"]