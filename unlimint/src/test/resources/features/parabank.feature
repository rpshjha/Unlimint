Feature: UI Automated tests

  Background: Generate user
    Given I generate 2 users

  @001
  Scenario: Register a user and make a bill payment
    Given I register user as
      | SENDER    |
      | RECIPIENT |
    When I login as a SENDER
    Then I can transfer amount '10' to RECIPIENT