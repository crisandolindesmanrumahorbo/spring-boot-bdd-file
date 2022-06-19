package com.rumahorbo.filebdd.service;

import com.rumahorbo.filebdd.constant.Response;
import com.rumahorbo.filebdd.exception.FileNotFoundException;
import com.rumahorbo.filebdd.model.File;
import com.rumahorbo.filebdd.model.FileDTO;
import com.rumahorbo.filebdd.model.ResponseDTO;
import com.rumahorbo.filebdd.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public ResponseEntity<ResponseDTO> store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        File fileDB = new File(fileName, file.getContentType(), file.getBytes());
        ResponseDTO responseDTO = ResponseDTO.builder()
                .statusCode(Response.SUCCESS_STORE_FILE.getCode())
                .message(Response.SUCCESS_STORE_FILE.getMessage())
                .jsonBody("File id " + fileDB.getId())
                .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<byte[]> getFile(String id) {
        File fileDB = fileRepository.findById(id).orElseThrow(FileNotFoundException::new);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    public ResponseEntity<List<FileDTO>> getAllFiles() {
        Stream<File> filesStream = fileRepository.findAll().stream();
        List<FileDTO> files = filesStream.map(dbFile -> {
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
}
