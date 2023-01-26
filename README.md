# task-products-cart


### Technologies : #

- Java 11
- Docker (last stable version)
- Spring Boot 2.7.8
- Maven 3.8.6
- 
### Installation : #

- Run PostgreSQL database with docker by using the following command:

```
- docker run -d --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=YOURPASSWORD postgres:15.1
```
#### Creating Postgres roles :

- Run SQL command:

```
CREATE USER dev WITH PASSWORD 'dev';
CREATE USER test WITH PASSWORD 'test';
```

#### Creating database
- Run SQL command:

```
CREATE DATABASE task_products_cart_dev;
CREATE DATABASE task_products_cart_test;
```
### Run : #

There are two profiles for app development and testing:
- dev
- test

To run application with dev profile use VM option:
```
-Dspring.profiles.active=dev
```
