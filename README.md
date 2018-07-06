# DEMO PRODUCT API

This is a sample Product API created to illustrate usage of Spring Boot + Swagger + Hibernate + Maven

## Table of Contents

1. [Dependencies](#dependencies)
1. [Installation](#installation)
1. [Usage](#usage)
1. [API Information](#api-information)
1. [Additional notes](#additional-notes)

### Dependencies

- Install Maven
- Clone this repository

### Installation

 - In order to install this application, just run following commands in a terminal:
 	- `mvn clean`
 	- `mvn install`

## Usage

- In order to run this code just type following commands in a terminal:
	 `mvn spring-boot:run`

- Accessing DB 
	- All DB information is located inside resources folder: ``application.properties`` file. So, when running code, embedded Tomcat server is configured to run in port 8090; in memory DB console URL is `http://localhost:8080/h2 `; user name and password are configure inside ```application.properties``` file. They are defined by default as "admin".

### API Information

All API details can be visualized by Sweagger documentation. So, after running application [usage](#usage), either go to `http://localhost:8090/swagger-ui.html or http://localhost:8090/v2/api-docs

## Additional notes
This application populates a sample in memory database when Spring loads its context
          
