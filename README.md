# Secure Access Log & Threat Analyzer

A backend security-focused application built using **Spring Boot** that provides secure authentication, login monitoring, and threat detection mechanisms.

---

## Features

*  JWT-based Authentication & Authorization
*  Secure User Registration & Login
*  DTO-based API Design (clean architecture)
*  Access Logging System (tracks login activity)
*  Brute-force Attack Detection
*  Threat Alert Generation
*  Protected APIs using Spring Security
*  PostgreSQL Database Integration

---

##  Tech Stack

* **Backend:** Spring Boot, Spring Security
* **Database:** PostgreSQL
* **Authentication:** JWT (JSON Web Token)
* **ORM:** Spring Data JPA (Hibernate)
* **Build Tool:** Maven
* **API Testing:** Swagger UI / Postman

---

##  Project Structure

```
src/main/java/com/harshit/securitymonitor

├── controller        # REST Controllers  
├── service           # Business Logic  
├── repository        # JPA Repositories  
├── entity            # Database Entities  
├── dto               # Data Transfer Objects  
├── jwt               # JWT Utility & Filter  
├── config            # Security Configuration  
```

---

##  Authentication Flow

1. User registers using `/api/auth/register`
2. Password is encrypted using BCrypt
3. User logs in via `/api/auth/login`
4. JWT token is generated
5. Token is used to access protected APIs

---

##  API Endpoints

###  Public APIs

| Method | Endpoint             | Description     |
| ------ | -------------------- | --------------- |
| POST   | `/api/auth/register` | Register user   |
| POST   | `/api/auth/login`    | Login & get JWT |

---

###  Protected APIs

| Method | Endpoint    | Description                     |
| ------ | ----------- | ------------------------------- |
| GET    | `/api/logs` | View access logs (JWT required) |

---

##  Security Features

* Password encryption using **BCrypt**
* JWT-based stateless authentication
* Protection against brute-force login attempts
* Account locking after multiple failed attempts
* Secure API access with token validation

---

##  Setup Instructions

###  Clone Repository

```bash
git clone https://github.com/your-username/securitymonitor.git
cd securitymonitor
```

---

###  Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/security_monitoring_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

---

###  Run Application

```bash
mvn clean install
mvn spring-boot:run
```

---

###  Access Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

## Future Enhancements

*  Role-based Authorization (ADMIN / USER)
*  Dashboard for logs & threat analysis
*  Analytics & visualization
*  Email alerts for suspicious activity

---

##  Key Learnings

* Implementing secure authentication using JWT
* Designing REST APIs with DTO architecture
* Handling real-world security threats like brute-force attacks
* Integrating Spring Security with custom filters

---

##  Contributing

Feel free to fork this repository and contribute!

---

##  Contact

**Harshit Kumar**
 Backend Developer | Java | Spring Boot
