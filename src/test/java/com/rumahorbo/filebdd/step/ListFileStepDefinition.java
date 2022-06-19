package com.rumahorbo.filebdd.step;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rumahorbo.filebdd.SpringIntegrationTest;
import com.rumahorbo.filebdd.factory.FileFactory;
import com.rumahorbo.filebdd.model.FileDTO;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ListFileStepDefinition extends SpringIntegrationTest {

    private final int EXPECTED_SIZE = 2;
    @Autowired
    private ObjectMapper objectMapper;

    @Before("@files")
    public void beforeScenario() {
        deleteAllFiles();
        saveFiles(FileFactory.constructFilesWithSize(EXPECTED_SIZE));
    }

    @When("^the client calls /files$")
    public void the_client_issues_GET_files() throws Throwable {
        getAllFiles();
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        resultActions.andExpect(status().is(statusCode));
    }

    @And("the client receives all files")
    public void the_client_receives_all_files_body() throws Throwable {
        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        List<FileDTO> fileDTOS = objectMapper.readValue(responseString, objectMapper.getTypeFactory().constructCollectionType(List.class, FileDTO.class));
        assertEquals(EXPECTED_SIZE, fileDTOS.size());
    }

}
