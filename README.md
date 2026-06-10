Personal Finance Tracker
# Overview

Personal Finance Tracker is a backend finance management application built using Java, Spring Boot, PostgreSQL, Spring Security, JWT Authentication, and Swagger/OpenAPI.

The application helps users manage their personal finances by tracking transactions, generating reports, monitoring budgets, and analyzing spending patterns. It follows a layered architecture using DTOs, validation, exception handling, pagination, and secure authentication.

# Features

* Authentication & Security
  - User Registration
  - User Login
  - JWT Authentication
  - Authorization with Spring Security
  - BCrypt Password Encryption
  - Protected API Endpoints
  - Current Authenticated User Access

  
* Transaction Management
  - Create Transactions
  - Update Transactions
  - Delete Transactions
  - View Transactions
  - Category Filtering
  - Month Filtering
  - Balance Calculation
 

* Reporting & Analytics
  - Monthly Income Report
  - Monthly Expense Report
  - Monthly Balance Report
  - Monthly Transaction Summary
  - Monthly Spending Report for Each Category


* Budget Management
  - Create Budget Limits
  - Track Category Spending
  - Remaining Budget Calculation
  - Budget Status Analysis 
    - SAFE 
    - EXCEEDED


* Data Management
  - PostgreSQL Database Integration
  - DataFaker Sample Data Generation
  - CSV Export Functionality

  
* API Improvements
  - DTO-Based Architecture
  - Request Validation
  - Global Exception Handling
  - Pagination Support
  - Swagger/OpenAPI Documentation 
  
## Technology Stack

| Technology      |	  Purpose                      |
|-----------------|--------------------------------|
| Java 17         | 	Programming Language          |
| Spring Boot     | 	Backend Framework             |
| Spring Security | 	Authentication & Authorization |
| JWT	            | Secure Authentication          |
| PostgreSQL      | 	Database
| Spring Data JPA |	Database Access
| Hibernate       |	ORM
| Maven           |	Dependency Management
| Swagger/OpenAPI |	API Documentation
| DataFaker	      | Sample Data Generation
         
### Project Architecture

```mermaid
  src/
├── main/
│   ├── java/
│   │   └── com/example/FinanceTracker/  
│   │       ├── config/               # Configuration classes (e.g., Beans, CORS)
│   │       ├── controller/           # REST Controllers
│   │       ├── dto/                  # Data Transfer Objects
│   │       ├── entity/               # JPA / Database Entities
│   │       ├── exception/            # Custom exceptions and global handlers
│   │       ├── repository/           # Data access repositories
│   │       ├── security/             # Security configuration, filters, and JWT logic
│   │       └── service/              # Business logic interfaces and implementations
│   └── resources/
│       └── application.properties    # Application configuration file  

```

## Main Modules
### User Module
The User Module manages user registration and authenticarion within the application.
It uses Spring Security and JWT-based authentication to ensure secure access to protected resources while maintaining user-specific financial data. 
### Transaction Module
The Transaction Module is responsible for recording and managing financial transactions. 
User can create, update, delete and view income and expense records, while also filtering transactions by category and month for better financial tracking. 
### Reporting Module
The Reporting Module provides insights into financial activities by generating monthly summaries and category-wise spending reports.
It helps users analyze their income, expenses, and overall financial performance through aggregated data.
### Budget Module
The Budget Module allows users to set spending limits for different categories and monitor their financial goals.
It compares actual spending against predefined budgets, calculates remaining balances, and indicates whether the budget is within limits or exceeded.

## Validation

Implemented using Jakarta Validation.Examples: @NotBlank, @NotNull, @Email, @Size, @Positive
All request DTOs are validated using:@Valid

## Global Exception Handling

Implemented using: @RestControllerAdvice.

Handles:
* Validation Errors
* Resource Not Found Exceptions
* Runtime Exceptions
* Generic Exceptions

Returns consistent API error responses.

## Pagination

Implemented using Spring Data Pageable.

Example:

`Page<TransactionResponse>`

Supports efficient retrieval of large transaction datasets.

## CSV Export

Export transaction data into CSV format for reporting and analysis.

## API Documentation

Swagger/OpenAPI is integrated for:

* API Testing
* Endpoint Documentation
* Request/Response Inspection

Access:

http://localhost:8080/swagger-ui.html

## Deployment and Infrastructure

The application was initially developed using an in-memory database and later migrated to PostgreSQL to provide persistent data storage suitable for production environments. 
The database migration involved updating the data source configuration, validating entity mappings, and ensuring that all application data was correctly persisted.

To simplify deployment and maintain consistency across different environments, the application was containerized using Docker. 
A Docker image was created for the Spring Boot application, enabling it to run independently of the host machine and making the deployment process more reliable and reproducible.

The Dockerized application was successfully deployed on Render, allowing the backend services and API documentation to be publicly accessible. 
This deployment experience provided practical exposure to containerization, cloud deployment, environment configuration, and managing production-ready applications.

## Database Configuration

Example:

spring.datasource.url=jdbc:postgresql://localhost:5432/finance_tracker
spring.datasource.username=postgres
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update

## How to Run
### Clone Repository
git clone https://github.com/prvmeenu/finance-tracker-backend
### Navigate to Project
cd finance-tracker-backend
### Configure PostgreSQL
Create database:

CREATE DATABASE finance_tracker;
### Run Application
mvn spring-boot:run

# Current Project Status
## Completed
* Authentication
* Authorization
* DTO Refactoring
* Validation
* Global Exception Handling
* PostgreSQL Migration
* Pagination
* CSV Export
* Reporting
* Budget Management
* Swagger Documentation
* Docker Containerization
* Render Deployment

Core backend development is complete.


Through this project, the following concepts were implemented and practiced:

* Spring Boot Development
* REST API Design
* Spring Security
* JWT Authentication
* DTO Architecture
* Validation
* Exception Handling
* PostgreSQL Integration
* Reporting Systems
* Budget Analysis
* Pagination
* Backend Project Structuring

### Github link :
You can access the complete source code, project documentation, and future updates through the GitHub repository:
https://github.com/prvmeenu/finance-tracker-backend

### Swagger UI:
The API documentation is deployed and publicly accessible through Swagger UI. 
It provides an interactive interface to explore and test all available endpoints, view request and response models, and understand the application's API structure.
https://finance-tracker-backend-u1k3.onrender.com/swagger-ui/index.html

Users can execute API requests directly from the browser using the "Try it out" feature provided by Swagger UI, making it easier to understand and test the backend functionality.

Author
Meenakshi Veerappan

