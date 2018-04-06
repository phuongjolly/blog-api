Feature: Able to show list comments from post id
  Background:
    Given comment data has been setup
  Scenario: Get list comments from post id successful
    When I send a request get list of comments with current post id
    Then The result must contain list of comments
  Scenario: Get list of comments from post id fail
    When I send a request get list of comments with wrong post id
    Then the result should be null