# EMAT - Enterprise Management Application Tool

## Overview
EMAT is a Spring Boot application designed for managing users with role-based access control. It provides a comprehensive REST API for user management with support for multiple organizational roles.

## Project Structure

```
emat/
├── src/
│   ├── main/
│   │   ├── java/org/example/
│   │   │   ├── Main.java                 (Spring Boot Application)
│   │   │   ├── controller/               (REST Controllers)
│   │   │   │   ├── UserController.java
│   │   │   │   └── HealthController.java
│   │   │   ├── service/                  (Business Logic)
│   │   │   │   └── UserService.java
│   │   │   ├── entity/                   (JPA Entities)
│   │   │   │   └── User.java
│   │   │   ├── repository/               (Data Access)
│   │   │   │   └── UserRepository.java
│   │   │   ├── dto/                      (Data Transfer Objects)
│   │   │   │   ├── CreateUserRequest.java
│   │   │   │   └── UserResponse.java
│   │   │   ├── enums/                    (Enumerations)
│   │   │   │   └── Role.java
│   │   │   ├── config/                   (Configuration)
│   │   │   │   └── SecurityConfig.java
│   │   │   └── exception/                (Exception Handling)
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       └── ErrorResponse.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/java/
├── pom.xml                              (Maven Configuration)
└── README.md                            (This file)
```

## Technologies

- **Java 25**
- **Spring Boot 3.3.2**
- **Spring Data JPA**
- **Spring Security**
- **H2 Database**
- **Lombok**
- **Maven**

## Available Roles

The system supports the following roles:
- DIA
- BSE
- GT_FIELD_TEAM
- GT_PMU
- SIDBI_SDE
- SIDBI_RO
- SIDBI_HO_MAKER
- SIDBI_HO_CHECKER
- CLUSTER_EXPERT

## Building the Project

```bash
mvn clean compile
mvn clean package
```

## Running the Application

```bash
mvn spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/emat-1.0-SNAPSHOT.jar
```

The application will start on `http://localhost:8080/emat`

## API Endpoints

### Health Check
- **GET** `/emat/health` - Check application health
- **GET** `/emat/` - Welcome message

### User Management

#### Create User
- **POST** `/emat/api/users`
- **Body:**
  ```json
  {
    "username": "john_doe",
    "password": "password123",
    "email": "john@emat.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "MANPOWER_AGENCY"
  }
  ```

#### Get All Users
- **GET** `/emat/api/users`
- **Response:**
  ```json
  [
    {
      "id": 1,
      "username": "john_doe",
      "email": "john@emat.com",
      "firstName": "John",
      "lastName": "Doe",
      "role": "MANPOWER_AGENCY",
      "isActive": true,
      "createdAt": "2026-07-23T07:49:20",
      "updatedAt": "2026-07-23T07:49:20"
    }
  ]
  ```

#### Get User by ID
- **GET** `/emat/api/users/{id}`

#### Get User by Username
- **GET** `/emat/api/users/username/{username}`

#### Update User
- **PUT** `/emat/api/users/{id}`
- **Body:**
  ```json
  {
    "username": "john_doe",
    "password": "password123",
    "email": "john@emat.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "BSE"
  }
  ```

#### Delete User
- **DELETE** `/emat/api/users/{id}`

#### Toggle User Status
- **PATCH** `/emat/api/users/{id}/toggle-status`

## Database

The application uses H2 in-memory database by default. To access the H2 console:
- URL: `http://localhost:8080/emat/h2-console`
- JDBC URL: `jdbc:h2:mem:ematdb`
- Username: `sa`
- Password: (empty)

## Configuration

Key configuration properties in `application.properties`:
- `server.port=8080` - Server port
- `server.servlet.context-path=/emat` - Application context path
- `spring.jpa.hibernate.ddl-auto=create-drop` - Database schema generation strategy

## Security

- Spring Security is enabled with BCrypt password encoding
- Currently configured to allow public access to user endpoints for development
- Basic HTTP Authentication is configured

## Error Handling

The application includes a global exception handler that provides consistent error responses:

```json
{
  "timestamp": "2026-07-23T07:49:20",
  "status": 400,
  "error": "Bad Request",
  "message": "Username already exists",
  "path": "/emat/api/users"
}
```

## Best Practices Implemented

✅ RESTful API design
✅ Proper separation of concerns (Controller, Service, Repository)
✅ Data Transfer Objects (DTOs)
✅ Dependency Injection
✅ Exception Handling
✅ Lombok for reducing boilerplate
✅ JPA/Hibernate ORM
✅ Password encoding
✅ Transaction management
✅ Timestamp auditing (createdAt, updatedAt)

## Future Enhancements

- JWT Token-based authentication
- Role-based access control (RBAC)
- API documentation with Swagger/SpringDoc
- Pagination and filtering
- Audit logging
- Unit and integration tests

## Contributing

Please ensure all code follows the established conventions:
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Follow Java naming conventions
- Use dependency injection over direct instantiation

## License

This project is provided as-is for educational purposes.

