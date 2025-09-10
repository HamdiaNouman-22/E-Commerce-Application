Project Overview
This is a Spring Boot based RESTful API backend for an E-Commerce application. It provides secure role-based authentication with JWT, complete CRUD operations for products, carts, orders, reviews, and customer messages, as well as file upload support for product images. The backend is integrated with Swagger/OpenAPI for interactive API documentation.

Features
User Management
-Role-based Authentication & Authorization using JWT (Admin, Customer)
-Secure user signup/login (Create, Read)
-User roles and profile handling

Product Management 
-Create → Admin adds products with images & shade variants
-Read → Customers browse all products, filter by brand/category, sort by price, and view details
-Update → Admin updates stock & price
Delete → Admin deletes products

Cart & Orders
Cart  → Customers add, view, update, and remove items
Orders → Place new order (Create), view orders (Read), cancel order (Delete)

Reviews
Create → Customers add reviews for products
Read → Fetch reviews by product ID
Delete → Remove reviews (Admin/Customer)

Messages 
Create → Customers send messages/queries
Read → Admin views messages
Update → Mark status as “checked”

Tech Stack
Backend: Spring Boot, Spring Security, Spring Data JPA
Build Tool: Maven
Java Version: Java 17+
Database: PostgreSQL (via Supabase)
Authentication: JWT
Validation: Jakarta Validation
API Documentation: SpringDoc OpenAPI (Swagger UI)
Other: Multipart file handling for product image uploads, Gemini API for chatbot

Local Swagger UI: http://localhost:8080/swagger-ui.html
Online Swagger Spec:[👉 Swagger UI](https://editor.swagger.io/?url=https://raw.githubusercontent.com/HamdiaNouman-22/E-Commerce-Application/refs/heads/main/src/main/java/com/example/pinkbullmakeup/docs/api-docs.json)
