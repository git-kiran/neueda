**ATM Machine Demo**

Example project demonstrating the use of Java/Spring-boot to build a microservice to be used in an ATM Machine.

**Running locally:**

```
mvn clean install
java -jar target/neueda-0.0.1-SNAPSHOT.jar
```

**Running on Docker:**

```
docker build -t "spring-boot:neueda" .
docker run -p 8080:8080 spring-boot:neueda
```

Pass in any arguments or support libraries using -v flag


**Tech stack:**

- Spring Boot: For creating the RESTful Web Services
- MockMVC: For testing the Web Layer
- Mockito: For testing the Services Layer
- SpringBootTest: For creation of Integration Tests
- H2 as database
- Maven for managing the project's build
- Attached Dockerfile for building and managing the application distribution using containers


**Usage:**

**Account Balance Request:**

Endpoint Served (Post Request):

http://localhost:8080/request

Sample Request(Json Format):

```
{
    "accountNumber": "987654321",
    "pin": 4321
}
```

**Withdraw Request:**

Endpoint Served (Post Request):

http://localhost:8080/withdraw

Sample Request(Json Format):

```
{
    "accountNumber": "987654321",
    "pin": 4321,
    "withdrawAmount": 100
}
```
