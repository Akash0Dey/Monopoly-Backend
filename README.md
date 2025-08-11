# Monopoly Backend

This project is a Spring Boot backend for the Monopoly game, supporting multiple environments (local, dev, sit, prod) and deployment to Render using Docker and GitHub Actions.

## Features
- Spring Boot application
- Environment-specific configuration (local, dev, prod)
- Swagger enabled for non-prod environments only
- Dockerized for easy deployment
- GitHub Actions workflows for CI/CD

## Project Structure
- `src/main/java/com/monopoly/` - Java source code
- `src/main/resources/` - Application configuration files
- `build.gradle` - Gradle build file
- `Dockerfile` - Containerization setup
- `.github/workflows/` - GitHub Actions workflows

## Environment Configuration
Application properties are managed via YAML files:
- `application.yml` - Base configuration
- `application-local.yml` - Local overrides
- `application-dev.yml` - Dev environment
- `application-prod.yml` - Production environment

## Swagger Configuration
Swagger is enabled only for local and dev environments using `@ConditionalOnProperty` at the class level in the Swagger config file. It is not available in production.

## Docker Deployment
The application is containerized using the provided `Dockerfile`. The entrypoint uses `./gradlew bootRun` and sets the server port from the `PORT` environment variable (as required by Render).

### Example Docker Build & Run
```sh
docker build -t monopoly-backend .
docker run -e SPRING_PROFILES_ACTIVE=dev -e DB_USERNAME=sa -e DB_PASSWORD=securePassword -p 8080:8080 monopoly-backend
```

## Render Deployment
- The Dockerfile and GitHub Actions workflows are set up for Render deployment.
- Render assigns a port via the `PORT` environment variable, which is used by the entrypoint script.

## GitHub Actions Workflows
- `.github/workflows/main.yml`: Deploys to Render dev environment on every push.
- `.github/workflows/manual-deploy.yml`: Allows manual deployment of a specific commit to a selected environment (dev, prod) via workflow dispatch.

### Manual Deploy Workflow
- Triggered manually from GitHub Actions UI.
- Accepts environment and optional commit SHA. (not working yet)
- Deploys the selected commit to the chosen environment using Render deploy hooks.

## How to Use
1. **Local Development**
   - Run with `./gradlew bootRun --args='--spring.profiles.active=local'`
   - Access Swagger UI at `/swagger-ui.html` (local/dev only)
2. **Build Docker Image**
   - `docker build -t monopoly-backend .`
3. **Run Docker Container**
   - `docker run -e SPRING_PROFILES_ACTIVE=dev -e DB_USERNAME=sa -e DB_PASSWORD=securePassword -p 8080:8080 monopoly-backend`
4. **Deploy to Render**
   - Push to GitHub for dev auto-deploy
   - Use manual workflow for specific commit/environment

## Security
- Database credentials and other secrets are managed via environment variables and GitHub secrets.
- Swagger is not exposed in production.


