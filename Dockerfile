# Multi-stage build for JavaFX Particle Simulator

# Stage 1: Build with Maven
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime with JavaFX support
FROM eclipse-temurin:17-jre

WORKDIR /app

# Install required libraries for JavaFX (headless support + X11)
RUN apt-get update && apt-get install -y \
    libgl1-mesa-glx \
    libgtk-3-0 \
    libxtst6 \
    libxxf86vm1 \
    xvfb \
    && rm -rf /var/lib/apt/lists/*

# Copy built JAR from build stage
COPY --from=build /app/target/particle-simulator-*.jar app.jar

# Environment variables for headless mode (if needed)
ENV DISPLAY=:99

# Expose port (if adding web features later)
EXPOSE 8080

# Run with Xvfb for headless environments (cloud deployment)
# For local development with GUI, override with: docker run -e DISPLAY=$DISPLAY
CMD ["java", "-jar", "app.jar"]
