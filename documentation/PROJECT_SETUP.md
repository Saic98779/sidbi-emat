# EMAT Project Setup - Complete Guide

## Project Overview

**EMAT** (Enterprise Management Application Tool) is a Spring Boot 4.0.1 enterprise application with:
- User management system
- Role-based access control (9 different roles)
- Oracle database integration
- RESTful API endpoints
- Spring Security integration

## Technology Stack

| Technology | Version |
|-----------|---------|
| Java | 17 LTS |
| Spring Boot | 4.0.1 |
| Spring Framework | 7.0.2 |
| Spring Data JPA | 4.0.1 |
| Spring Security | Latest |
| Hibernate | 7.2.0.Final |
| Oracle JDBC Driver | 23.2.0.0 |
| Lombok | 1.18.30 |
| Maven | 3.6+ |

## Project Structure

```
emat/
├── src/
│   ├── main/
│   │   ├── java/org/emat/
│   │   │   ├── EmatApplication.java          # Spring Boot entry point
│   │   │   ├── controller/                   # REST Controllers
│   │   │   │   ├── UserController.java
│   │   │   │   └── HealthController.java
│   │   │   ├── service/                      # Business Logic
│   │   │   │   └── UserService.java
│   │   │   ├── entity/                       # JPA Entities
│   │   │   │   └── User.java
│   │   │   ├── repository/                   # Data Access Layer
│   │   │   │   └── UserRepository.java
│   │   │   ├── dto/                          # Data Transfer Objects
│   │   │   │   ├── CreateUserRequest.java
│   │   │   │   └── UserResponse.java
│   │   │   ├── enums/                        # Enumerations
│   │   │   │   └── Role.java
│   │   │   ├── config/                       # Configuration Classes
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── DatabaseConfig.java
│   │   │   ├── exception/                    # Exception Handling
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── ErrorResponse.java
│   │   │   └── init/                         # Initialization
│   │   │       └── DataInitializer.java
│   │   └── resources/
│   │       └── application.properties        # Configuration
│   └── test/
│       └── java/                             # Unit tests
├── db/
│   └── oracle-schema.sql                     # Oracle database schema
├── pom.xml                                   # Maven configuration
├── README.md                                 # General documentation
├── QUICKSTART.md                             # Quick start guide
└── ORACLE_DATABASE_GUIDE.md                  # Database setup guide
```

## Prerequisites

