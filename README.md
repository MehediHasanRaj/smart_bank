# Smart Bank
 A Digital Banking

### 🌟 Key Features

1. **💻 Digital Banking Service**
   - Convenient, secure, and accessible banking.

2. **🔒 Highly Secured Authentication**
   - **📩 Login Alert**: Real-time notifications for login activities to keep your account secure.
   - **🛡️ Latest JWT Implementation**: Cutting-edge JSON Web Token technology for seamless and secure authentication.

3. **🌐 Third-Party API Integration**
   - **✉️ Email Service**: Effortless communication with external APIs for an enhanced user experience.

4. **🤖 AI Implementation for Enhanced Experience**
   - **💬 Intelligent Chatbot**: 24/7 support providing instant and personalized assistance.

5. **📄 PDF Bank Statement Generation**
   - Quickly generate and download detailed bank statements in PDF format for easy financial tracking.

6. **🚀 Performance Improvements**
   - **🗄️ Efficient Database Design**: Optimized database structure for faster data retrieval and improved scalability.
   - **⚡ Caching Implementation (Redis)**: Enhanced performance with in-memory data caching to reduce response times and increase system efficiency.


### 🚀 How to Run the Project
Follow these steps to set up and run the project locally:

#### 🛠️ Prerequisites
Make sure you have the following installed:

- Java (version 17 or higher) ☕
- Maven (version 3.8 or higher) 📦
- MySQL (for database setup) 🗃️
- Git (to clone the repository) 🛠️

#### 📦 Clone the Repository
```bash
git clone https://github.com/mehedihasanraj007/smart_bank
cd smart_bank
```

#### 🔧 Configure the Application
1. Create a database in MySQL:
```SQL
CREATE DATABASE smart_bank;
```
2. Update the application.properties file in the src/main/resources directory with your MySQL credentials:
properties

```java
spring.datasource.url=jdbc:mysql://localhost:3306/smart_bank
spring.datasource.username=<usename>
spring.datasource.password=<password>
```

#### ▶️ Run the Application
Use Maven to build and run the project:
```bash
mvn spring-boot:run
```

#### 🌐 Access the Application
Open your browser and navigate to:
```html
http://localhost:8080
```


#### 📜 API Documentation
The API documentation (Swagger UI) is available at:
```html
http://localhost:8080/swagger-ui.html
```


### 📧 Contact
- **Author**: Md Mehedi Hasan Raj
- **GitHub**: [mehedihasanraj007](https://github.com/mehedihasanraj007)
- **Email**: [mehedihasanraj007@gmail.com](mailto:mehedihasanraj007@gmail.com)
- **Portfolio Website**: [mehedi-hasan-raj.co.uk](https://mehedi-hasan-raj.co.uk)
### 🛠️ Dependencies
Dependency for the project
```xml
<dependencies>
    <!-- Spring Boot Starter for Web Applications -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Boot Starter for Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Spring Boot Starter Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <!-- JWT Implementation -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.6</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.12.6</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.12.6</version>
    </dependency>

    <!-- Spring AI Integration -->
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-openai</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
    </dependency>

    <!-- PDF Generation -->
    <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itextpdf</artifactId>
        <version>5.5.13.3</version>
    </dependency>

    <!-- SpringDoc for API Documentation -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.5.0</version>
    </dependency>
</dependencies>

```
Additionlly, ensure to import the Spring AI BOm in your dependency management
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-bom</artifactId>
            <version>1.0.0-M4</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
