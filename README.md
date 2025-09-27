# safe-bank

## Spring Boot Application with Spring Security

This is a **Spring Boot** application secured using **Spring Security** with customized endpoint access.

Certain endpoints such as `/myAccount`, `/myBalance`, `/myLoans`, and `/myCards` require authentication. Other endpoints like `/notices`, `/contact`, and `/error` are publicly accessible without logging in.

To access the secured URLs, you need to log in using the credentials defined in the `application.properties` file:

- **Username:** `siraj`
- **Password:** `12345`

Once authenticated, you can access the application's protected endpoints.
