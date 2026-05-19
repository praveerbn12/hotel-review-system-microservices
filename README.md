# Hotel Review System Microservices

A Java Spring Boot microservices project for managing users, hotels, and ratings in a hotel review platform.

This project demonstrates a basic microservices architecture using multiple independent Spring Boot services and a Eureka Service Registry for service discovery.

## Project Overview

The Hotel Review System is divided into separate services based on business responsibility:

- **UserService** manages user information.
- **HotelService** manages hotel information.
- **RatingService** manages ratings and reviews.
- **ServiceRegistry** provides service discovery using Eureka.

The goal of this project is to understand how backend systems can be split into smaller services that communicate with each other instead of building one large monolithic application.

## Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Eureka Service Registry
- Maven
- REST APIs
- MySQL / PostgreSQL
- Lombok

## Microservices

### UserService

Responsible for user-related operations.

Example responsibilities:

- Create users
- Get user by ID
- Get all users
- Connect user information with rating data

### HotelService

Responsible for hotel-related operations.

Example responsibilities:

- Create hotels
- Get hotel by ID
- Get all hotels

### RatingService

Responsible for ratings and reviews.

Example responsibilities:

- Add ratings
- Get ratings by user
- Get ratings by hotel

### ServiceRegistry

Eureka server used for service discovery.

Each service registers itself with Eureka so other services can discover and communicate with it.

## Architecture

```text
Client
  |
  v
UserService -------- RatingService -------- HotelService
  \                     |                    /
   \                    |                   /
    ------------ Service Registry ----------
                 Eureka Server
```

## Project Structure

```text
hotel-review-system-microservices/
├── UserService/
├── HotelService/
├── RatingService/
├── ServiceRegistry/
├── .gitignore
└── README.md
```

## API Endpoints

### UserService

```http
POST /api/user
GET /api/user
GET /api/user/{userId}
```

### HotelService

```http
POST /api/hotels
GET /api/hotels
GET /api/hotels/{hotelId}
```

### RatingService

```http
POST /api/ratings
GET /api/ratings
GET /api/ratings/users/{userId}
GET /api/ratings/hotels/{hotelId}
```

## Key Concepts Demonstrated

- Microservices architecture
- REST API development
- Service discovery using Eureka
- Separation of business responsibilities
- Java Spring Boot backend development
- Inter-service communication
- Modular backend project structure

## How to Run

### 1. Start Service Registry

Run the `ServiceRegistry` application first.

```bash
cd ServiceRegistry
mvn spring-boot:run
```

### 2. Start HotelService

```bash
cd HotelService
mvn spring-boot:run
```

### 3. Start RatingService

```bash
cd RatingService
mvn spring-boot:run
```

### 4. Start UserService

```bash
cd UserService
mvn spring-boot:run
```

## Recommended Run Order

```text
1. ServiceRegistry
2. HotelService
3. RatingService
4. UserService
```

## Future Improvements

- Add API Gateway
- Add centralized configuration server
- Add Resilience4j circuit breaker
- Add Swagger/OpenAPI documentation
- Add Docker Compose for running all services together
- Add authentication and authorization
- Add centralized logging
- Add unit and integration tests
- Add database migration scripts

## Learning Outcome

This project helped me understand how Java Spring Boot applications can be structured as independent microservices and how service discovery helps services communicate in a distributed backend system.

## Status

Core microservice folders are created with UserService, HotelService, RatingService, and ServiceRegistry. Additional production-level improvements such as API Gateway, Docker Compose, and Swagger documentation can be added in future iterations.
