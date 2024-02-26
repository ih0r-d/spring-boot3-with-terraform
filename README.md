# Rest API using Spring Boot 3

![](https://img.shields.io/badge/Language-Java-informational?style=flat&logo=java&logoColor=white&color=2bbc8a)
![](https://img.shields.io/badge/Tools-Terraform-informational?style=flat&logo=java&logoColor=white&color=2bbc8a)
![](https://img.shields.io/badge/Tools-Maven-informational?style=flat&logo=apache-maven&logoColor=white&color=2bbc8a)

This repository contains a Spring Boot v3.2.1 application (Java 21) built with Maven, located in the `app` folder, and Terraform infrastructure code in the `terraform` folder.

## Table of Contents

- [Application](#application)
    - [Prerequisites](#prerequisites)
    - [Getting Started](#getting-started)
    - [Usage](#usage)
- [Terraform Infrastructure](#terraform-infrastructure)
    - [Prerequisites](#prerequisites-terraform)
    - [Getting Started](#getting-started-terraform)
    - [Usage](#usage-terraform)


## Application

### Prerequisites

Before running the Spring Boot application, ensure you have the following installed:

- Java 21
- Maven

### Getting Started

1. Navigate to the `app` folder:

    ```bash
    cd app
    ```

2. Build and run the application:

    ```bash
    mvn clean install
    java -jar target/your-application.jar
    ```

### Usage

Describe how to use the Spring Boot application and any specific details about its functionality.

### Additional Notes

Include any additional information about the Spring Boot application, configuration options, or troubleshooting tips.

## Terraform Infrastructure

### Prerequisites

Before deploying the infrastructure with Terraform, ensure you have the following installed:

- Terraform

### Getting Started

1. Navigate to the `terraform` folder:

    ```bash
    cd terraform
    ```

2. Initialize Terraform:

    ```bash
    terraform init
    ```

3. Review and customize the Terraform variables in `variables.tf`.

### Usage

Deploy the infrastructure:

```bash
terraform apply
