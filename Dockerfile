# Build stage
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

# Copy only the files needed for dependency resolution first
COPY gradle/ gradle/
COPY gradlew build.gradle settings.gradle ./

# Download dependencies - this layer will be cached if dependencies don't change
RUN chmod +x gradlew && \
    ./gradlew dependencies --no-daemon

# Copy source code
COPY src/ src/

# Build the application
RUN ./gradlew bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Add a non-root user
RUN addgroup --system --gid 1001 appuser && \
    adduser --system --uid 1001 --group appuser

# Copy the jar from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Configure container
USER appuser
EXPOSE 8080
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=85.0 -Xms64m"


# Run the application with proper memory settings
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]