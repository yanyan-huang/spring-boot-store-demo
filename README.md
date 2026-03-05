# Store Demo (Spring Boot)

This project is a simple Spring Boot demo application that demonstrates:

-   Spring Boot application startup
-   Dependency Injection (DI)
-   Interface-based design
-   Service layer architecture
-   A basic web controller
-   How Spring automatically wires components together

------------------------------------------------------------------------

# Project Structure (To be revised)

    src
    └── main
        └── java
            └── com.springdemo.store
                ├── StoreApplication.java
                ├── HomeController.java
                ├── OrderService.java
                ├── PaymentService.java
                ├── PayPalPaymentService.java
                └── StripePaymentService.java

------------------------------------------------------------------------

# Application Components

## StoreApplication (Application Entry Point)

`StoreApplication` is the main class that starts the Spring Boot
application.

``` java
@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {

        ApplicationContext context =
                SpringApplication.run(StoreApplication.class, args);

        var orderService = context.getBean(OrderService.class);
        orderService.placeOrder();
    }
}
```

### Responsibilities

-   Bootstraps the Spring Boot application
-   Starts the Spring container (`ApplicationContext`)
-   Demonstrates retrieving a bean (`OrderService`)
-   Calls `placeOrder()` when the application starts

------------------------------------------------------------------------

# OrderService (Business Logic Layer)

`OrderService` represents the service layer of the application.

``` java
@Service
public class OrderService {

    private PaymentService paymentService;

    @Autowired
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder() {
        paymentService.processPayment(10);
    }
}
```

### Responsibilities

-   Handles the business logic of placing an order
-   Depends on the `PaymentService` interface
-   Calls a payment provider to process payments

Spring automatically injects a payment service implementation.

------------------------------------------------------------------------

# PaymentService (Interface)

`PaymentService` defines the contract for processing payments.

``` java
public interface PaymentService {
    void processPayment(double amount);
}
```

### Why use an interface?

Interfaces allow the system to support multiple payment providers
without changing business logic.

------------------------------------------------------------------------

# PayPalPaymentService (Implementation)

``` java
@Service
public class PayPalPaymentService implements PaymentService {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing payment with PayPal: " + amount);
    }
}
```

### Responsibilities

-   Implements `PaymentService`
-   Processes payments using PayPal
-   Annotated with `@Service` so Spring automatically creates a bean

------------------------------------------------------------------------

# StripePaymentService (Alternative Implementation)

``` java
public class StripePaymentService implements PaymentService {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing payment with Stripe: " + amount);
    }
}
```

### Note

This class is not registered as a Spring bean, so Spring does not use it
automatically.

It demonstrates how the application could support multiple payment
providers.

------------------------------------------------------------------------

# HomeController (Web Layer)

`HomeController` handles HTTP requests.

``` java
@Controller
public class HomeController {

    @Value("${spring.application.name}")
    private String appName;

    @RequestMapping("/")
    public String index() {
        System.out.println("appName: " + appName);
        return "index.html";
    }
}
```

### Responsibilities

-   Handles requests to `/`
-   Prints the application name
-   Returns the homepage

This method runs only when the endpoint is accessed.

------------------------------------------------------------------------

# Dependency Injection Flow

``` mermaid
flowchart TD

A[StoreApplication.main()]
B[SpringApplication.run()]
C[Spring Container Starts]
D[Component Scan]
E[Create Beans]

F[OrderService Bean]
G[PayPalPaymentService Bean]

H[PaymentService Interface]

A --> B
B --> C
C --> D
D --> E

E --> F
E --> G

G -. implements .-> H
F -->|requires PaymentService| H
G -->|Spring injects dependency| F
```

### Explanation

1.  The application starts with `SpringApplication.run()`.
2.  Spring scans the package `com.springdemo.store`.
3.  Classes annotated with `@Service` and `@Controller` are detected.
4.  Spring creates beans for those classes.
5.  `OrderService` requires a `PaymentService`.
6.  Spring finds `PayPalPaymentService`, which implements
    `PaymentService`.
7.  Spring injects `PayPalPaymentService` into `OrderService`.

This process is called Dependency Injection.

------------------------------------------------------------------------

# Application Startup Flow

``` mermaid
flowchart LR

A[Application Start]
B[Spring Boot Starts]
C[Spring Creates Beans]
D[OrderService Retrieved]
E[placeOrder() Called]
F[Payment Processed]

A --> B --> C --> D --> E --> F
```

Console output example:

    Processing payment with PayPal: 10

------------------------------------------------------------------------

# Web Request Flow

``` mermaid
sequenceDiagram

participant Browser
participant SpringBoot
participant HomeController

Browser->>SpringBoot: HTTP GET /
SpringBoot->>HomeController: index()
HomeController->>Console: print appName
HomeController->>Browser: return index.html
```

------------------------------------------------------------------------

# Architecture Overview

    Browser
       ↓
    Controller
       ↓
    Service
       ↓
    Interface
       ↓
    Implementation

Example in this project:

    Browser
       ↓
    HomeController
       ↓
    OrderService
       ↓
    PaymentService
       ↓
    PayPalPaymentService

------------------------------------------------------------------------

# Running the Application

Start the application using Maven:

    ./mvnw spring-boot:run

or

    mvn spring-boot:run

Then open:

    http://localhost:8080

------------------------------------------------------------------------

# Example Console Output

Application startup:

    Processing payment with PayPal: 10

When visiting `/`:

    appName: store

------------------------------------------------------------------------

# Key Spring Concepts Demonstrated

Concept                Explanation
  ---------------------- -----------------------------------------
Spring Boot            Bootstraps the application
Dependency Injection   Spring automatically wires dependencies
Service Layer          Contains business logic
Interfaces             Enables flexible architecture
Controllers            Handle HTTP requests
ApplicationContext     Spring container managing beans

------------------------------------------------------------------------

# Possible Improvements

Future improvements could include:

-   Adding additional payment providers
-   Using `@Primary` or `@Qualifier`
-   Creating REST APIs
-   Adding persistence (database)
-   Using proper logging instead of `System.out.println`
