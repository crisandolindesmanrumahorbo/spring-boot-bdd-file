Feature: the files can be retrieved

  @files
  Scenario: client makes call to GET /files
    When the client calls /files
    Then the client receives status code of 200
    And the client receives all files
