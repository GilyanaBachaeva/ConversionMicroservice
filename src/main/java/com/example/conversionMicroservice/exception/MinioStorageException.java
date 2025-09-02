package com.example.conversionMicroservice.exception;

public class MinioStorageException extends RuntimeException {
    public MinioStorageException(String message) {
        super(message);
    }
}
