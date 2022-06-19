package com.rumahorbo.filebdd.controller;

import com.rumahorbo.filebdd.constant.Response;
import com.rumahorbo.filebdd.model.File;
import com.rumahorbo.filebdd.model.FileDTO;
import com.rumahorbo.filebdd.model.ResponseDTO;
import com.rumahorbo.filebdd.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseDTO> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        File fileStored = fileService.store(file);
        ResponseDTO responseDTO = ResponseDTO.builder()
                .statusCode(Response.SUCCESS_STORE_FILE.getCode())
                .message(Response.SUCCESS_STORE_FILE.getMessage())
                .jsonBody("File id " + fileStored.getId())
                .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileDTO>> getListFiles() {
        List<FileDTO> files = fileService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();
            return new FileDTO(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        File fileDB = fileService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }
}
