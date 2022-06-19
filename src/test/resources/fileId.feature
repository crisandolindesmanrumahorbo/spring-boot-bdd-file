Feature: the file can be retrieved

  @fileId
  Scenario: client makes call to GET /files/id
    When the client calls /files/id
    Then the client file id receives status code of 200
