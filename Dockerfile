#FROM openjdk:17-jdk-alpine
##FROM openjdk:23-jdk-slim-bookworm
#COPY target/eRepairShop-0.0.1-SNAPSHOT.jar .
#EXPOSE 8888
#ENTRYPOINT ["java", "-jar", "eRepairShop-0.0.1-SNAPSHOT.jar"]

# --------------------------
# Stage 1: Build the application
# --------------------------
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Download dependencies and build the application
RUN mvn clean package -DskipTests

# --------------------------
# Stage 2: Create the runtime image
# --------------------------
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar ./app.jar

# Expose the application port
EXPOSE 8888

# Run the application
CMD ["java", "-jar", "app.jar"]