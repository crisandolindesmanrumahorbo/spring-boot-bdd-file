package com.rumahorbo.filebdd.constant;

import lombok.Getter;

@Getter
public enum Response {
    SUCCESS_STORE_FILE("00", "Store Succeed!"),
    FAILED_STORE_FILE("50", "Store Failed!"),
    FAILED_STORE_MAX_SIZE_FILE("51", "File too large!");

    private final String code;
    private final String message;

    Response(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
