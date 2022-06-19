## Spring boot Download Upload File
[source: bezkoder] 

#### Postgres run on docker
```
docker run -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=mysecretpassword -e POSTGRES_DB=file_db -p 5432:5432 library/postgres:13-alpine
```

#### Blob
Store the file into Postgres with Blob.
https://www.baeldung.com/hibernate-lob

#### Testing
BDD Source: 
https://www.baeldung.com/cucumber-spring-integration
https://krushna-dash.medium.com/spring-boot-cucumber-bdd-c077666c9c68

- must be on option create, so the H2 (database for the testing) create a fresh database every unit test
- if hibernate.ddl-auto change into option update, the H2 get the data from actual database, not create a db fresh one 
```
spring.jpa.hibernate.ddl-auto=create
```

- Implementation Scenario and Tagged Hooks [JVM Hook] 
```
    @Before("@files")
```

- we can either use Cucumber Expressions or regular expressions inside the annotations 
- right now we use regex
```
    Regular Express/Regex:
    @Then("^the client receives status code of (\\d+)$")
```

```
    Cucumber Express:
    @Then("the client receives status code of {int}")
```

- feature where the feature located
```
    @CucumberOptions(features = "src/test/resources/feature")
```

- Base Class or in this project (SpringIntegrationTest), we can use that class to implement the logic

- @CucumberContextConfiguration location
https://stackoverflow.com/questions/71790142/cucumberbackendexception-no-qualifying-bean-of-type

[source: bezkoder]: <https://www.bezkoder.com/spring-boot-upload-file-database/>
[JVM Hook]: <https://zsoltfabok.com/blog/2012/09/cucumber-jvm-hooks/>
