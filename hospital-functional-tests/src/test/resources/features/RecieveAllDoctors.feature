Feature: test receiving all doctors list json

  Scenario: Get doctors list in json format
    Given I set get request for api endpoint "http://localhost:8080/hospital/doctors"
    When I send get http request
    Then I receive valid HTTP response 200
    And I receive "" list of doctors


