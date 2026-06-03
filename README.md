# Reduction Backend

Spring Boot REST API for pantry and recipe management.

## Current stack
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Redis
- Docker Compose for local stack

## What works now
- Health endpoint at `GET /api/health`
- Pantry CRUD endpoints
- Recipe CRUD endpoints
- Global error handler for API errors
- Basic request validation in the service layer

## Local run
If your machine has Java installed:

```bash
cd reduction-backend
bash ./mvnw test
bash ./mvnw spring-boot:run
```

## Docker run

```bash
cd reduction-backend
docker compose up --build
```

This starts:
- PostgreSQL on `5432`
- Redis on `6379`
- the Spring Boot API on `8080`

Inside Docker, the app connects to Postgres with host `postgres` and Redis with host `redis`.

## Config model

The app reads datasource, JPA, Redis, and Spoonacular settings from environment variables with local defaults.

Useful variables:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SPRING_JPA_HIBERNATE_DDL_AUTO`
- `SPRING_DATA_REDIS_HOST`
- `SPRING_DATA_REDIS_PORT`
- `SPOONACULAR_API_KEY`

