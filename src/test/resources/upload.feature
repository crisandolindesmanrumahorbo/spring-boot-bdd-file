Feature: the files can be uploaded

  @upload
  Scenario: client makes call to POST /upload
    When the client calls /upload
    Then the client uploader receives status code of 201
    And the client receives response
