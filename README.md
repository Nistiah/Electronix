# Electronix Web Application

Electronix is a web application built using the Spring Boot framework, Thymeleaf templating engine, and PostgreSQL database. It is designed to provide a seamless shopping experience for electronic products. The application includes features such as user authentication, wishlist management, a shopping cart, product listings, and an admin panel for managing the system.

## Screenshots

Here are some screenshots showcasing different pages and functionalities of the Electronix web application:

### Login Page
![image](https://github.com/Nistiah/Electronix/assets/56507634/aa799755-9b4b-4f4c-a85e-dd9fa8658c58)

### Wishlist
![image](https://github.com/Nistiah/Electronix/assets/56507634/23325621-c439-4a5b-addb-4bd16beaa894)

### Shopping Cart
![image](https://github.com/Nistiah/Electronix/assets/56507634/dfdb8ebe-c3d2-4e36-9d13-c20fd88fbc29)

### Buttons (play with emoji)
![image](https://github.com/Nistiah/Electronix/assets/56507634/03ab68e0-063a-4d9f-8ec0-d4cb8f07b93e)
![image](https://github.com/Nistiah/Electronix/assets/56507634/e35414a5-e3f6-45ce-925a-513e42812f18)
![image](https://github.com/Nistiah/Electronix/assets/56507634/6128a41f-83c1-465c-9c7f-67123b057e92)
![image](https://github.com/Nistiah/Electronix/assets/56507634/86440e60-d1ab-4df2-97d4-1db1eba17ac6)

### Admin Panel under http://localhost:8080/api/admin/adminPage
In page editable fields, all the values comes from database
![image](https://github.com/Nistiah/Electronix/assets/56507634/f41b4d39-dcf1-470c-892c-43064320efd5)
![image](https://github.com/Nistiah/Electronix/assets/56507634/5bdef71e-ddb3-493f-9732-ae814455bac9)

#InfoPages
![image](https://github.com/Nistiah/Electronix/assets/56507634/716e9a09-afe9-475e-bdd8-fb2cf0af5210)

## Technologies Used

The Electronix web application utilizes the following technologies:

- Spring Boot: A Java-based framework that simplifies the development of web applications.
- Thymeleaf: A server-side Java template engine for building dynamic web pages.
- PostgreSQL: A powerful open-source relational database management system.

## Getting Started

To set up the Electronix web application locally, follow these steps:

1. Clone the Electronix repository from GitHub:

   ```
   git clone https://github.com/Nistiah/Electronix.git
   ```

2. Install the required dependencies by navigating to the project directory and running:

   ```
   mvn install
   ```

3. Set up the PostgreSQL database and configure the database connection in the application.properties file.

4. Build and run the application:

   ```
   mvn spring-boot:run
   ```

5. Access the web application by navigating to `http://localhost:8080` in your web browser.

6. Currently the shop database is dynamically populated when the application start, use those credentials to login into
   an Admin authorized as user: username:"jajco", password:"jajco". Alternatively, you can create a new account. However, please note that new accounts do not have access to the adminPanel.
