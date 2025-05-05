# Profile Data Management with AWS Lambda and RDS

This project is a Java-based AWS Lambda function for processing profile data and storing it in an Amazon RDS database. It uses Maven for dependency management and includes functionality for handling profile and linked system data.

## Features

- Processes profile data with attributes like email, phone, address, and vehicle ID.
- Validates input data before storing it in the database.
- Stores profile data in the `profiles` table and linked system data in the `linked_system` table in RDS.
- Uses the AWS Lambda runtime and MySQL database.

## Prerequisites

- **Java 11** or higher
- **Maven** for dependency management
- An **Amazon RDS** instance with a MySQL database
- AWS Lambda setup with the required environment variables:
  - `RDS_JDBC_URL`: JDBC URL for the RDS instance
  - `RDS_USERNAME`: Username for the RDS database
  - `RDS_PASSWORD`: Password for the RDS database

## Dependencies

The project uses the following dependencies:

- **AWS Lambda Java Core**: For AWS Lambda runtime support.
- **Apache HttpClient**: For HTTP operations (if needed in the future).
- **MySQL Connector**: For connecting to the MySQL database.

Dependencies are managed in the `pom.xml` file.

## Build and Deployment

1. **Build the project**:
   ```bash
   mvn clean package
