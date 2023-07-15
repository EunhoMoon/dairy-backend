# Base image
FROM openjdk:17-jdk

# Set the working directory
WORKDIR /app

# Copy Gradle build files
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle ./gradle

# Copy source code
COPY src ./src

# Build the project
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# Expose the application port
EXPOSE 5555

# Run the application
CMD ["java", "-jar", "build/libs/myapp.jar"]
