@echo off
setlocal enabledelayedexpansion

REM Car Rental System - Backend Deployment Script (Windows)
REM This script deploys the backend using Docker Compose

echo 🚀 Starting Backend Deployment...

REM Check if Docker is running
docker info >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker is not running. Please start Docker and try again.
    pause
    exit /b 1
)

REM Check if Docker Compose is available
docker-compose --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker Compose is not installed. Please install Docker Compose and try again.
    pause
    exit /b 1
)

REM Navigate to backend directory
cd backend

REM Check if .env file exists
if not exist .env (
    echo ⚠️  .env file not found. Creating from template...
    (
        echo # Database Configuration
        echo DATABASE_URL=jdbc:postgresql://db:5432/carrental
        echo DATABASE_USERNAME=postgres
        echo DATABASE_PASSWORD=postgres123
        echo.
        echo # JWT Configuration
        echo JWT_SECRET=your-super-secret-jwt-key-for-production-change-this
        echo.
        echo # CORS Configuration
        echo CORS_ALLOWED_ORIGINS=http://localhost:4200,https://yourdomain.com
        echo.
        echo # Email Configuration
        echo MAIL_HOST=smtp.gmail.com
        echo MAIL_PORT=587
        echo MAIL_USERNAME=your-email@gmail.com
        echo MAIL_PASSWORD=your-app-password
        echo.
        echo # Upload Configuration
        echo UPLOAD_DIR=uploads
    ) > .env
    echo 📝 Please edit the .env file with your production values before continuing.
    echo Press Enter when ready to continue...
    pause
)

REM Stop existing containers
echo 🛑 Stopping existing containers...
docker-compose down

REM Build and start containers
echo 🔨 Building and starting containers...
docker-compose up -d --build

REM Wait for application to start
echo ⏳ Waiting for application to start...
timeout /t 30 /nobreak >nul

REM Check application health
echo 🏥 Checking application health...
curl -f http://localhost:8080/actuator/health >nul 2>&1
if errorlevel 1 (
    echo ❌ Backend health check failed. Check logs with: docker-compose logs -f app
    pause
    exit /b 1
) else (
    echo ✅ Backend is running successfully!
    echo 🌐 Backend URL: http://localhost:8080
    echo 📊 Health Check: http://localhost:8080/actuator/health
)

echo 🎉 Backend deployment completed successfully!
echo.
echo 📋 Useful commands:
echo   View logs: docker-compose logs -f app
echo   Stop: docker-compose down
echo   Restart: docker-compose restart
echo   Update: docker-compose up -d --build

pause
