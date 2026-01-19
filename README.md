# E-Commerce Microservices Application

   **The Project is ongoing**

A scalable **E-commerce backend system** built using **Spring Boot Microservices architecture**.  
The application demonstrates real-world concepts like **service discovery, API gateway, inter-service communication, security, event-driven architecture, containerization, and monitoring**.

This project is implemented as a **monorepo** with a single parent (`ecommerce-parent`) managing all microservices.

---

## Architecture Overview

The system follows a distributed microservices architecture:

- **API Gateway** – Single entry point for all client requests
- **Service Discovery (Eureka Server)** – Dynamic service registration and lookup
- **Product Service** – Manages product catalog
- **Order Service** – Handles order creation and processing
- **Inventory Service** – Manages stock availability
- **Notification Service** – Sends notifications using event-driven communication
- **Authentication** – Secured using Keycloak
- **Messaging** – Kafka for asynchronous communication
- **Monitoring** – Prometheus and Grafana
- **Containerization** – Dockerized services

---


---

## 🔧 Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Cloud (Eureka, Gateway)**
- **Spring Security**
- **Keycloak**
- **Apache Kafka**
- **MongoDB / MySQL**
- **Docker & Docker Compose**
- **Prometheus & Grafana**
- **Maven**
- **IntelliJ IDEA**
- **Postman**

---

## 🔗 Microservices Description

### 🔹 Product Service 
- Manages product details (name, description, price)
- Exposes REST APIs for product operations
- Uses MongoDB for persistence
- finished

### 🔹 Inventory Service
- Maintains stock availability
- Communicates with Order Service
- Ensures product quantity consistency
- Finished

### 🔹 Order Service
- Handles order creation and processing
- Communicates with Product and Inventory services
- Publishes events to Kafka
- Finished

### 🔹 Notification Service
- Consumes Kafka events
- Sends notifications on order placement
- finished 

### 🔹 Eureka Server
- Central service registry
- Enables service discovery for all microservices
- finished 

### 🔹 API Gateway
- Centralized routing and filtering
- Single entry point for clients
- Integrates authentication and authorization
- finished 

---

## 🔐 Security
- Implemented using **Keycloak**
- Role-based authentication and authorization
- Secures APIs through API Gateway
- finished 

---

## 🔄 Communication
- **Synchronous:** Feign Client / REST calls
- **Asynchronous:** Apache Kafka (event-driven architecture)
- finished 

---

## 🐳 Docker Support
All services are containerized using Docker.  
`docker-compose.yml` is used to run the complete system with one command.

```bash
docker-compose up


