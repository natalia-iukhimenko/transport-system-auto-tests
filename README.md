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
1. Install [Docker](https://docs.docker.com/install/)
2. Install [Docker Compose](https://docs.docker.com/compose/install/)
3. Execute the following command:
```
docker-compose up api-tests allure-docker-service allure-ui -d
```

As a result, three docker containers are started:
* **api-tests** - for executing api tests
* **allure-docker-service** - for generating test execution report
* **test-execution-report** - for showing text execution report in web browser
![api_containers](https://github.com/user-attachments/assets/43fb1722-8c88-407a-9a1c-e01c9d1239da)

4. Once api-tests are finished and container is stopped, a test execution report can be generated. To generate the report, execute the following request:
```
curl -X 'GET' \
'http://localhost:5050/allure-docker-service/generate-report?project_id=api-tests' \
-H 'accept: */*'
```

5. Open the generated report by following this link: http://localhost:5252 and select "api-tests" from the left-side menu
![api-menu](https://github.com/user-attachments/assets/f228d7a9-f40b-4e17-ad82-ffb5c06b66bf)

## Run UI tests
### In a docker container
1. Install [Docker](https://docs.docker.com/install/)
2. Install [Docker Compose](https://docs.docker.com/compose/install/)
3.  Set **environment=remote** in transportsystem.autotesting.core/src/main/resources/config.properties
4. Execute the following command:
```
docker-compose up ui-tests selenoid selenoid-ui allure-docker-service allure-ui -d
```
As a result, the following docker containers are started:
![containers_ui](https://github.com/user-attachments/assets/4f9855f3-88b8-4e7f-ae93-e173ab2d5ad0)

5. Once the test execution is finished and **ui-tests** container is stopped, a test execution report can be generated. To generate the report, execute the following request:
```
curl -X 'GET' \
'http://localhost:5050/allure-docker-service/generate-report?project_id=ui-tests' \
-H 'accept: */*'
```

6. Open the generated report by following this link: http://localhost:5252 and select "ui-tests" from the left-side menu
![ui_tests_menu](https://github.com/user-attachments/assets/81f67a07-1cf6-4532-be81-1b82ab8ea59d)

### Locally
1. Set **environment=local** in transportsystem.autotesting.core/src/main/resources/config.properties
2. Set **junit.jupiter.execution.parallel.enabled=false** in transportsystem.autotesting.ui/src/test/resources/junit-platform.properties
3. Execute the following command to build project modules:
```
mvn install -DskipTests
```
4. Execute the following command to run tests:
```
mvn test -pl transportsystem.autotesting.ui
```
