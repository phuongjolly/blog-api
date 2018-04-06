Feature: As a new user, I should be able to register for an account
  Scenario: Register successful
    When User put correct information
    Then result should contain new user information
    