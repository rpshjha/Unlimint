Feature: UI Automated tests

  Background: Generate user
    Given I generate 2 users

  @001
  Scenario: Register a user and make a bill payment
    Given I am a registered user
    And recipient is a registered user
    Then I can transfer '10' amount to recipient