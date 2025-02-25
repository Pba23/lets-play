# Spring Boot CRUD API with MongoDB

## 📝 Description

This project is a basic CRUD (Create, Read, Update, Delete) API built with **Spring Boot** and **MongoDB**. It provides user management and product management functionalities, adhering to RESTful principles. The application includes token-based authentication and role-based authorization to secure the APIs.

---

## 🎯 Objectives

- Develop a RESTful API for managing users and products.
- Implement token-based authentication and role-based authorization.
- Ensure the API is secure, scalable, and adheres to best practices.
- Handle errors gracefully and avoid returning 5XX errors.
- Implement security measures such as password hashing, input validation, and HTTPS.

---

## 🛠️ Features

### 1. **User Management**
- **Register**: Create a new user.
- **Login**: Authenticate and receive a JWT token.
- **Get User**: Retrieve user details by ID.
- **Update User**: Update user information.
- **Delete User**: Delete a user (admin only).

### 2. **Product Management**
- **Create Product**: Add a new product (authenticated users only).
- **Get Product**: Retrieve product details by ID.
- **Get All Products**: Retrieve all products (public access).
- **Update Product**: Update product information (owner or admin only).
- **Delete Product**: Delete a product (owner or admin only).

### 3. **Authentication & Authorization**
- Token-based authentication using **JWT**.
- Role-based access control:
  - **Admin**: Can delete users and products.
  - **User**: Can create, update, and delete their own products.

### 4. **Security Measures**
- Password hashing using **BCrypt**.
- Input validation to prevent MongoDB injection attacks.
- Sensitive information (e.g., passwords) is not returned in API responses.
- HTTPS support for secure data transmission.

### 5. **Error Handling**
- Custom error responses for invalid requests, unauthorized access, and other exceptions.
- No 5XX errors are returned to the client.

---

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher.
- **MongoDB** installed and running.
- **Maven** for dependency management.
- **Postman** or any API testing tool.

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://learn.zone01dakar.sn/git/pba/lets-play/.git
   cd lets-play
2. **Configure MongoDB**:
   - Update the MongoDB connection settings in `application.properties`:
     ```properties
     spring.data.mongodb.uri=mongodb://localhost:27017/user_product_db
     ```

3. **Build the project**:
   ```bash
   mvn clean install
4. **Run the application**:
   ```bash
   mvn spring-boot:run
5. **Access the API**:
   - The API will be available at `https://localhost:8443`.
   - You can interact with the API using tools like **Postman**, **cURL**, or any HTTP client.
   - Make sure your MongoDB server is running, as the application depends on it for data storage.