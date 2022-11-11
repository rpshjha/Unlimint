## QA CODING CHALLENGE

Libraries used

* MAVEN
* JAVA
* SELENIUM
* REST ASSURED
* CUCUMBER
* JUNIT
* EXTENT REPORT

### Prerequisites

This project requires the following pieces of software to run.

* [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or above
* [Maven](https://maven.apache.org/install.html)
* Chrome

## HOW TO RUN THE TEST

Go to project folder and type:

    cd unlimint

In command window and run:

    mvn clean test

Above command will run all the UI and API tests

Run a specific tag:

    mvn clean test -Dcucumber.filter.tags="@001"


## Test Report

Extent report is generated at the below location

    ./test-output/extent_report/qa_coding_challenge_report.html

## Logs

Logs is generated at the below location

    ./test-output/logs/QA_CODING_CHALLENGE.log

##

### Entry point to the UI test is **RunCucumberTest.java**
