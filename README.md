# Hotel Review System — Microservices Backend

A secured, discovery-based **Spring Boot microservices** backend for a hotel review platform, with a reactive **API Gateway**, **Eureka** service discovery, declarative **Feign** inter-service communication, and full **JWT (RS256) authentication with role-based authorization** enforced at the gateway.

> **Status:** In active development. The secured core (services, gateway, discovery, auth) is complete. Caching, event streaming, an AI feature, containerization, tests, and deployment are planned — see the [Roadmap](#roadmap).

---

## Overview

The system is split into independent services by business responsibility, coordinated by a service registry and fronted by a single API gateway that handles authentication and routing:

- **UserService** — user accounts, registration, login (issues JWTs)
- **HotelService** — hotel catalog
- **RatingService** — ratings and text reviews
- **ServiceRegistry** — Eureka server for service discovery
- **ApiGateway** — single entry point; validates JWTs, enforces roles, routes to services

All traffic enters through the gateway on port `8080`. Services resolve each other by registry name (not hardcoded hosts), and authentication happens once at the edge so downstream services stay simple.

---

## Architecture

```text
                        ┌──────────┐
                        │  Client  │
                        └────┬─────┘
                             │  Authorization: Bearer <JWT>
                             ▼
                    ┌──────────────────┐
                    │   API Gateway    │  :8080
                    │  • JWT validation (RS256, public key)
                    │  • role-based authorization (401 / 403)
                    │  • routing via lb:// (load-balanced)
                    └────────┬─────────┘
             ┌───────────────┼────────────────┐
             ▼               ▼                ▼
      ┌────────────┐  ┌────────────┐  ┌──────────────┐
      │UserService │  │HotelService│  │ RatingService│
      │   :8081    │  │   :8082    │  │    :8083     │
      └─────┬──────┘  └────────────┘  └──────────────┘
            │ Feign client → RatingService
            │
            ▼  (all services register with Eureka)
     ┌───────────────────────┐
     │  Eureka Service Registry │  :8761
     └───────────────────────┘
```

**Auth flow:** UserService signs JWTs with an RSA **private key** (RS256). The gateway verifies them with the matching **public key** — so the gateway can validate tokens but can never forge them. On a valid token the gateway forwards the caller's identity downstream as `X-User-Id` and `X-User-Roles` headers.

---

## Tech Stack

| Area | Technology |
|------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.2.x (services), 3.3.x (gateway) |
| Cloud | Spring Cloud 2023.0.x, Spring Cloud Gateway (reactive/WebFlux), Netflix Eureka |
| Inter-service calls | Spring Cloud OpenFeign |
| Security | Spring Security, JWT (jjwt) with **RS256** asymmetric signing, BCrypt password hashing |
| Validation | Jakarta Bean Validation (`@Valid`) |
| Persistence | Spring Data JPA, MySQL |
| Build | Maven (multi-module, per-service) |
| Utilities | Lombok |

---

## Services

### ApiGateway (`:8080`)
Single entry point for all clients. A reactive `GlobalFilter` extracts the bearer token, verifies its RS256 signature and expiry against the public key, whitelists public routes (`/login`, `/register`), rejects invalid/missing tokens with **401**, and blocks non-admins from admin routes with **403**. Routes to services by Eureka name using `lb://`.

### UserService (`:8081`)
User accounts and authentication.
- Register (BCrypt-hashed passwords, default `ROLE_USER`)
- Login (verifies credentials, issues an RS256-signed JWT containing user id + role)
- Get user by id / get all users
- Enriches a user's response with their ratings via a **Feign** call to RatingService

### HotelService (`:8082`)
Hotel catalog.
- Add hotel (**admin only**)
- Get hotel by id / get all hotels

### RatingService (`:8083`)
Ratings and text reviews.
- Add rating
- Get ratings by user / by hotel / all ratings

### ServiceRegistry (`:8761`)
Eureka server. Every service registers on startup so they can be discovered by name.

---

## Security Model

| Capability | Public | USER | ADMIN |
|-----------|:------:|:----:|:-----:|
| Register / Login | ✅ | — | — |
| Browse hotels / read reviews | — | ✅ | ✅ |
| Add a rating | — | ✅ | ✅ |
| Add a hotel (catalog management) | — | ❌ | ✅ |

- **Authentication:** email + password → RS256-signed JWT.
- **Authorization:** role claim (`ROLE_USER` / `ROLE_ADMIN`) read from the token at the gateway.
- **401 vs 403:** `401` = not authenticated (no/invalid token); `403` = authenticated but not permitted.
- Passwords are never stored in plaintext (BCrypt) and never returned in responses.
- RSA keys are generated locally and are **not** committed to the repository.

---

## API Endpoints

All requests go through the gateway at `http://localhost:8080`. Protected endpoints require an `Authorization: Bearer <token>` header.

### UserService — `/api/user`
```http
POST /api/user/register        # public
POST /api/user/login           # public → returns { "token": "<JWT>" }
GET  /api/user/getUser         # authenticated
GET  /api/user/getUser/{id}    # authenticated (includes the user's ratings)
```

### HotelService — `/api/hotel`
```http
POST /api/hotel/addHotel       # ADMIN only
GET  /api/hotel/getHotel       # authenticated
GET  /api/hotel/getHotel/{id}  # authenticated
```

### RatingService — `/api/rating`
```http
POST /api/rating/addRating              # authenticated
GET  /api/rating/allRating              # authenticated
GET  /api/rating/allRating/{userId}/user   # authenticated
GET  /api/rating/allRating/{hotelId}/hotel # authenticated
```

---

## Getting Started

### Prerequisites
- Java 17
- MySQL running locally (databases are auto-created on startup)
- Maven (or the included `mvnw` wrapper)

### 1. Generate the RSA key pair (one time)
The auth service signs tokens with an RSA key pair. Generate it into `UserService/src/main/resources/keys/`, and copy the **public** key into the gateway:

```bash
openssl genrsa -out private.pem 2048
openssl rsa -in private.pem -pubout -out public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in private.pem -out private_pkcs8.pem
```
- `UserService` uses `private_pkcs8.pem` to sign.
- `ApiGateway` uses `public.pem` to verify (copy `public.pem` into `ApiGateway/src/main/resources/keys/`).

Keys are gitignored and must be generated per environment.

### 2. Start the services (in order)
Eureka must be up first so the others can register.

```bash
# 1. Service Registry (Eureka)
cd ServiceRegistry && ./mvnw spring-boot:run

# 2. Hotel Service
cd HotelService && ./mvnw spring-boot:run

# 3. Rating Service
cd RatingService && ./mvnw spring-boot:run

# 4. User Service
cd UserService && ./mvnw spring-boot:run

# 5. API Gateway
cd ApiGateway && ./mvnw spring-boot:run
```

### 3. Verify
- Eureka dashboard: `http://localhost:8761` (should list all four services)
- Register a user, log in to get a token, then call a protected endpoint through `http://localhost:8080` with the token.

---

## Key Concepts Demonstrated

- Microservices architecture with clear separation of business responsibilities
- Service discovery with Eureka (services resolved by name, not hardcoded addresses)
- API Gateway as a single entry point (routing + centralized auth)
- Declarative inter-service communication with Feign (with client-side load balancing)
- Stateless authentication with **JWT** and **asymmetric RS256** signing
- Role-based authorization enforced at the edge
- DTO layer separating API contracts from persistence entities
- Centralized error handling (`@RestControllerAdvice`) with consistent JSON responses
- Input validation at the API boundary (Jakarta Bean Validation)
- Secure secret handling (BCrypt hashing; keys kept out of version control)

---

## Roadmap

Planned enhancements, building on the secured core:

- [ ] **Redis caching** for hotel details and top-rated hotels, with before/after latency benchmarks
- [ ] **Kafka event streaming** — publish a `review-created` event on new reviews
- [ ] **AI review summarization** — consume review events, generate sentiment + top positives/complaints via an LLM, cache the result in Redis
- [ ] **Docker & Docker Compose** — containerize every service plus MySQL, Redis, and Kafka
- [ ] **Testing** — unit tests and Testcontainers-based integration tests on critical paths
- [ ] **Deployment** — deploy the full stack and publish a live demo
- [ ] **Payments service** and **centralized config server** (post-launch)

---

## What I Learned

Building this taught me how a backend can be decomposed into independent, separately deployable services, and the real trade-offs that come with it: how service discovery removes hardcoded coupling, why authentication belongs at the gateway in a distributed system, how stateless JWTs let services scale without shared session state, and why asymmetric (RS256) signing matters when multiple services need to verify tokens but only one should be able to issue them. Several design decisions (Feign vs. RestTemplate, enum vs. string for roles, DTOs vs. exposing entities, 401 vs. 403 semantics) were made deliberately and can be defended in review.

---

## Author

**Praveer** — [GitHub: praveerbn12](https://github.com/praveerbn12)
