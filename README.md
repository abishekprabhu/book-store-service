# Book Store REST API

## Overview
This project is a RESTful web service for managing a bookstore. It provides endpoints for managing books, authors, customers, and purchases. The application is built using Spring Boot and follows clean architecture principles.

## Features
- CRUD operations for books, authors, and customers
- Purchase management
- Swagger UI for API documentation
- Logging and exception handling

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- MapStruct
- Lombok
- Swagger
- JUnit & Mockito
- JaCoCo
- SonarQube

## Entity Relationship
The system includes the following entities:
- **Author**: Can write multiple books
- **Book**: Written by an author and can be purchased by customers
- **Customer**: Can purchase multiple books
- **Purchase**: Represents a transaction between a customer and a book

## REST Endpoints
- `/api/books` - Manage books
- `/api/authors` - Manage authors
- `/api/purchases` - Manage purchases
- `/api/auth` - Authentication endpoints

## Testing
Unit and integration tests are written using JUnit and Mockito. Code coverage is measured using JaCoCo.

## Code Quality Tools
- **JaCoCo**: For code coverage
- **SonarQube**: For static code analysis

## Package Structure
The project follows a layered architecture with packages for controllers, services, repositories, DTOs, and entities.

## Conclusion
This project demonstrates a clean and modular implementation of a RESTful API for a bookstore. It integrates modern development tools and practices for maintainability and scalability.
