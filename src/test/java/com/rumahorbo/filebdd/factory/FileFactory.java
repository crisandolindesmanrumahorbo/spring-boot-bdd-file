package com.rumahorbo.filebdd.factory;


import com.rumahorbo.filebdd.model.File;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileFactory {
    public static List<File> constructFilesWithSize(int size) {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            File file = File.builder()
                    .data(new byte[0])
                    .name("cris" + i + ".jpg")
                    .type("image/jpeg")
                    .build();
            files.add(file);
        }
        return files;
    }

    public static MockMultipartFile constructMultiPart(String name) {
        Path path = Paths.get("/Users/20058748/Documents/Project/Spring boot Environtment/file-bdd/src/test/resources/upload.txt");
        String originalFileName = "upload.txt";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = java.nio.file.Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MockMultipartFile(name,
                originalFileName, contentType, content);
    }
}
