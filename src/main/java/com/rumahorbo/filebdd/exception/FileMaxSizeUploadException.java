package com.rumahorbo.filebdd.exception;

import com.rumahorbo.filebdd.constant.Response;
import com.rumahorbo.filebdd.model.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FileMaxSizeUploadException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseDTO> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .statusCode(Response.FAILED_STORE_MAX_SIZE_FILE.getCode())
                .message(Response.FAILED_STORE_MAX_SIZE_FILE.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseDTO);
    }
}
