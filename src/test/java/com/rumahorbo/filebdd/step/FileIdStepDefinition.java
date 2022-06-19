package com.rumahorbo.filebdd.step;

import com.rumahorbo.filebdd.SpringIntegrationTest;
import com.rumahorbo.filebdd.factory.FileFactory;
import com.rumahorbo.filebdd.model.File;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@CucumberContextConfiguration
public class FileIdStepDefinition extends SpringIntegrationTest {

    private List<File> files;

    @Before("@fileId")
    public void beforeScenario() {
        deleteAllFiles();
        files = saveFiles(FileFactory.constructFilesWithSize(1));
    }

    @When("^the client calls /files/id$")
    public void the_client_issues_GET_files_id() throws Throwable {
        getFile(files.get(0).getId());
    }

    @Then("^the client file id receives status code of (\\d+)$")
    public void the_client_file_id_receives_status_code_of(int statusCode) throws Throwable {
        resultActions.andExpect(status().is(statusCode));
    }

}
