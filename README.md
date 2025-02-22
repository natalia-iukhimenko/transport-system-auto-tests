## Introduction
This repository represents a testing project for the transport system (https://transport-system.onrender.com).
The project consists of three modules:
1. **API** - contains methods for sending http requests and api-tests themselves.
2. **UI** - contains page classes and ui-tests.
3. **CORE** - contains configuration, model and utility classes that must be available in both API and UI modules.

## Tech stack
* **JUnit 5** - as a testing framework;
* **Unirest** - for sending http requests;
* **Selenide** - for UI web tests;
* **Allure** - for reporting;
* **AssertJ** - for assertions;
* **Slf4j** - for logging.

## Run API tests in docker container
Execute the following command:
```
docker-compose up
```
As a result, two docker containers are started:
* **api-tests** - for test execution
* **api-tests-reports** - for showing the report with test execution results
  
![containers](https://github.com/user-attachments/assets/5e3e07fa-15a2-4bd3-9067-d7051b0180b7)

Once test execution is finished and **api-tests** container is stopped, test execution report will be available by the following link: http://localhost:5050/allure-docker-service/projects/default/reports/latest/index.html?redirect=false
![Screenshot_2](https://github.com/user-attachments/assets/83380aa0-a4c7-4217-a765-dc089e56da77)

## Run UI tests
TBA
