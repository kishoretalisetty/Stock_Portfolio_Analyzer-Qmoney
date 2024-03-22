# QMoney

QMoney is a Java application for managing stock portfolios, calculating annualized returns, and fetching stock data from external APIs.

## Overview

During the course of this project, the following key tasks were accomplished:

1. **Core Logic Implementation**: Implemented the core logic of the portfolio manager and published it as a library, enabling portfolio managers to manage their stock portfolios effectively.

2. **Support for Multiple Stock Quote Services**: Refactored the codebase to add support for multiple stock quote services, enhancing flexibility and allowing users to choose their preferred service provider.

3. **Improved Application Stability and Performance**: Made enhancements to the application to improve stability and performance, ensuring a smoother user experience and reliable operation under varying conditions.

4. **Multithreading for Improved Responsiveness**: Introduced multithreading to the application to improve responsiveness, enabling concurrent processing of multiple portfolio trades and optimizing resource utilization.

5. **Performance Testing with Unit Tests**: Developed comprehensive unit tests to measure performance improvements, ensuring that the application meets performance requirements and identifying areas for further optimization.

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

## Multithreading

QMoney utilizes multithreading to improve performance by processing multiple portfolio trades concurrently. This is achieved by utilizing Java's `ExecutorService` and `Callable` interfaces to perform parallel execution of tasks.


QMoney is a visual stock portfolio analyzer. It helps portfolio managers make trade recommendations for their clients.
During the course of this project,
1) Implemented the core logic of the portfolio manager and published it as a library.
2) Refactored code to add support for multiple stock quote services.
3) Improved application stability and performance.
4) Improved application responsiveness by introducing multithreading.
5) Wrote unit tests to measure performance improvements.

[Project Link](https://www.crio.do/learn/portfolio/kishorethalisetty/ME_QMONEY_V2/?edit=true)
