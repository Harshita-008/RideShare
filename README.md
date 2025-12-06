# 🚗 RideShare Backend API

> A comprehensive ride-sharing backend service built with Spring Boot 4.0, MongoDB, and JWT Authentication. This application provides a complete RESTful API for managing ride requests between passengers and drivers with role-based access control.

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [API Documentation](#-api-documentation)
- [Testing Guide](#-testing-guide)
- [Security](#-security)
- [Error Handling](#-error-handling)
- [Database Schema](#-database-schema)
- [Business Rules](#-business-rules)
- [Troubleshooting](#-troubleshooting)

---

## ✨ Features

### 🔐 Core Functionality
- **Secure Authentication** - JWT-based authentication with BCrypt password encryption
- **Role-Based Access Control** - Separate permissions for passengers (USER) and drivers (DRIVER)
- **Ride Management** - Complete CRUD operations for ride requests
- **Input Validation** - Jakarta Bean Validation for all API requests
- **Global Exception Handling** - Centralized error handling with meaningful messages
- **MongoDB Integration** - NoSQL database for flexible and scalable data storage
- **Clean Architecture** - Layered design following Spring Boot best practices

### 👤 User Features (Passengers)
- Register and login with secure authentication
- Create ride requests with pickup and drop locations
- View personal ride history
- Complete accepted rides

### 🚕 Driver Features
- Register and login as a driver
- View all pending ride requests
- Accept ride requests from passengers
- Complete rides after reaching destination

---

## 🛠 Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 25 | Core Programming Language |
| **Spring Boot** | 4.0.0 | Application Framework |
| **Spring Security** | 4.0.0 | Authentication & Authorization |
| **Spring Data MongoDB** | 4.0.0 | Database Integration |
| **MongoDB** | 4.0+ | NoSQL Database |
| **JWT (JJWT)** | 0.11.5 | Token-based Authentication |
| **Lombok** | Latest | Code Generation & Boilerplate Reduction |
| **Jakarta Validation** | 3.0+ | Input Validation |
| **Maven** | 3.6+ | Build Tool & Dependency Management |

---

## 🏗 Architecture

### Layered Architecture Pattern

```
┌─────────────────────────────────────────────────────┐
│         Client (Postman/Web/Mobile)                 │
└────────────────────┬────────────────────────────────┘
                     │ HTTP Requests + JWT Token
                     ▼
┌─────────────────────────────────────────────────────┐
│           Controller Layer (REST API)               │
│  • AuthController  • RideController                 │
│  • Request Validation  • Response Formatting        │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│              Service Layer                          │
│  • AuthService  • RideService                       │
│  • Business Logic  • Transaction Management         │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│            Repository Layer                         │
│  • UserRepository  • RideRepository                 │
│  • Spring Data MongoDB Operations                   │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│              MongoDB Database                       │
│  • users collection  • rides collection             │
└─────────────────────────────────────────────────────┘
```

### Request Flow

```
1. Client Request
   ↓
2. JwtAuthenticationFilter (validates token & extracts user info)
   ↓
3. SecurityConfig (checks authorization based on role)
   ↓
4. Controller (receives request & validates input)
   ↓
5. Service Layer (processes business logic)
   ↓
6. Repository (performs database operations)
   ↓
7. Response (flows back through layers)
   ↓
8. GlobalExceptionHandler (catches & formats any errors)
```

---

## 📁 Project Structure

```
RideShare/
├── README.md                                           # Project documentation
├── pom.xml                                            # Maven configuration
│
└── src/main/
    ├── java/com/example/RideShare/
    │   │
    │   ├── RideShareApplication.java                  # Main application entry point
    │   │
    │   ├── model/                                     # Entity classes
    │   │   ├── User.java                              # User entity
    │   │   └── Ride.java                              # Ride entity
    │   │
    │   ├── repository/                                # Data access layer
    │   │   ├── UserRepository.java                    # User CRUD operations
    │   │   └── RideRepository.java                    # Ride CRUD operations
    │   │
    │   ├── service/                                   # Business logic layer
    │   │   ├── AuthService.java                       # Authentication logic
    │   │   └── RideService.java                       # Ride management logic
    │   │
    │   ├── controller/                                # REST API endpoints
    │   │   ├── AuthController.java                    # /api/auth/** endpoints
    │   │   └── RideController.java                    # /api/v1/** endpoints
    │   │
    │   ├── config/                                    # Configuration classes
    │   │   ├── SecurityConfig.java                    # Spring Security setup
    │   │   └── JwtAuthenticationFilter.java           # JWT validation filter
    │   │
    │   ├── dto/                                       # Data Transfer Objects
    │   │   ├── RegisterRequest.java                   # Registration payload
    │   │   ├── LoginRequest.java                      # Login payload
    │   │   ├── CreateRideRequest.java                 # Create ride payload
    │   │   └── AuthResponse.java                      # Authentication response
    │   │
    │   ├── exception/                                 # Exception handling
    │   │   ├── GlobalExceptionHandler.java            # Centralized error handler
    │   │   ├── NotFoundException.java                 # 404 custom exception
    │   │   ├── BadRequestException.java               # 400 custom exception
    │   │   └── ErrorResponse.java                     # Standard error format
    │   │
    │   └── util/                                      # Utility classes
    │       └── JwtUtil.java                           # JWT token operations
    │
    └── resources/
        └── application.yaml                           # Application configuration
```

---

## 📦 Prerequisites

Before running this application, ensure you have:

| Software | Minimum Version | Download Link |
|----------|----------------|---------------|
| **Java JDK** | 25 | [Oracle Java](https://www.oracle.com/java/technologies/downloads/) |
| **Maven** | 3.6+ | [Apache Maven](https://maven.apache.org/download.cgi) |
| **MongoDB** | 4.0+ | [MongoDB Community](https://www.mongodb.com/try/download/community) |

### Verify Installations

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check MongoDB
mongosh --version
```

---

## 🚀 Installation

### Step 1: Clone the Repository

```bash
git clone https://github.com/yourusername/rideshare-backend.git
cd RideShare
```

### Step 2: Install MongoDB

#### macOS
```bash
brew tap mongodb/brew
brew install mongodb-community
brew services start mongodb-community
```

#### Ubuntu/Linux
```bash
sudo apt update
sudo apt install -y mongodb
sudo systemctl start mongodb
sudo systemctl enable mongodb
```

#### Windows
Download and install from [MongoDB Official Site](https://www.mongodb.com/try/download/community)

### Step 3: Build the Project

```bash
mvn clean install
```

### Step 4: Run the Application

```bash
# Using Maven
mvn spring-boot:run

# Or using JAR
java -jar target/RideShare-0.0.1-SNAPSHOT.jar
```

### Step 5: Verify

```bash
curl http://localhost:8081/api/auth/login
```

You should see a 400 error (expected without credentials).

---

## ⚙️ Configuration

### application.yaml

```yaml
spring:
  application:
    name: RideShare
  data:
    mongodb:
      uri: mongodb://localhost:27017/rideshare
      database: rideshare

jwt:
  secret: mySecretKeyForJWTTokenGenerationAndValidation12345
  expiration: 86400000  # 24 hours

server:
  port: 8081
```

### Configuration Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| `mongodb.uri` | `mongodb://localhost:27017/rideshare` | MongoDB connection |
| `mongodb.database` | `rideshare` | Database name |
| `jwt.secret` | (provided) | JWT signing key |
| `jwt.expiration` | `86400000` | Token expiry (24h) |
| `server.port` | `8081` | Server port |

---

## 📡 API Documentation

### Base URL
```
http://localhost:8081
```

### Endpoints Overview

| Method | Endpoint | Description | Auth | Role |
|--------|----------|-------------|------|------|
| POST | `/api/auth/register` | Register user | No | Public |
| POST | `/api/auth/login` | Login & get JWT | No | Public |
| POST | `/api/v1/rides` | Create ride | Yes | USER |
| GET | `/api/v1/user/rides` | Get user rides | Yes | USER |
| GET | `/api/v1/driver/rides/requests` | Get pending rides | Yes | DRIVER |
| POST | `/api/v1/driver/rides/{id}/accept` | Accept ride | Yes | DRIVER |
| POST | `/api/v1/rides/{id}/complete` | Complete ride | Yes | USER/DRIVER |

---

### Authentication Endpoints

#### POST /api/auth/register

Register a new user.

**Request:**
```json
{
  "username": "john_doe",
  "password": "password123",
  "role": "ROLE_USER"
}
```

**Response (200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "john_doe",
  "role": "ROLE_USER"
}
```

**Validation:**
- Username: min 3 characters
- Password: min 4 characters
- Role: `ROLE_USER` or `ROLE_DRIVER`

---

#### POST /api/auth/login

Login and receive JWT token.

**Request:**
```json
{
  "username": "john_doe",
  "password": "password123"
}
```

**Response (200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "john_doe",
  "role": "ROLE_USER"
}
```

---

### User Endpoints

#### POST /api/v1/rides

Create a new ride request.

**Headers:**
```
Authorization: Bearer <TOKEN>
```

**Request:**
```json
{
  "pickupLocation": "Koramangala, Bangalore",
  "dropLocation": "Indiranagar, Bangalore"
}
```

**Response (200):**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "userId": "507f191e810c19729de860ea",
  "driverId": null,
  "pickupLocation": "Koramangala, Bangalore",
  "dropLocation": "Indiranagar, Bangalore",
  "status": "REQUESTED",
  "createdAt": "2025-12-07T10:30:00.000Z"
}
```

---

#### GET /api/v1/user/rides

Get all rides for logged-in user.

**Headers:**
```
Authorization: Bearer <TOKEN>
```

**Response (200):**
```json
[
  {
    "id": "507f1f77bcf86cd799439011",
    "userId": "507f191e810c19729de860ea",
    "driverId": "507f191e810c19729de860eb",
    "pickupLocation": "Koramangala, Bangalore",
    "dropLocation": "Indiranagar, Bangalore",
    "status": "ACCEPTED",
    "createdAt": "2025-12-07T10:30:00.000Z"
  }
]
```

---

### Driver Endpoints

#### GET /api/v1/driver/rides/requests

Get all pending ride requests.

**Headers:**
```
Authorization: Bearer <DRIVER_TOKEN>
```

**Response (200):**
```json
[
  {
    "id": "507f1f77bcf86cd799439011",
    "userId": "507f191e810c19729de860ea",
    "driverId": null,
    "pickupLocation": "Koramangala, Bangalore",
    "dropLocation": "Indiranagar, Bangalore",
    "status": "REQUESTED",
    "createdAt": "2025-12-07T10:30:00.000Z"
  }
]
```

---

#### POST /api/v1/driver/rides/{rideId}/accept

Accept a ride request.

**Headers:**
```
Authorization: Bearer <DRIVER_TOKEN>
```

**Response (200):**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "userId": "507f191e810c19729de860ea",
  "driverId": "507f191e810c19729de860eb",
  "pickupLocation": "Koramangala, Bangalore",
  "dropLocation": "Indiranagar, Bangalore",
  "status": "ACCEPTED",
  "createdAt": "2025-12-07T10:30:00.000Z"
}
```

---

#### POST /api/v1/rides/{rideId}/complete

Complete a ride.

**Headers:**
```
Authorization: Bearer <TOKEN>
```

**Response (200):**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "userId": "507f191e810c19729de860ea",
  "driverId": "507f191e810c19729de860eb",
  "pickupLocation": "Koramangala, Bangalore",
  "dropLocation": "Indiranagar, Bangalore",
  "status": "COMPLETED",
  "createdAt": "2025-12-07T10:30:00.000Z"
}
```

---

## 🧪 Testing Guide

### Testing with cURL

#### 1. Register a User
```bash
curl -X POST http://localhost:8081/api/auth/register \
-H "Content-Type: application/json" \
-d '{
  "username": "john_doe",
  "password": "password123",
  "role": "ROLE_USER"
}'
```

#### 2. Register a Driver
```bash
curl -X POST http://localhost:8081/api/auth/register \
-H "Content-Type: application/json" \
-d '{
  "username": "driver_mike",
  "password": "driver123",
  "role": "ROLE_DRIVER"
}'
```

#### 3. Login
```bash
curl -X POST http://localhost:8081/api/auth/login \
-H "Content-Type: application/json" \
-d '{
  "username": "john_doe",
  "password": "password123"
}'
```

#### 4. Create a Ride
```bash
curl -X POST http://localhost:8081/api/v1/rides \
-H "Authorization: Bearer YOUR_TOKEN" \
-H "Content-Type: application/json" \
-d '{
  "pickupLocation": "Koramangala",
  "dropLocation": "Indiranagar"
}'
```

#### 5. View My Rides
```bash
curl -X GET http://localhost:8081/api/v1/user/rides \
-H "Authorization: Bearer YOUR_TOKEN"
```

#### 6. View Pending Rides (Driver)
```bash
curl -X GET http://localhost:8081/api/v1/driver/rides/requests \
-H "Authorization: Bearer DRIVER_TOKEN"
```

#### 7. Accept Ride (Driver)
```bash
curl -X POST http://localhost:8081/api/v1/driver/rides/RIDE_ID/accept \
-H "Authorization: Bearer DRIVER_TOKEN"
```

#### 8. Complete Ride
```bash
curl -X POST http://localhost:8081/api/v1/rides/RIDE_ID/complete \
-H "Authorization: Bearer YOUR_TOKEN"
```

---

## 🔐 Security

### JWT Authentication Flow

```
1. User registers/logs in
   ↓
2. Server generates JWT token (valid 24h)
   ↓
3. Client stores token
   ↓
4. Client sends token in Authorization header
   ↓
5. JwtAuthenticationFilter validates token
   ↓
6. Request proceeds if valid, else 401
```

### Security Features

- **BCrypt Password Hashing** - Passwords encrypted with BCrypt
- **JWT Tokens** - Stateless authentication
- **Role-Based Access** - `@PreAuthorize` annotations
- **CSRF Protection** - Disabled for stateless API
- **Secure Headers** - Spring Security defaults

### Token Structure

```json
{
  "sub": "john_doe",
  "role": "ROLE_USER",
  "iat": 1701950400,
  "exp": 1702036800
}
```

---

## ⚠️ Error Handling

### Error Response Format

```json
{
  "error": "ERROR_TYPE",
  "message": "Descriptive error message",
  "timestamp": "2025-12-07T10:30:00.123456"
}
```

### HTTP Status Codes

| Status | Error Type | Description |
|--------|------------|-------------|
| 400 | `BAD_REQUEST` | Invalid input |
| 400 | `VALIDATION_ERROR` | Validation failed |
| 401 | `UNAUTHORIZED` | Missing/invalid token |
| 403 | `FORBIDDEN` | Insufficient permissions |
| 404 | `NOT_FOUND` | Resource not found |
| 500 | `INTERNAL_ERROR` | Server error |

### Example Errors

**Validation Error:**
```json
{
  "error": "VALIDATION_ERROR",
  "message": "Username is required, Password must be at least 4 characters",
  "timestamp": "2025-12-07T10:30:00.123456"
}
```

**Not Found:**
```json
{
  "error": "NOT_FOUND",
  "message": "Ride not found",
  "timestamp": "2025-12-07T10:30:00.123456"
}
```

---

## 💾 Database Schema

### Users Collection

```javascript
{
  _id: ObjectId("507f191e810c19729de860ea"),
  username: "john_doe",
  password: "$2a$10$...",  // BCrypt hash
  role: "ROLE_USER"
}
```

### Rides Collection

```javascript
{
  _id: ObjectId("507f1f77bcf86cd799439011"),
  userId: "507f191e810c19729de860ea",
  driverId: "507f191e810c19729de860eb",
  pickupLocation: "Koramangala, Bangalore",
  dropLocation: "Indiranagar, Bangalore",
  status: "ACCEPTED",  // REQUESTED, ACCEPTED, COMPLETED
  createdAt: ISODate("2025-12-07T10:30:00.000Z")
}
```

---

## 📏 Business Rules

### User Registration
- Username must be unique
- Username: min 3 characters
- Password: min 4 characters
- Role: `ROLE_USER` or `ROLE_DRIVER`

### Ride Creation
- Only `ROLE_USER` can create rides
- Pickup and drop locations required
- Initial status: `REQUESTED`

### Ride Acceptance
- Only `ROLE_DRIVER` can accept
- Ride must be `REQUESTED`
- Changes status to `ACCEPTED`

### Ride Completion
- Both USER and DRIVER can complete
- Ride must be `ACCEPTED`
- Changes status to `COMPLETED`

---

## 🐛 Troubleshooting

### MongoDB Connection Issues

**Problem:** Cannot connect to MongoDB

**Solution:**
```bash
# Check if MongoDB is running
sudo systemctl status mongodb

# Start MongoDB
sudo systemctl start mongodb

# Check logs
tail -f /var/log/mongodb/mongod.log
```

---

### JWT Token Issues

**Problem:** Invalid JWT token error

**Solutions:**
- Ensure token format: `Authorization: Bearer TOKEN`
- Check token expiration (24h default)
- Verify JWT secret in application.yaml

---

### Port Already in Use

**Problem:** Port 8081 already in use

**Solution:**
```bash
# Find process using port
lsof -i :8081

# Kill process
kill -9 <PID>

# Or change port in application.yaml
```

---

### Build Errors

**Problem:** Maven build fails

**Solution:**
```bash
# Clean and rebuild
mvn clean install -U

# Skip tests
mvn clean install -DskipTests
```

---

## 🚀 Deployment

### Building for Production

```bash
# Build production JAR
mvn clean package -Pprod

# Run the application
java -jar target/RideShare-0.0.1-SNAPSHOT.jar
```

### Docker Deployment

**Dockerfile:**
```dockerfile
FROM openjdk:25-jdk-slim
WORKDIR /app
COPY target/RideShare-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Build and Run:**
```bash
docker build -t rideshare-backend .
docker run -p 8081:8081 rideshare-backend
```

---