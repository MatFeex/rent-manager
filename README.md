# RentManager

RentManager is a web application built using Spring framework that allows users to manage clients, vehicles, and reservations. It provides functionalities to add, update, and delete client details, vehicle information, and reservation records. Additionally, the project includes a Command Line Interface (CLI) to perform these operations via command line commands.


## Features

* **Web Application:** Users can access the web application interface to interact with the RentManager system through a user-friendly interface.
* **CLI Interface:** The CLI allows users to perform all the CRUD (Create, Read, Update, Delete) operations related to clients, vehicles, and reservations using command line commands.


## Technologies used

* Java
* Spring Framework
* Maven


## Prerequisites

To run RentManager, ensure that you have the following installed on your system:
* Java Development Kit (JDK)
* Apache Maven

## Getting started

Follow these steps to get started with RentManager:
1. Clone the repository to your local machine:
```
git clone https://github.com/MatFeex/rent-manager.git
```
2. Build the project using Maven:

```
cd rent-manager
mvn clean install
mvn compile
```

3. Run the application:

    * Web Application:

    ```
    mvn tomcat7:run
    ```
   The web application will be accessible at http://localhost:8080/rentmanager.

    * Command Line Interface (CLI):

   Run the CLI class --> com.epf.rentmanager.ui.cli.CLI  
   The CLI will be launched, and you can follow the on-screen instructions to perform various operations.


## Usage

* Web Application:

    * Access the RentManager web application through your preferred web browser.
    * Use the provided interface to add, update, and delete clients, vehicles, and reservations.

* Command Line Interface (CLI):
    * Launch the CLI as mentioned in the "Getting Started" section.
    * Follow the on-screen instructions to execute commands for managing clients, vehicles, and reservations.
