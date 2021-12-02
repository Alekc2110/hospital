Feature: test GET, POST, PUT methods in Doctor REST API service

  Scenario: Get doctors list in json format
    Given I set GET doctor service api endpoint
    When  I send GET HTTP request to get list of doctors
    Then I receive valid HTTP response 200
    And I receive JSON as list of doctors


  Scenario: Add new doctor record
    Given I set Post doctor service api endpoint
    When  I send POST HTTP request to add doctor record
    Then I receive valid HTTP response 201 added new doctor
    And I receive ID of the saved doctor


  Scenario: Update doctor record
    Given I set PUT doctor service api endpoint for id 5
    When  I send PUT HTTP request to update doctor
    Then If doctor Updated I receive valid HTTP response 200
    And I receive JSON of the saved doctor