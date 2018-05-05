# Summary

[![CircleCI](https://circleci.com/gh/omerio/unit-testing.svg?style=svg)](https://circleci.com/gh/omerio/unit-testing) 
[![codecov](https://codecov.io/gh/omerio/unit-testing/branch/master/graph/badge.svg)](https://codecov.io/gh/omerio/unit-testing)
[![Sonar](https://sonarcloud.io/api/project_badges/measure?project=com.omerio%3Aunit-testing&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.omerio%3Aunit-testing)

An example that demonstrates mocking using [jMockit](http://jmockit.github.io/) and [Mockito](https://github.com/mockito/mockito) + [PowerMock](https://github.com/powermock/powermock/wiki/Mockito) for a standard Java Spring application with JPA persistence

# Getting Started
Requirements:

- Apache Maven 3.3
- Java 1.7

Clone and build the project

```bash
    git clone https://github.com/omerio/unit-testing
    cd unit-testing
    mvn install
```    

# Classes

![Alt text](https://github.com/omerio/unit-testing/blob/master/classes.png "Class Diagram")

# Test Coverage

For test coverage we are using the [Cobertura](https://github.com/cobertura/cobertura) code coverage maven plugin. The plugin is configured with the ignoreTrivial flag to ignore trivial one line methods such as getters and setters. 

To run the test coverage:

```bash
    mvn cobertura:cobertura
```
The coverage report will be available inside the target/site/cobertura/index.html directory


# Static Code Checks

To ensure the code follows the best practices and standards, we are using the [Checkstyle](https://github.com/checkstyle/checkstyle) maven plugin with the [Google Checkstyle](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml) settings. 

To run the checkstyle report:

```bash
    mvn checkstyle:checkstyle
```

To run FindBugs plugin:

```bash
    mvn findbugs:check
```

To run the PMD plugin:

```bash
    mvn pmd:check
```
