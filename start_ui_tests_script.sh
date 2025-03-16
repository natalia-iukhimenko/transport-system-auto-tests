#!/bin/sh
mvn install -DskipTests
mvn test -pl transportsystem.autotesting.ui -Dbrowsers=firefox:125.0