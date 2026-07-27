# EMAT - Quick Reference Card

## 🚀 Quick Start Commands

### Build Project
```bash
mvn clean compile          # Compile only
mvn clean package          # Build JAR
mvn clean install          # Build and install
```

### Run Application
```bash
mvn spring-boot:run                    # Via Maven
java -jar target/emat.jar             # Via JAR
```

### Stop Application
```bash
Ctrl+C    # In terminal
```

---

## 📋 API Endpoints Quick Reference

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/emat/health` | Health check |
| GET | `/emat/` | Welcome |
| POST | `/emat/api/users` | Create user |
| GET | `/emat/api/users` | Get all users |
| GET | `/emat/api/users/{id}` | Get by ID |
| GET | `/emat/api/users/username/{username}` | Get by username |
| PUT | `/emat/api/users/{id}` | Update user |
| DELETE | `/emat/api/users/{id}` | Delete user |
| PATCH | `/emat/api/users/{id}/toggle-status` | Toggle status |

---

## 🔐 Available Roles

```
DIA, BSE, GT_FIELD_TEAM, GT_PMU, 
SIDBI_SDE, SIDBI_RO, SIDBI_HO_MAKER, 
SIDBI_HO_CHECKER, CLUSTER_EXPERT
```

---

## 💾 Database Connection

```
Host: 51.210.96.119
Port: 1521
Database: XEPDB1
User: SYSTEM
Password: Saichaitu98@
```

---

## 📁 Important Files

| File | Location | Purpose |
|------|----------|---------|
| Main App | `src/main/java/org/emat/EmatApplication.java` | Entry point |
| Config | `src/main/resources/application.properties` | Configuration |
| Schema | `db/oracle-schema.sql` | Database setup |
| User Entity | `src/main/java/org/emat/entity/User.java` | Data model |
| User API | `src/main/java/org/emat/controller/UserController.java` | REST controller |

---

## 🛠️ Maven Phases

```bash
mvn clean           # Delete target directory
mvn compile         # Compile source code
mvn test            # Run unit tests
mvn package         # Create JAR
mvn install         # Install to local repository
mvn deploy          # Deploy to remote repository
mvn spring-boot:run # Run Spring Boot app
```

---

## 🔧 Common Issues & Quick Fixes

| Issue | Fix |
|-------|-----|
| Port 8080 in use | Change `server.port` in application.properties |
| Can't connect to Oracle | Verify database is running, check credentials |
| Compilation error | Run `mvn clean install` |
| Tables don't exist | Execute `db/oracle-schema.sql` |
| Lombok not working | Enable annotation processing in IDE |

---

## 📊 Sample cURL Commands

### Health Check
```bash
curl http://localhost:8080/emat/health
```

### Create User
```bash
curl -X POST http://localhost:8080/emat/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"pass123","email":"test@test.com","firstName":"Test","role":"DIA"}'
```

### Get All Users
```bash
curl http://localhost:8080/emat/api/users
```

### Get User by ID
```bash
curl http://localhost:8080/emat/api/users/1
```

### Update User
```bash
curl -X PUT http://localhost:8080/emat/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"newpass","email":"test@test.com","firstName":"Test","role":"BSE"}'
```

### Delete User
```bash
curl -X DELETE http://localhost:8080/emat/api/users/1
```

---

## 🧪 Testing Checklist

- [ ] Application starts without errors
- [ ] Health endpoint returns UP status
- [ ] Can create a new user
- [ ] Can retrieve all users
- [ ] Can get user by ID
- [ ] Can get user by username
- [ ] Can update user information
- [ ] Can toggle user status
- [ ] Can delete user
- [ ] Sample data loaded on startup

---

## 🔑 Default Sample Users

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | DIA |
| bse_user | bse123 | BSE |
| field_team | field123 | GT_FIELD_TEAM |
| pmu_user | pmu123 | GT_PMU |
| sidbi_sde | sidbi123 | SIDBI_SDE |

---

## 📚 Documentation Files

```
README.md                   - Project overview
QUICKSTART.md               - Quick start guide
PROJECT_SETUP.md            - Complete setup guide
ORACLE_DATABASE_GUIDE.md    - Database configuration
API_DOCUMENTATION.md        - API endpoints documentation
IMPLEMENTATION_SUMMARY.md   - Implementation summary
QUICK_REFERENCE.md          - This file
```

---

## 🏗️ Project Structure

```
emat/
├── src/main/java/org/emat/
│   ├── EmatApplication.java      # Entry point
│   ├── controller/               # REST APIs
│   ├── service/                  # Business logic
│   ├── repository/               # Data access
│   ├── entity/                   # Models
│   ├── dto/                      # Data transfer objects
│   ├── enums/                    # Role enum
│   ├── config/                   # Configuration
│   ├── exception/                # Error handling
│   └── init/                     # Data initialization
├── src/main/resources/
│   └── application.properties    # Config file
├── db/
│   └── oracle-schema.sql         # Database schema
├── pom.xml                       # Maven config
└── target/
    └── emat.jar                  # Executable JAR
```

---

## 🔗 Useful Links

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Hibernate ORM](https://hibernate.org/)
- [Oracle Docs](https://docs.oracle.com/database/)
- [Maven Central](https://mvnrepository.com/)

---

## ✅ Current Status

- ✅ Spring Boot 4.0.1 configured
- ✅ Oracle database integrated
- ✅ 9 roles implemented
- ✅ User management API complete
- ✅ Exception handling configured
- ✅ Spring Security integrated
- ✅ Proper dependency management
- ✅ Comprehensive documentation
- ✅ Ready for development/testing

---

## 🚦 Application URL

```
http://localhost:8080/emat
```

---

## 📞 Support Resources

1. Check ORACLE_DATABASE_GUIDE.md for database issues
2. Check API_DOCUMENTATION.md for API issues
3. Check PROJECT_SETUP.md for setup issues
4. Review logs in console/terminal

---

## 🔒 Security Notes

- Passwords are BCrypt encoded
- Use HTTPS in production
- Don't commit credentials to Git
- Use environment variables for secrets
- Enable CSRF protection for web UI

---

## 📈 Next Steps

1. Run the application
2. Test all endpoints
3. Verify database connectivity
4. Load sample data
5. Implement additional features
6. Add authentication (JWT)
7. Setup CI/CD pipeline
8. Deploy to production

---

**Quick Reference Version:** 1.0  
**Last Updated:** July 23, 2026  
**Status:** Ready to Use ✅

