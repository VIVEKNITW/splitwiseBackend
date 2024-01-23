# Splitwise application
## Overview
This is a Splitwise application built using Spring Boot, Maven, and MySQL. The application helps users manage and track shared expenses within a group.
Splitwise is a free app that allows consumers to split expenses with friends. If a group needs to share the cost of a particular bill, Splitwise ensures that anyone who pays is reimbursed the correct amount and with a minimal number of transactions.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Database Setup](#database-setup)
- [Build and Run](#build-and-run)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## Features
- User creation and management
- Group creation and management
- Expense tracking and splitting
- User authentication and authorization (to be done)
- Real-time notifications (optional) (to be done)
- Detailed reports and summaries (to be done)

## Technologies Used

- Spring Boot
- Maven
- MySQL
- Spring Data JPA
- Spring Security
- JUnit & Mockito
- lombok

## Getting Started

To get started with the Splitwise application, follow these steps:

1. Clone the repository: `git clone https://github.com/VIVEKNITW/splitwiseBackend.git`
   

## Configuration

Update the application configuration as needed. Important configuration files:

- `application.properties`: Database configuration, server port, etc.
- ...

## Database Setup

Ensure you have MySQL installed and create a new database for Splitwise. Update the `application.properties` file with your database credentials.

## Build and Run

Build and run the application using Maven:

```bash
mvn clean install
java -jar target/splitwise.jar
```

## Documentation:
Used swagger to document the endpoints, which can help other developers to use the application in their code.
Swagger end point: `http://localhost:8080/swagger-ui/index.html`
