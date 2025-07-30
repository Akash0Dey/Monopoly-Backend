FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy gradle files and source code
COPY . .

# Build the application
RUN chmod +x ./gradlew && \
    ./gradlew build --no-daemon -x test

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create data directory for H2 database
RUN mkdir -p /app/data && chmod 777 /app/data

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Environment variables with defaults
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application using the PORT that Render provides
CMD java -Dserver.port=${PORT:-8080} -jar app.jar
