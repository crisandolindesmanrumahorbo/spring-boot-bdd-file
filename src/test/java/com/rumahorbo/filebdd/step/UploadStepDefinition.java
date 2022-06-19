package com.rumahorbo.filebdd.step;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rumahorbo.filebdd.SpringIntegrationTest;
import com.rumahorbo.filebdd.constant.Response;
import com.rumahorbo.filebdd.factory.FileFactory;
import com.rumahorbo.filebdd.model.ResponseDTO;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UploadStepDefinition extends SpringIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Before("@upload")
    public void beforeScenario() {
        deleteAllFiles();
    }

    @When("^the client calls /upload$")
    public void the_client_issues_POST_upload() throws Throwable {
        upload(FileFactory.constructMultiPart("file"));
    }

    @Then("^the client uploader receives status code of (\\d+)$")
    public void the_client_uploader_receives_status_code_of(int statusCode) throws Throwable {
        resultActions.andExpect(status().is(statusCode));
    }

    @And("^the client receives response$")
    public void the_client_receives_response() throws UnsupportedEncodingException, JsonProcessingException {
        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        ResponseDTO responseDTO = objectMapper.readValue(responseString, ResponseDTO.class);
        assertEquals(Response.SUCCESS_STORE_FILE.getCode(), responseDTO.getStatusCode());
        assertEquals(Response.SUCCESS_STORE_FILE.getMessage(), responseDTO.getMessage());
    }

}
