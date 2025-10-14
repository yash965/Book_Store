# 📚 **Virtual Bookstore Backend**

A **robust and secure RESTful backend** for a **Virtual Bookstore Application**, built with **Spring Boot**.
This project provides a complete set of APIs to manage **users, books, shopping carts, and orders**, replicating the experience of a physical bookstore in a digital environment.

The backend is **fully decoupled** from any frontend, allowing seamless integration with **web or mobile clients**.

---

## ✨ **Core Features**

* 🔐 **Secure User Authentication** – Implements **JWT (JSON Web Tokens)** for stateless, secure authentication.
  Includes endpoints for **user registration** and **login**.

* 🧩 **Role-Based Access Control** – Differentiates between:

  * 👤 Regular users (`ROLE_USER`)
  * 🛠️ Administrators (`ROLE_ADMIN`)
    using **Spring Security** to protect sensitive endpoints.

* 📖 **Book Catalog Management** – APIs for browsing, searching, and viewing books.
  Includes **admin-only** endpoints for adding new books to the catalog.

* 🛒 **Shopping Cart Functionality** – Authenticated users can manage a **persistent cart**,
  adding or removing books as they browse.

* 📦 **Order Processing** – Complete workflow for users to create an order from their cart and view their order history.

* 🧱 **RESTful API Design** – Follows REST principles for clean, predictable, and easy-to-use APIs.

* 💾 **Database Integration** – Uses **Spring Data JPA** and **Hibernate** for efficient and reliable interaction with a **MySQL** database.

* 🧰 **DTO Implementation** – Created **DTOs (Data Transfer Objects)** for `Order` and `Cart` to simplify data handling and responses.
  Users only need to provide **username and password** for smooth authentication and interaction.

---

## 🛠️ **Technology Stack**

| Category        | Technology                        |
| --------------- | --------------------------------- |
| **Framework**   | Spring Boot 3                     |
| **Language**    | Java 17                           |
| **Security**    | Spring Security, JWT              |
| **Database**    | Spring Data JPA, Hibernate, MySQL |
| **Build Tool**  | Apache Maven                      |
| **API Testing** | Postman                           |

---

## 🚀 **API Endpoints Guide**

All endpoints are prefixed with:

```bash
/api
```

---

### 🔑 **1. Authentication (`/api/auth`)**

These endpoints are **public** and do **not** require a JWT token.

| Method   | Endpoint    | Description                             | Request Body                              |
| -------- | ----------- | --------------------------------------- | ----------------------------------------- |
| **POST** | `/register` | Creates a new user account.             | `AuthRequest` (username, email, password) |
| **POST** | `/login`    | Authenticates a user and returns a JWT. | `AuthRequest` (username, password)        |

---

### 📚 **2. Books (`/api/books`)**

Endpoints for managing and accessing the **book catalog**.

| Method   | Endpoint | Description                                  | Authentication |
| -------- | -------- | -------------------------------------------- | -------------- |
| **GET**  | `/`      | Retrieves a list of all available books.     | Public         |
| **GET**  | `/{id}`  | Retrieves details for a specific book by ID. | Public         |
| **POST** | `/`      | Adds a new book to the catalog.              | ROLE_ADMIN     |

---

### 🛍️ **3. Shopping Cart (`/api/cart`)**

Endpoints for managing a user’s shopping cart.
Require **JWT authentication** in the header:

```bash
Authorization: Bearer <token>
```

| Method     | Endpoint          | Description                                 | Request Body                        |
| ---------- | ----------------- | ------------------------------------------- | ----------------------------------- |
| **GET**    | `/`               | Retrieves the current user’s shopping cart. | None                                |
| **POST**   | `/items`          | Adds a book to the current user's cart.     | `AddItemRequest` (bookId, quantity) |
| **DELETE** | `/items/{bookId}` | Removes a book from the user’s cart.        | None                                |

---

### 📦 **4. Orders (`/api/orders`)**

Endpoints for processing and viewing user orders.

| Method   | Endpoint | Description                                       | Authentication |
| -------- | -------- | ------------------------------------------------- | -------------- |
| **POST** | `/`      | Creates a new order from the user’s current cart. | ROLE_USER      |
| **GET**  | `/`      | Retrieves a list of the user’s past orders.       | ROLE_USER      |

---

## 🧠 **Additional Notes**

* DTOs are used to simplify data transfer and ensure **clean, minimal responses**.
* Passwords are **securely hashed** before storage.
* The project follows **SOLID principles** and clean architecture for scalability.

