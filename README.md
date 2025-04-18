# 🚀 Microservices Architecture with Spring Boot & Kafka

A robust, event-driven microservices architecture leveraging Spring Boot and Apache Kafka for asynchronous communication between services.

## 📋 Table of Contents

- [Architecture Overview](#-architecture-overview)
- [Microservices](#-microservices)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Configuration](#-configuration)
- [Installation & Setup](#-installation--setup)
- [API Documentation](#-api-documentation)
- [Kafka Integration](#-kafka-integration)
- [Roadmap](#-roadmap)
- [License](#-license)
- [Author](#-author)

## 🏗 Architecture Overview

```
┌────────────────┐     ┌────────────────┐     ┌────────────────┐
│                │     │                │     │                │
│    Product     │     │     Order      │     │   Inventory    │
│    Service     │◄────┤    Service     │◄────┤    Service     │
│                │     │                │     │                │
└───────┬────────┘     └───────┬────────┘     └───────┬────────┘
        │                      │                      │
        │                      │                      │
        ▼                      ▼                      ▼
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│                         Apache Kafka                        │
│                                                             │
└─────────────────────────────────────────────────────────────┘
        ▲                      ▲                      ▲
        │                      │                      │
        │                      │                      │
┌───────┴────────┐     ┌───────┴────────┐     ┌───────┴────────┐
│                │     │                │     │                │
│    Product     │     │     Order      │     │    Inventory   │
│   Database     │     │    Database    │     │    Database    │
│                │     │                │     │                │
└────────────────┘     └────────────────┘     └────────────────┘
```

This project implements a distributed system with three independent microservices communicating via Apache Kafka. The architecture follows modern design principles for building scalable, resilient, and maintainable applications.

## 🧩 Microservices

### 🛒 Product Service
Manages the product catalog with comprehensive product information:
- Product details (name, description, price)
- Category management
- Product metadata

### 📦 Order Service
Handles the complete order lifecycle:
- Order creation and processing
- Order status tracking
- Customer information

### 🏬 Inventory Service
Maintains real-time inventory data:
- Stock levels and availability
- Warehouse locations
- Inventory adjustments

## 💻 Technology Stack

| Technology      | Version | Purpose                                |
|-----------------|---------|----------------------------------------|
| Spring Boot     | 2.7.0   | Microservices framework                |
| Spring Web      | 2.7.0   | RESTful API development                |
| Spring Data JPA | 2.7.0   | ORM & database operations              |
| MySQL           | 8.0     | Persistent data storage                |
| Apache Kafka    | 3.2.0   | Event-driven messaging                 |
| Lombok          | 1.18.24 | Boilerplate code reduction             |
| ModelMapper     | 3.1.0   | Object mapping                         |
| Maven           | 3.8.6   | Dependency and build management        |

## 📁 Project Structure

```
microservices-kafka-app/
│
├── product-service/
│   ├── src/
│   ├── pom.xml
│   └── README.md
│
├── order-service/
│   ├── src/
│   ├── pom.xml
│   └── README.md
│
├── inventory-service/
│   ├── src/
│   ├── pom.xml
│   └── README.md
│
├── docker-compose.yml
├── README.md
└── LICENSE
```

Each service is a complete Spring Boot application with its own:
- Controllers, Services, Repositories
- Entity models and DTOs
- Kafka configuration
- Database configuration

## ⚙️ Configuration

### Application Properties

Each microservice has its own `application.yml` with service-specific configurations:

<details>
<summary><b>Product Service Configuration</b></summary>

```yaml
spring:
  application:
    name: product
  datasource:
    url: jdbc:mysql://localhost:3306/product_kafka_db
    username: root
    password: 1234
  jpa:
    hibernate:
      ddl-auto: update

server:
  port: 8081

kafka:
  bootstrap-servers: localhost:9092
  topic:
    product: product-topic
```
</details>

<details>
<summary><b>Order Service Configuration</b></summary>

```yaml
spring:
  application:
    name: order
  datasource:
    url: jdbc:mysql://localhost:3306/order_kafka_db
    username: root
    password: 1234
  jpa:
    hibernate:
      ddl-auto: update

server:
  port: 8082

kafka:
  bootstrap-servers: localhost:9092
  topic:
    order: order-topic
```
</details>

<details>
<summary><b>Inventory Service Configuration</b></summary>

```yaml
spring:
  application:
    name: inventory
  datasource:
    url: jdbc:mysql://localhost:3306/inventory_kafka_db
    username: root
    password: 1234
  jpa:
    hibernate:
      ddl-auto: update

server:
  port: 8083

kafka:
  bootstrap-servers: localhost:9092
  topic:
    inventory: inventory-topic
```
</details>

### Database Setup

Three separate MySQL databases are used:
- `product_kafka_db`
- `order_kafka_db`
- `inventory_kafka_db`

### Kafka Topics

The following Kafka topics facilitate inter-service communication:
- `product-topic`
- `order-topic`
- `inventory-topic`

## 🚀 Installation & Setup

### Prerequisites

- JDK 17+
- MySQL 8.0+
- Apache Kafka 3.2.0+
- Maven 3.8.6+

### Step-by-Step Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/microservices-kafka-app.git
   cd microservices-kafka-app
   ```

2. **Configure MySQL**
   ```bash
   # Create required databases
   mysql -u root -p
   CREATE DATABASE product_kafka_db;
   CREATE DATABASE order_kafka_db;
   CREATE DATABASE inventory_kafka_db;
   ```

3. **Start Kafka**
   ```bash
   # Start Zookeeper
   bin/zookeeper-server-start.sh config/zookeeper.properties

   # Start Kafka broker
   bin/kafka-server-start.sh config/server.properties
   ```

4. **Build and run microservices**
   ```bash
   # Product Service
   cd product-service
   mvn clean install
   mvn spring-boot:run

   # Order Service
   cd ../order-service
   mvn clean install
   mvn spring-boot:run

   # Inventory Service
   cd ../inventory-service
   mvn clean install
   mvn spring-boot:run
   ```

## 📝 API Documentation

### Product Service (Port: 8081)

| Method | Endpoint               | Description           |
|--------|------------------------|-----------------------|
| POST   | `/api/products`        | Create product        |
| GET    | `/api/products`        | Get all products      |
| GET    | `/api/products/{id}`   | Get product by ID     |
| PUT    | `/api/products/{id}`   | Update product        |
| DELETE | `/api/products/{id}`   | Delete product        |

### Order Service (Port: 8082)

| Method | Endpoint             | Description         |
|--------|----------------------|---------------------|
| POST   | `/api/orders`        | Create order        |
| GET    | `/api/orders`        | Get all orders      |
| GET    | `/api/orders/{id}`   | Get order by ID     |
| PUT    | `/api/orders/{id}`   | Update order        |
| DELETE | `/api/orders/{id}`   | Delete order        |

### Inventory Service (Port: 8083)

| Method | Endpoint                 | Description             |
|--------|--------------------------|-------------------------|
| POST   | `/api/inventories`       | Create inventory entry  |
| GET    | `/api/inventories`       | Get all inventory items |
| GET    | `/api/inventories/{id}`  | Get inventory by ID     |
| PUT    | `/api/inventories/{id}`  | Update inventory        |
| DELETE | `/api/inventories/{id}`  | Delete inventory        |

## 🔄 Kafka Integration

The microservices communicate asynchronously through Kafka events:

```
Event Flow:

Product Service ──[product-created]──> product-topic ──> Order Service
                                                   └──> Inventory Service

Order Service ──[order-placed]──> order-topic ──> Inventory Service
                                             └──> Product Service (for analytics)

Inventory Service ──[stock-updated]──> inventory-topic ──> Product Service
                                                      └──> Order Service
```

**Event Flow Examples:**
- When a new product is created, Product Service publishes an event to `product-topic`
- Order Service consumes this event to update its product catalog
- When an order is placed, Order Service publishes to `order-topic`
- Inventory Service consumes this event to update stock levels

## 🔮 Roadmap

- [ ] Implement comprehensive Kafka listeners for all services
- [ ] Add Circuit Breakers using Resilience4J
- [ ] Integrate OpenAPI (Swagger) documentation
- [ ] Containerise with Docker and Docker Compose
- [ ] Implement Spring Cloud Config for centralised configuration
- [ ] Add service discovery with Eureka
- [ ] Implement API Gateway with Spring Cloud Gateway
- [ ] Add distributed tracing with Sleuth and Zipkin

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Nipun**  
Software Engineering Student  
Specialising in scalable backend systems with Spring Boot, Kafka, and Microservices architecture.
