## 📦 Project Dependencies

This project uses the following key dependencies to provide its functionality:

### 1. spring-boot-starter-data-jpa
- Provides integration with JPA and Hibernate ORM for database access.
- Simplifies CRUD and query operations on database entities.

### 2. spring-boot-starter-data-rest
- Automatically exposes Spring Data repositories as RESTful APIs.
- Allows quick creation of REST endpoints without writing controllers for basic operations.

### 3. spring-boot-starter-jdbc
- Adds JDBC support for direct SQL queries and database interactions.

### 4. spring-boot-starter-web
- Includes Spring MVC and embedded Tomcat server for building RESTful web applications.

### 5. mysql-connector-j
- Official MySQL JDBC driver for connecting the application to MySQL databases.

### 6. spring-boot-starter-test
- Provides testing libraries such as JUnit and Mockito for unit and integration testing.
- Scope set to `test` to include only in test builds.

### 7. modelmapper
- A library for mapping between Java objects, useful for converting entities to DTOs and vice versa.

### 8. lombok
- Generates boilerplate code like getters, setters, and constructors at compile time.
- Scope set to `provided` since it’s only needed during compilation.

### 9. spring-boot-starter-websocket
- Enables WebSocket support for real-time, full-duplex communication.

### 10. jackson-datatype-jsr310
- Adds support for Java 8 Date/Time API types in JSON serialization/deserialization with Jackson.

---

These dependencies enable the project to manage database entities, expose REST and WebSocket endpoints, connect to MySQL, handle JSON data including date/time, and support testing and clean code practices.
