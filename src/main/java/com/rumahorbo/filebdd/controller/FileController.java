package com.rumahorbo.filebdd.controller;

import com.rumahorbo.filebdd.model.FileDTO;
import com.rumahorbo.filebdd.model.ResponseDTO;
import com.rumahorbo.filebdd.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseDTO> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.store(file);
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileDTO>> getListFiles() {
        return fileService.getAllFiles();
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        return fileService.getFile(id);
    }
}
