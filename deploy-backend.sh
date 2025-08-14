#!/bin/bash

# Car Rental System - Backend Deployment Script
# This script deploys the backend using Docker Compose

set -e

echo "🚀 Starting Backend Deployment..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker and try again."
    exit 1
fi

# Check if Docker Compose is available
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose is not installed. Please install Docker Compose and try again."
    exit 1
fi

# Navigate to backend directory
cd backend

# Check if .env file exists
if [ ! -f .env ]; then
    echo "⚠️  .env file not found. Creating from template..."
    cat > .env << EOF
# Database Configuration
DATABASE_URL=jdbc:postgresql://db:5432/carrental
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres123

# JWT Configuration
JWT_SECRET=your-super-secret-jwt-key-for-production-change-this

# CORS Configuration
CORS_ALLOWED_ORIGINS=http://localhost:4200,https://yourdomain.com

# Email Configuration
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password

# Upload Configuration
UPLOAD_DIR=uploads
EOF
    echo "📝 Please edit the .env file with your production values before continuing."
    echo "Press Enter when ready to continue..."
    read
fi

# Stop existing containers
echo "🛑 Stopping existing containers..."
docker-compose down

# Build and start containers
echo "🔨 Building and starting containers..."
docker-compose up -d --build

# Wait for application to start
echo "⏳ Waiting for application to start..."
sleep 30

# Check application health
echo "🏥 Checking application health..."
if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo "✅ Backend is running successfully!"
    echo "🌐 Backend URL: http://localhost:8080"
    echo "📊 Health Check: http://localhost:8080/actuator/health"
else
    echo "❌ Backend health check failed. Check logs with: docker-compose logs -f app"
    exit 1
fi

echo "🎉 Backend deployment completed successfully!"
echo ""
echo "📋 Useful commands:"
echo "  View logs: docker-compose logs -f app"
echo "  Stop: docker-compose down"
echo "  Restart: docker-compose restart"
echo "  Update: docker-compose up -d --build"
