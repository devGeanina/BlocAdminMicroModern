## BlocAdmin
Micro-service and event driven web application, with a modern architecture, that allows the administrator of any building complex to manage the information regarding the buildings, residents, resident requests, budgets and expenses, as well as keeping all the information and documents in one place.

## Pre-requisites
* Cassandra
* Kafka and Zookeeper or Docker (depending on how you'd like to run the application, configuration is done for both variants)
For this application Docker for Windows was used, which also requires Python and Alpine to be installed previously on the machine.
* Create an account in Okta developer and update the information with your own information into the Config server under web-service.properties and under config-data web-service.properties.

## Overview
The application is split into six micro-services, as follows:
* discovery - this handles and starts the Eureka server, that registers every micro-service. Eureka server knows all the client applications running on each port and IP address, while keeping this information contained into itself, without the web-service needing the actual information. The service discovery helps the micro-services communicate among each other.
* config - this provides the configuration on the project while removing any trace of configuration values in the project’s source code.
* budget-service - handling the budgeting for the administration. This was split into a separate micro-service because it has may possibilities to be extended to contain larger features. This service will also be the Kafka Consumer for when a new Expense is registered and the budget needs to substract that expense from its overall availability.
* operation-service - handling the other operations for the administration such as the Households, Requests and Expenses. These were paired together as they share information and are not overall big operations. The expense-service also behaves as the Kafka Producer, sending messages to the budget-service when a new Expense is registered.
* user-service - handles the users that the administration needs to keep a track of such as the owners, employees, associates and so on.
* web-service - provides Okta authentication and Spring Security and the UI implementation of the code, keeping the BE completely separate. Each request allows only users with scope profile to access them (every authenticated user will have it) and all controls also requests for the users authentication to ensure the app's security.
The application has the configuration in place to run both using Docker (see screenshot of it in a running state), as well as using Cassandra, Zookeper, Python and Kafka installed separately on your local machine.

## Built With
* Spring Boot - an open source Java-based framework used to create micro services for web application.
* Cassandra - distributed, wide-column store, NoSQL database management system designed to handle large amounts of data.
* Kafka - distributed event streaming platform and distributed system consisting of servers and clients that communicate via a high-performance TCP network protocol. The Kafka profile provided in the pom.xmls of the projects can be easily replaced to use RabbitMQ as well, instead of Kafka.
* ZooKeeper - open-source server for highly reliable distributed coordination of cloud applications.
* Docker - can easily pack, ship, and run any application as a lightweight, portable, self-sufficient container, which can run virtually anywhere (here used on Windows with the help of Alpine - minimal Docker image based on Alpine Linux). Containers are isolated from one another and bundle their own software, libraries and configuration files; they can communicate with each other through well-defined channels.
* Spring Cloud Stream - a framework for building highly scalable event-driven micro-services connected with shared messaging systems.
* Bootstrap - free and open-source CSS framework directed at responsive, mobile-first front-end web development.
* Thymeleaf - Java XML/XHTML/HTML5 template engine workable with web and non-web applications.
* Log4j - logging application information.
* Librepdf - free Java library for creating and editing PDF files, used to export PDF files.
* FontAwesome - font and icon toolkit based on CSS.
* Jackson Databind - used to convert JSON to and from Java Objects using property accessor or using annotations.

## Author
* **Geanina Chiricuta**

## Demo - for a more descriptive demo of the actual application (FE) see also my repository for BlocAdminMicro

https://user-images.githubusercontent.com/35954631/121706844-abdefc00-cade-11eb-8167-37d958447724.mp4

![docker](https://user-images.githubusercontent.com/35954631/121706636-7cc88a80-cade-11eb-88ec-cc04e4b41c11.JPG)
![eureka](https://user-images.githubusercontent.com/35954631/121706641-7d612100-cade-11eb-944e-e298d9a0b1d0.JPG)



