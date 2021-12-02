Feature: test GET, POST, PUT methods in Patient REST API service

  Scenario: Get patients list in json format
    Given I set GET patient service api endpoint
    When  I send GET HTTP request to get list of patients
    Then I receive valid HTTP response 200 returns patients
    And I receive JSON as list of patients


  Scenario: Add new patient record
    Given I set Post patient service api endpoint
    When  I send POST HTTP request to add patient record
    Then I receive valid HTTP response 201 added new patient
    And I receive ID of the saved patient


  Scenario: Update patient record
    Given I set PUT patient service api endpoint for id 1
    When  I send PUT HTTP request to update patient
    Then If patient Updated I receive valid HTTP response 200
    And I receive JSON of the saved patient