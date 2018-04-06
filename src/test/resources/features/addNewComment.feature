Feature: Current user able to add new comment
  Background:
    Given data has been setup
    Scenario: Add new comment successful
      When user send a request add new comment with correct information
      Then the result should contain new comment in post
    Scenario: Add new comment fail
      When user send a request add new comment with empty information
      Then the request should be null