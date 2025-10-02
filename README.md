**Virtual Bookstore - Spring Boot Backend**
A RESTful backend for a Virtual Bookstore application, built with Spring Boot. This API handles user authentication, book management, shopping carts, and orders.

**Features & Technology**
This project uses Java 17 and Spring Boot 3 to create a secure, stateless API with JWT authentication. It connects to a MySQL database using Spring Data JPA.

Key features include:
User Registration and Login
Book Catalog Management
Shopping Cart and Order Processing
Role-Based Security (USER and ADMIN)

**API Overview**
The API provides endpoints for managing the bookstore's core functionalities.
Authentication (/api/auth): Handles user registration and login.
Books (/api/books): Allows public browsing of books and admin-only management.
Cart (/api/cart): Authenticated endpoints for users to manage their shopping cart.
Orders (/api/orders): Authenticated endpoints for users to place orders and view their history.
