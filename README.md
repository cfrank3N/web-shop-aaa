
# Web Shop – Java Spring Boot Project

This project was developed as a backend and frontend assignment using **Spring Boot**, **Javascript**, and integration with the **Fake Store API**.  
The purpose was to build a simple web shop application with product management, authentication, and order handling.

---

## 📋 Project Overview

- Fetches product data from the **Fake Store API** and stores it in a local SQL database.
- Provides a **web frontend** to display all products.
- Implements a layered architecture: **Controller → Service → Repository**.
- Includes **integration tests** for API validation.
- For higher grade (VG), the project extends functionality with login, roles, and order management.

---

## ✅ Features (Pass / Grade G Requirements)

- **Fake Store API Integration**  
  Fetch all products from the external API and save them to the database.

- **Database Persistence**  
  Store products in a local SQL database.

- **Frontend**  
  Display products in a web interface.

- **Architecture**  
  Code is structured in multiple layers: Controller → Service → Repository.

- **Integration Test**  
  Tests verifies that products returned from the Fake Store API include all relevant attributes.

---

## ⭐ Additional Features (Distinction / Grade VG Requirements)

- **Authentication & Authorization**  
  Implemented with Spring Security.
    - **USER role**: Can purchase products.
    - **ADMIN role**: Can view and delete orders.

- **User Registration**  
  Registration page allows creating accounts with USER or ADMIN roles.

- **Database Sync**  
  On application startup, the database synchronizes with the Fake Store API:
    - Products removed from API are deleted from the database.
    - New products are added.

- **Order Management**
    - USER can place an order.
    - ADMIN can view and manage all orders.

---

## 🛠️ Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Data JPA / Hibernate**
- **Spring Security**
- **Vite**
- **JavaScript**
- **HTML5**
- **BootStrap**
- **MySQL**
- **JUnit** for testing
- **Gradle**

---

## 📂 Project Structure

```
web-shop-aaa/
├── backend/    # Spring Boot backend service
│ ├── src/          # Java source code (controllers, services, repositories, entities)
│ ├── build.gradle  # Gradle configuration
│ └── ...
├── frontend/   # Vanilla JS frontend (Vite)
│ ├── index.html    # App entry point
│ ├── main.js       # Main JS file
│ ├── package.json  # Frontend dependencies
│ └── ...
└── README.md # Project documentation
```

---

## 🚀 Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/cfrank3N/web-shop-aaa.git
   cd web-shop-aaa
   ```

2. **Configure the database**:  
   Update `application.properties` with your database connection settings.

3. **Build and run the backend**:
   ```bash
   ./gradlew bootRun
   ```
   
4. **Build and run the Frontend**

    Navigate to the frontend folder:
   ```bash
    cd frontend
   ```

    Install dependencies:
   ```bash
    npm install
   ```

    Start the Vite dev server:
   ```bash
    npm run dev
   ```

5. **Access the app**:  
   Open the app in your browser at http://localhost:5173

---

## 👥 Contributors

This project was developed as part of a course assignment.  
Contributors:
- [cfrank3N](https://github.com/cfrank3N)
- [ArvidUtas](https://github.com/ArvidUtas)
- [Gaurgle](https://github.com/Gaurgle)

---
