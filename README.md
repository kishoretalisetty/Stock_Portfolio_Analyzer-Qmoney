# QMoney

QMoney is a visual stock portfolio analyzer. It helps portfolio managers make trade recommendations for their clients.

## Overview

During the course of this project:

1. **Core Logic Implementation**: Implemented the core logic of the portfolio manager and published it as a library.
   
2. **Support for Multiple Stock Quote Services**: Refactored code to add support for multiple stock quote services, enhancing flexibility and scalability.
   
3. **Improved Stability and Performance**: Made enhancements to improve application stability and performance, ensuring a reliable user experience.
   
4. **Multithreading for Improved Responsiveness**: Introduced multithreading to improve application responsiveness, allowing for faster processing of portfolio trades and recommendations.
   
5. **Unit Testing for Performance Measurement**: Wrote comprehensive unit tests to measure performance improvements and ensure the reliability of the application.

## Technologies Used

- **Java**: Core programming language for the application logic.
- **Spring Framework**: Utilized for dependency injection and inversion of control.
- **Gradle**: Build automation tool used for project management.
- **JUnit 5**: Framework for unit testing.
- **Mockito**: Framework for mocking dependencies in unit tests.
- **RESTful APIs**: Used to interact with external services such as Tiingo and Alphavantage.
- **Jackson**: Library for JSON data binding.
- **Log4j**: Logging framework for logging application events.
- **Apache HttpClient**: Library for making HTTP requests to external APIs.
- **Multithreading**: Utilized to improve performance by processing multiple portfolio trades concurrently.
- **Factory Design Principle**: Implemented to create instances of `PortfolioManager` based on different stock quote service providers.

## Configuration

Configure the application via the `application.properties` file located in the `src/main/resources` directory. Options include logging levels and external API endpoints.

## Design Principles

- **Dependency Injection (DI)**: Utilized Spring's DI mechanism to manage dependencies and promote loose coupling.
- **Inversion of Control (IoC)**: Leveraged Spring's IoC container to handle object creation and lifecycle management.
- **Separation of Concerns (SoC)**: Modularized the application into distinct components for easier maintenance and scalability.
- **RESTful Design**: Designed the application to follow RESTful principles for communication with external APIs.
- **Single Responsibility Principle (SRP)**: Ensured that each class and method has a single responsibility to improve maintainability.
- **Factory Design Principle**: Implemented a factory (`PortfolioManagerFactory`) to create instances of `PortfolioManager` based on different stock quote service providers.

## External APIs

QMoney integrates with the following external APIs:

- **Tiingo**: Used for fetching stock data. Ensure you have a valid Tiingo API token configured in the code. [Tiingo API Documentation](https://api.tiingo.com/documentation/end-of-day)
- **Alphavantage**: An alternate provider. Configure the appropriate token in the code. [Alphavantage API Documentation](https://www.alphavantage.co/documentation/)

**For More Details** [Link](https://www.crio.do/learn/portfolio/kishorethalisetty/ME_QMONEY_V2/?edit=true)
