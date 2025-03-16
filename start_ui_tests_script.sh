#!/bin/sh
mvn install -DskipTests
mvn test -pl transportsystem.autotesting.ui -Dbrowser=chrome