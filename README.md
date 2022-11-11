# Unlimint
We are fast growing and reliable payments provider with more than 12 years’ experience on the FinTech and digital payments markets


Test task
Requirements for tools:
 Rest Assured
 Selenium/Selenide
 Java 8+
 Junit/TestNG
 Cucumber
 Maven(preferred)/Gradle
Objective: create a working* project for test case automation using the required tools
* the project must be assembled without errors, the tests must run completely Requirements for the project:
 tests must be informative for the test taker (logs are available). For example: slf4j/log4j
 the tests must pass without errors
 the tests must take into account the error when the username is already
occupied during registration
 the project must meet all the requirements of clean code: patterns, naming
convention, code reuse, and look like a finished product (exclude any drafts)
There are two tasks to choose from, you can do any of them
Test case 1:
1. (Rest Assured)Use the user generator (https://randomuser.me/) to generate 2 users. The first one is the sender and the second one is the recipient.
2. (Selenium/Selenide) Open the site of the test bank (https://parabank.parasoft.com/parabank/index.htm)
3. (Selenium/Selenide) Register using the data of the First user (sender)
a. If username is already occupied, you have to change it (for example,
add digits)
4. (Selenium/Selenide) After successful registration, go to the Bill Pay page
and transfer a random amount to the Second user (recipient)
5. (Selenium/Selenide) Verify that the payment was successful and to the
correct user (recipient)
Test case 2:
  
1. (Rest Assured)Use the user generator (https://randomuser.me/) to generate a user.
2. (Selenium/Selenide) Open the site of the test store (https://magento.softwaretestingboard.com/)
3. (Selenium/Selenide) Register using the data of the user
4. (Selenium/Selenide) After successful registration, go and buy any item
5. (Selenium/Selenide) Verify that the purchase was successful
After completing the task, send a link to the git repository with the completed task
  
