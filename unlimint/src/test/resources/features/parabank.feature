Feature: UI Automated tests

  Background: Generate user
    Given I generate 2 users of 'AU' nationality

  @001
  Scenario: Register a user and make a bill payment
    When I navigate to 'https://parabank.parasoft.com/parabank/index.htm'
    And I register user as
      | SENDER    |
      | RECIPIENT |
    Then I login as a SENDER
    And I can transfer amount 10 to RECIPIENT