package com.rumahorbo.filebdd.service;

import com.rumahorbo.filebdd.exception.FileNotFoundException;
import com.rumahorbo.filebdd.model.File;
import com.rumahorbo.filebdd.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public File store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        File fileDB = new File(fileName, file.getContentType(), file.getBytes());
        return fileRepository.save(fileDB);
    }

    public File getFile(String id) {
        return fileRepository.findById(id).orElseThrow(FileNotFoundException::new);
    }

    public Stream<File> getAllFiles() {
        return fileRepository.findAll().stream();
    }
}
