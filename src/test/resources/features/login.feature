Feature: User will be able to login
  Background:
    Given user data has been setup
  Scenario: Login successfully
    When I send login request with correct information
    Then the request should be successful
    And the result must contain user information
  Scenario: Login fail
    When I send login request with incorrect information
    Then the request should fail with Access Deny Exception