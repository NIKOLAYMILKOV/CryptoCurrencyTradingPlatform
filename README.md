
# CryptoCurrencyTradingPlatform

A simple Spring Boot project for managing cryptocurrency assets and users.

---

## 📌 How to Run the Project — Step by Step

This guide will help you run the project from start to finish.

---

## 1️⃣ Clone the Repository

First, clone the project and go to the project folder:

```bash
git clone https://github.com/NIKOLAYMILKOV/CryptoCurrencyTradingPlatform.git
cd CryptoCurrencyTradingPlatform
```

---

## 2️⃣ Create the Database

Log in to your MySQL server and create the database:

```sql
CREATE DATABASE trading;
```

---

## 3️⃣ Configure `application.properties`

Make sure your `src/main/resources/application.properties` contains:

```properties
spring.application.name=CryptoCurrencyTradingPlatform

server.port=8888

spring.datasource.url=jdbc:mysql://localhost:3306/trading
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
```

---

## 4️⃣ Set Environment Variables

**On macOS/Linux:**

```bash
export DB_USERNAME=root
export DB_PASSWORD=your_mysql_password
```

**On Windows (PowerShell):**

```powershell
setx DB_USERNAME "root"
setx DB_PASSWORD "your_mysql_password"
```

---

## 5️⃣ Build and Run the Project

Use Maven to build and run the application:

```bash
mvn clean install
mvn spring-boot:run
```

---

✅ **Done!**
Your CryptoCurrencyTradingPlatform should now be running on [http://localhost:8888](http://localhost:8888).

---

**Happy coding! 🚀**
