# 💵 Loan Tracking System Backend

##  Overview
The Loan Tracking System backend is designed to efficiently track loans and manage payment transactions. It provides a robust, data-driven platform with a normalized database architecture to ensure strict data integrity across all financial records.

## 💻 Tech Stack
* **Frontend:** React, TypeScript
* **Backend:** Java, Spring Boot 
* **Database:** PostgreSQL

## ✨ Key Features
* **Comprehensive Loan Management:** Able to track different kindso of 
* **Robust Backend Architecture:** Powered by a Spring Boot backend exposing 20 distinct RESTful API endpoints for seamless frontend-backend communication.
* **Complex Data Integrity:** Utilizes a highly normalized PostgreSQL database schema heavily relying on JPA entities to maintain reliable data states.

## 🚀 Getting Started

### Prerequisites
Before you begin, ensure you have the following installed:
* [Node.js](https://nodejs.org/) (v16 or higher)
* [Java JDK](https://www.oracle.com/java/technologies/downloads/) (v17 or higher)
* [PostgreSQL](https://www.postgresql.org/)
* [Git](https://git-scm.com/)

### Installation & Setup

#### 1. Database Setup
1. Open pgAdmin or your PostgreSQL CLI.
2. Create a new database named `loan_tracking_db`.
3. Update the `application.properties` (or `.yml`) file in the Spring Boot project with your database credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/loan_tracking_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
