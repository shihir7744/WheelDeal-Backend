# Car Rental System

A modern, production-ready car rental management system built with Spring Boot 3, Angular 19, and PostgreSQL.

## Features

### Backend (Spring Boot 3)
- RESTful API with JWT authentication
- Role-based access control (Customer, Manager, Admin)
- PostgreSQL database integration
- Comprehensive CRUD operations for cars, bookings, branches
- Input validation and error handling
- Security with Spring Security 6

### Frontend (Angular 19)
- Modern, responsive UI with Bootstrap 5
- Professional animations and transitions
- Advanced car search with filters
- User authentication and registration
- Booking management system
- Mobile-friendly design

## Technology Stack

- **Backend**: Spring Boot 3.4.0, Java 21, PostgreSQL, Spring Security, JWT
- **Frontend**: Angular 19, TypeScript, Bootstrap 5, SCSS
- **Database**: PostgreSQL 14+
- **Build Tools**: Maven, Angular CLI

## Prerequisites

- Java 21 or higher
- Node.js 18+ and npm
- PostgreSQL 14+
- Maven 3.6+

## Quick Start

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd car-rental-system
   ```

2. Configure PostgreSQL database:
   ```sql
   CREATE DATABASE carrental;
   CREATE USER carrental WITH PASSWORD 'carrental123';
   GRANT ALL PRIVILEGES ON DATABASE carrental TO carrental;
   ```

3. Update `src/main/resources/application.properties` if needed

4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

The backend will start on http://localhost:8080

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   ng serve
   ```

The frontend will start on http://localhost:4200

## API Endpoints

### Authentication
- POST `/api/auth/login` - User login
- POST `/api/auth/register` - User registration

### Cars
- GET `/api/cars/search` - Search cars with filters
- GET `/api/cars/available` - Get available cars
- GET `/api/cars/{id}` - Get car by ID

### Bookings
- POST `/api/bookings` - Create booking
- GET `/api/bookings/user` - Get user bookings
- DELETE `/api/bookings/{id}` - Cancel booking

### Branches
- GET `/api/branches` - Get all branches
- GET `/api/branches/city/{city}` - Get branches by city

## Database Schema

The application uses the following main entities:
- Users (authentication and profile)
- Cars (vehicle information)
- Branches (rental locations)
- Bookings (rental transactions)

## Security

- JWT-based authentication
- Role-based authorization
- Password encryption with BCrypt
- CORS configuration for frontend integration

## Deployment

### Backend Deployment
1. Build the application: `./mvnw clean package`
2. Run with: `java -jar target/backend-0.0.1-SNAPSHOT.jar`

### Frontend Deployment
1. Build for production: `ng build --configuration production`
2. Deploy the `dist/frontend` folder to a web server

## License

This project is licensed under the MIT License.
