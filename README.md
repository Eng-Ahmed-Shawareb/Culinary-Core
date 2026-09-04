# 🍳 Culinary Core — Culinary School Management System

![Java](https://img.shields.io/badge/Java-25%2B-orange.svg?style=flat\&logo=openjdk)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue.svg?style=flat\&logo=java)
![Database](https://img.shields.io/badge/Database-SQL%20Server-red.svg?style=flat\&logo=microsoftsqlserver)
![Build](https://img.shields.io/badge/Build-Maven-C71A36.svg?style=flat\&logo=apachemaven)
![Architecture](https://img.shields.io/badge/Architecture-Layered%20%7C%20MVC%20%7C%20DAO-success.svg?style=flat)

**Culinary Core** is a desktop **Culinary School Management System** built with **JavaFX** and **Microsoft SQL Server**. The application provides a centralized platform for managing students, chefs, workshops, kitchens, suppliers, ingredient batches, registrations, and consumption records through a structured multi-layer architecture.

The project combines a rich JavaFX desktop interface with a persistent SQL Server backend, separating presentation, business logic, data access, and domain models to keep the system organized and maintainable.

---

## 🌟 Key Features

### 👨‍🍳 Culinary School Management

* **Student Management:** Create, update, view, and manage student records.
* **Chef Management:** Maintain chef information and their involvement in school activities.
* **Workshop Management:** Organize culinary workshops and manage their related information and status.
* **Kitchen Management:** Track and manage kitchens used by the culinary school.

### 📦 Inventory & Ingredient Operations

* **Ingredient Batch Management:** Track ingredient batches and their lifecycle/status.
* **Consumption Tracking:** Record ingredient consumption and connect it with the relevant kitchen/workshop operations.
* **Supplier Management:** Manage suppliers and retrieve supplier-related information.
* **Database-backed Operations:** Major operations are persisted through SQL Server using dedicated DAO classes.

### 🎓 Registration & School Operations

* **Workshop Registration:** Manage student registrations in workshops.
* **Status Management:** Dedicated status enums for workshop, payment, and ingredient-batch states.
* **Dashboard:** Provides a central view for the application's main operations and management workflow.

### 🖥️ Desktop User Interface

* **JavaFX GUI:** A dedicated graphical interface built using JavaFX and FXML.
* **Multiple Screens:** Separate interfaces for students, chefs, workshops, kitchens, suppliers, consumption, ingredient batches, and registration.
* **Light / Dark Styling:** Includes separate CSS stylesheets for standard and dark UI themes.
* **Scene Management:** Centralized navigation through a dedicated `ClsSceneManager` component.

---

## 🏗️ Software Architecture & Design

The project follows a layered structure inspired by **MVC** and **DAO** patterns, keeping UI, business logic, persistence, and domain models separated.

### 🧩 Main Layers

* **GUI Layer:** JavaFX controllers and FXML views handle user interaction and presentation.
* **Service Layer:** Encapsulates business operations and coordinates application logic.
* **DAO Layer:** Handles communication with SQL Server and isolates SQL/database operations from the rest of the application.
* **Model Layer:** Contains domain entities such as students, chefs, kitchens, workshops, suppliers, registrations, consumption records, and ingredient batches.

### 🎯 Design Patterns & Principles

* **DAO Pattern:** Dedicated repository-style classes encapsulate database operations for each major entity.
* **Repository Abstraction:** The `IRepository<T>` interface provides a common abstraction for data-access operations.
* **Singleton Pattern:** Used for shared infrastructure such as the database connection and scene manager.
* **Separation of Concerns:** Controllers, services, DAOs, and models have clearly separated responsibilities.
* **FXML-Based UI:** Views are separated from Java controller logic, making the presentation layer easier to maintain.

---

## 🛠️ Tech Stack

| Technology               | Purpose                               |
| ------------------------ | ------------------------------------- |
| **Java**                 | Core application language             |
| **JavaFX**               | Desktop graphical user interface      |
| **Microsoft SQL Server** | Persistent data storage               |
| **JDBC**                 | Database connectivity                 |
| **Maven**                | Build and dependency management       |
| **PlantUML / Draw.io**   | UML and database design documentation |

---

## 🚀 Getting Started

### Prerequisites

Make sure the following are installed:

* **JDK 25** or later
* **Maven** (or use the included Maven Wrapper)
* Access to a **Microsoft SQL Server** database

### 1. Clone the Repository

```bash
git clone https://github.com/Eng-Ahmed-Shawareb/Culinary-Core.git
cd Culinary-Core
```

### 2. Configure the Database

Create the required SQL Server database and apply the scripts located in:

```text
database/ddl/
```

The application uses a centralized database connection class:

```text
src/main/java/com/culinarycore/dao/singleton/ClsDatabaseConnection.java
```

Update the connection configuration with your own SQL Server host, database name, username, and password before running the application.

### 3. Run the Application

Using the Maven Wrapper:

**Linux / macOS**

```bash
./mvnw clean javafx:run
```

**Windows**

```bat
mvnw.cmd clean javafx:run
```

You can also import the project into IntelliJ IDEA as a Maven project and run `ClsCulinaryCoreApp` directly.

---

## 📂 Project Structure

```text
Culinary-Core/
├── UML/                              # UML class diagram and PlantUML source
├── database/
│   ├── ddl/                          # SQL Server schema scripts
│   └── design/                       # Database design and mapping diagrams
├── src/main/java/com/culinarycore/
│   ├── dao/                          # Data Access Objects
│   │   ├── interfaces/               # Repository abstractions
│   │   └── singleton/                # Shared database connection
│   ├── gui/                          # JavaFX controllers & scene management
│   ├── model/                        # Domain entities and status enums
│   ├── service/                      # Business logic layer
│   └── ClsCulinaryCoreApp.java       # Application entry point
├── src/main/resources/com/culinarycore/gui/
│   ├── *.fxml                        # JavaFX views
│   ├── style.css                     # Main application stylesheet
│   ├── dark-style.css                # Dark theme stylesheet
│   └── cutlery.png                   # Application icon
├── pom.xml                           # Maven configuration
├── mvnw / mvnw.cmd                   # Maven Wrapper
└── README.md                         # Project documentation
```

---

## 🗄️ Database Design

The project includes both the database implementation and its design artifacts.

* SQL schema scripts are organized under `database/ddl/`.
* Database design diagrams are stored under `database/design/`.
* The repository includes a UML class diagram under `UML/`.

These artifacts make it easier to understand the relationships between the application's domain entities and the underlying database structure.

---

## 📚 Academic Context

Culinary Core was developed as an academic software engineering / database-oriented project with a focus on applying:

* Object-Oriented Programming
* Layered Architecture
* MVC concepts
* DAO and Repository patterns
* Database design and SQL
* JavaFX desktop application development
* UML-based software design

---

## 👨‍💻 Contributors

### Ahmed Shawareb
* **Email:** eng.ahmedshawareb@gmail.com
* **GitHub:** [Eng-Ahmed-Shawareb](https://github.com/Eng-Ahmed-Shawareb)
* **LinkedIn:** [Ahmed Shawareb](https://www.linkedin.com/in/ahmed-shawareb-82183133b/)
### Ahmed Salah
* **Email:** adh739184@gmail.com
* **GitHub:** [AhmedMo206](https://github.com/AhmedMo206)
* **LinkedIn:** [Ahmed Salah](https://www.linkedin.com/in/ahmed-mohamed-bb3598343/)
### Mohamed Ayser
* **Email:** aysermohamedal@gmail.com
* **GitHub:** [MohamedAyser1](https://github.com/MohamedAyser1)
* **LinkedIn:** [Mohamed Ayser](https://www.linkedin.com/in/mohamed-ayser-312718337/)

---

## 📌 Project Status

This project is structured as a complete desktop application with a JavaFX frontend, service layer, DAO-based SQL Server persistence, database design artifacts, and UML documentation.

---

## 📄 License

This project is intended primarily for educational and portfolio purposes.
