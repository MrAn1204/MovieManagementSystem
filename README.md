# Movie Management System

A movie theater management system with an Angular frontend and a Java Spring Boot REST API.

## Project Structure

```text
src/
  client/mms/       Angular frontend
  server/mms-api/   Spring Boot backend

design/             UI and database design files
dbinit/             Database initialization directories
```

## Prerequisites

Install or start the following services before running the application:

- Java 17
- Node.js and npm (the frontend declares `npm@11.2.0`)
- MySQL on `localhost:3306`
- Redis on `localhost:6379`

The repository does not pin a Node.js version. Use a Node.js release compatible with Angular 21.

Features require additional configuration:

- Gmail SMTP credentials for password-reset email
- Google Cloud Application Default Credentials for movie and promotion image storage

## Database Setup

The backend connects to MySQL with these settings:

```text
Host:     localhost
Port:     3306
Database: mms
Username: root
Password: DB_PASSWORD
```

Create the database before starting the backend:

```sql
CREATE DATABASE mms;
```

Hibernate uses `ddl-auto=validate`, so the required tables must already exist. This repository currently does not include an executable schema, migration, seed script, or default user account. The ERD under `design/database/ERD.drawio` is a design reference only. Obtain or create a compatible schema before running the application.

## Backend

Open PowerShell in the backend directory:

```powershell
cd src/server/mms-api
```

Set the required environment variables for the current PowerShell session:

```powershell
$env:DB_PASSWORD = "your-mysql-password"
$env:JWT_SECRET_KEY = "your-jwt-secret"
```

Start Redis on `localhost:6379`, then start the API:

```powershell
.\mvnw.cmd spring-boot:run
```

The API runs at `http://localhost:8080` by default.

### Development Profile

The development profile contains Gmail SMTP settings. Activate the Spring profile explicitly when password-reset email is needed:

```powershell
$env:MMS_EMAIL = "your-gmail-address"
$env:MMS_EMAIL_PASSWORD = "your-gmail-app-password"
$env:SPRING_PROFILES_ACTIVE = "dev"
.\mvnw.cmd spring-boot:run
```

Alternatively, pass the profile to Maven:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

`-Pdev` alone does not activate the Spring `dev` profile. The local `application-dev.properties` file may be ignored by Git, so recreate it or provide the values through environment variables as needed.

### Backend Build and Tests

Run the backend tests:

```powershell
.\mvnw.cmd clean test
```

Create a packaged JAR:

```powershell
.\mvnw.cmd clean package
```

Run the packaged application:

```powershell
java -jar target/mms-api-0.0.1-SNAPSHOT.jar
```

## Frontend

Open a second PowerShell window in the frontend directory:

```powershell
cd src/client/mms
npm ci
```

Start the Angular development server:

```powershell
npm start
```

Open `http://localhost:4200` in a browser. The frontend expects the backend at `http://localhost:8080`, so start the API first.

### Frontend Build and Tests

Build the application:

```powershell
npm run build
```

Run the unit tests:

```powershell
npm test
```

Run a development watch build:

```powershell
npm run watch
```

The build includes Angular SSR output. To serve the built SSR application:

```powershell
npm run serve:ssr:mms
```

The SSR server runs at `http://localhost:4000` by default. Its port can be changed with the `PORT` environment variable:

```powershell
$env:PORT = "4000"
npm run serve:ssr:mms
```

The backend CORS configuration currently allows `http://localhost:4200`, not `http://localhost:4000`. The normal integrated browser workflow is therefore the Angular development server on port 4200 unless CORS is updated for the SSR port.

## Recommended Startup Order

1. Start MySQL and Redis.
2. Create the `mms` database and load a compatible schema.
3. Set `DB_PASSWORD` and `JWT_SECRET_KEY`.
4. Start the backend from `src/server/mms-api`.
5. Install dependencies and start the frontend from `src/client/mms`.
6. Open `http://localhost:4200`.

## Configuration Notes

- Angular services use `http://localhost:8080` as the backend origin.
- `npm run build` may call backend endpoints while Angular prerenders routes, so the backend and database should be available during the build.
- Image upload and deletion require Google Cloud credentials and access to the configured storage bucket.
- Password-reset email requires Gmail SMTP credentials and the `dev` Spring profile.
- No default login credentials are included in the repository. A compatible database schema and user account must be supplied separately.
