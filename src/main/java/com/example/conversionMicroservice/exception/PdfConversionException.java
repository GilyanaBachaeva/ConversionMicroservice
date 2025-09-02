package com.example.conversionMicroservice.exception;

public class PdfConversionException extends RuntimeException {
    public PdfConversionException(String message) {
        super(message);
    }
}