- **Java 17+** (Install from [oracle.com](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.6+** (Install from [maven.apache.org](https://maven.apache.org/download.cgi))
- **Oracle Database** (Connected to: 51.210.96.119:1521/XEPDB1)
- **Git** (Optional, for version control)

## Installation & Setup

### 1. Clone or Extract Project

```bash
cd C:\Users\Chait\Desktop\projects\emat
```

### 2. Verify Java Installation

```bash
java -version
# Expected: Java 17 or higher
```

### 3. Verify Maven Installation

```bash
mvn -version
# Expected: Apache Maven 3.6.0 or higher
```

### 4. Configure Oracle Database

Follow the steps in `ORACLE_DATABASE_GUIDE.md`:
- Create tables using `db/oracle-schema.sql`
- Verify database connection
- Update credentials if needed

### 5. Build the Project

```bash
# Navigate to project directory
cd C:\Users\Chait\Desktop\projects\emat

# Clean and compile
mvn clean compile

# Run tests (optional)
mvn test

# Build executable JAR
mvn clean package
```

### 6. Run the Application

**Option A: Using Maven**
```bash
mvn spring-boot:run
```

**Option B: Using JAR file**
```bash
java -jar target/emat.jar
```

**Option C: Using IDE**
- Right-click on `EmatApplication.java`
- Select "Run" or "Debug"

### 7. Verify Application Started

```bash
curl http://localhost:8080/emat/health
# Expected response: {"status":"UP","timestamp":"...","application":"EMAT"}
```

## Available Roles

The system supports these 9 roles:

1. **DIA** - Department Internal Audit
2. **BSE** - Bombay Stock Exchange
3. **GT_FIELD_TEAM** - Ground Team Field
4. **GT_PMU** - Ground Team PMU
5. **SIDBI_SDE** - SIDBI Software Development Engineer
6. **SIDBI_RO** - SIDBI Regional Officer
7. **SIDBI_HO_MAKER** - SIDBI Head Office Maker
8. **SIDBI_HO_CHECKER** - SIDBI Head Office Checker
9. **CLUSTER_EXPERT** - Cluster Expert

## API Endpoints

### Health & Info

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/emat/health` | Application health check |
| GET | `/emat/` | Welcome message |

### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/emat/api/users` | Create new user |
| GET | `/emat/api/users` | Get all users |
| GET | `/emat/api/users/{id}` | Get user by ID |
| GET | `/emat/api/users/username/{username}` | Get user by username |
| PUT | `/emat/api/users/{id}` | Update user |
| DELETE | `/emat/api/users/{id}` | Delete user |
| PATCH | `/emat/api/users/{id}/toggle-status` | Toggle user active status |

### Example Requests

**Create User**
```bash
curl -X POST http://localhost:8080/emat/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "SecurePass123@",
    "email": "john.doe@emat.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "DIA"
  }'
```

**Get All Users**
```bash
curl http://localhost:8080/emat/api/users
```

**Get User by ID**
```bash
curl http://localhost:8080/emat/api/users/1
```

**Update User**
```bash
curl -X PUT http://localhost:8080/emat/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "NewPass456@",
    "email": "john.doe@emat.com",
    "firstName": "John",
    "lastName": "Updated",
    "role": "BSE"
  }'
```

**Delete User**
```bash
curl -X DELETE http://localhost:8080/emat/api/users/1
```

**Toggle User Status**
```bash
curl -X PATCH http://localhost:8080/emat/api/users/1/toggle-status
```

## Sample Data

On application startup, the following sample users are created:

| Username | Password | Email | Role |
|----------|----------|-------|------|
| admin | admin123 | admin@emat.com | DIA |
| bse_user | bse123 | bse@emat.com | BSE |
| field_team | field123 | field@emat.com | GT_FIELD_TEAM |
| pmu_user | pmu123 | pmu@emat.com | GT_PMU |
| sidbi_sde | sidbi123 | sde@sidbi.com | SIDBI_SDE |

## Maven Dependency Management

The project uses proper dependency management with:

**Properties Defined:**
```xml
<oracle.jdbc.version>23.2.0.0</oracle.jdbc.version>
<lombok.version>1.18.30</lombok.version>
```

**Dependency Management Section:**
- Centralizes version management
- Ensures consistency across modules
- Easier maintenance and updates

**Key Dependencies:**
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Oracle JDBC Driver (ojdbc11)
- Lombok
- Spring Boot DevTools
- Testing dependencies

## Configuration Files

### application.properties

Located at: `src/main/resources/application.properties`

**Key Settings:**
```properties
# Server
server.port=8080
server.servlet.context-path=/emat

# Oracle Database
spring.datasource.url=jdbc:oracle:thin:@51.210.96.119:1521:XEPDB1
spring.datasource.username=SYSTEM
spring.datasource.password=Saichaitu98@

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect

# Connection Pool (HikariCP)
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
```

## Build Configuration

### pom.xml Highlights

**Parent: Spring Boot Starter Parent (4.0.1)**
- Manages all Spring dependencies
- Configures Maven plugins
- Sets compiler source/target to Java 17

**Plugin Configuration:**
- Maven Compiler Plugin - Java 17 compilation
- Spring Boot Maven Plugin - Executable JAR creation
- Maven Resources Plugin - Resource file encoding
- Maven Surefire Plugin - Test execution

## Coding Standards

✅ **Implemented Best Practices:**

1. **Separation of Concerns**
   - Controllers handle HTTP requests
   - Services contain business logic
   - Repositories handle data access

2. **Dependency Injection**
   - Constructor injection preferred
   - No @Autowired annotations

3. **DTOs**
   - CreateUserRequest for input
   - UserResponse for output
   - Prevents entity exposure

4. **Exception Handling**
   - GlobalExceptionHandler for centralized handling
   - StandardErrorResponse format

5. **JPA Best Practices**
   - Entity lifecycle methods (@PrePersist, @PreUpdate)
   - Proper annotations (nullable, unique)
   - Timestamp auditing (createdAt, updatedAt)

6. **Lombok Usage**
   - @Data for getters/setters
   - @NoArgsConstructor for constructors
   - @AllArgsConstructor for full constructors

7. **Security**
   - BCrypt password encoding
   - Spring Security integration
   - Password never returned in API response

8. **Documentation**
   - JavaDoc comments on all public classes/methods
   - Inline comments for complex logic

## Development Workflow

### 1. Development Mode
```bash
mvn spring-boot:run
# Auto-reload enabled via DevTools
# Change code and refresh browser
```

### 2. Testing
```bash
mvn test
```

### 3. Building for Deployment
```bash
mvn clean package
# Creates: target/emat.jar (executable)
```

### 4. Running Tests with Coverage
```bash
mvn clean test jacoco:report
```

## Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| Port 8080 already in use | Change `server.port` in application.properties |
| Cannot connect to Oracle | Verify network connectivity, credentials, and Oracle status |
| Compilation errors | Run `mvn clean install` |
| Database schema not created | Execute `db/oracle-schema.sql` manually |
| Lombok not working | Enable annotation processing in IDE |

## Next Steps

1. ✅ Set up the project
2. ✅ Run the application
3. ✅ Test API endpoints
4. 📝 Implement additional features
5. 🔐 Add JWT authentication
6. 🗂️ Implement pagination/filtering
7. 📊 Add audit logging
8. 🧪 Write comprehensive tests

## Documentation Files

- **README.md** - General project information
- **QUICKSTART.md** - Quick start guide
- **ORACLE_DATABASE_GUIDE.md** - Database configuration
- **PROJECT_SETUP.md** - This file (complete setup guide)

## Support & Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Hibernate ORM](https://hibernate.org/orm/)
- [Oracle Database Documentation](https://docs.oracle.com/database/)
- [Maven Documentation](https://maven.apache.org/)

## License & Credits

This project is part of the EMAT initiative for enterprise management.

---

**Last Updated:** July 23, 2026
**Version:** 1.0-SNAPSHOT
**Environment:** Development

