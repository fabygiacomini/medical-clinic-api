# Medical Clinic API Rest

## Technologies used
- Spring Boot 3
- Java 17
- Lombok
- MySQL/Flyway
- JPA/Hibernate
- Maven

## The Project
- [Kanban](https://trello.com/b/ZWK1Wiwl/api-vespa-med)

## Api Docs
- http://localhost:8080/v3/api-docs
- http://localhost:8080/swagger-ui/index.html

## Execution
After generate de `.jar` file (`mvn clean package`), we can execute the application informing the profile we wanted (defaul use application.properties), and we
can pass environment variables as well, like this:
```shell
java -Dspring.profiles.active=prod -DDATASOURCE_URL=jdbc:mysql://localhost:3306/clinic -DDATASOURCE_USERNAME=root -DDATASOURCE_PASSWORD=root -jar target/api-0.0.1-SNAPSHOT.jar
```