Feature: Able to show list of Post by tag name
  Background:
    Given data has been setup
    Scenario: Get list of posts by tag name successful
      When I send a request get list of posts by tag name
      Then the result must contain list of posts
    Scenario: Get list of posts by tag name failed
      When I send a request get list of posts by wrong tag name
      Then the posts should be null