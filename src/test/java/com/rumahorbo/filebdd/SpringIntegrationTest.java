package com.rumahorbo.filebdd;

import com.rumahorbo.filebdd.model.File;
import com.rumahorbo.filebdd.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringIntegrationTest {

    protected static ResultActions resultActions = null;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private MockMvc client;

    protected void deleteAllFiles() {
        fileRepository.deleteAll();
    }

    protected List<File> saveFiles(List<File> files) {
        return fileRepository.saveAllAndFlush(files);
    }

    protected void getAllFiles() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/files");
        resultActions = this.client.perform(request);
    }

    protected void getFile(String id) throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/files/" + id);
        resultActions = this.client.perform(request);
    }

    protected void upload(MockMultipartFile multipartFile) throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .multipart("/upload")
                .file(multipartFile);
        resultActions = this.client.perform(request);
    }
}
