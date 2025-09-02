package com.example.conversionMicroservice.service;

import com.example.conversionMicroservice.exception.MinioStorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FileStorageService {

    private final MinioService minioService;

    public void saveFileToBucket(byte[] pdfBytes, String fileName, String defaultBucket) {
        try {
            minioService.save(pdfBytes, fileName, defaultBucket);
        } catch (MinioStorageException e) {
            throw new MinioStorageException("Error saving file: " + e.getMessage());
        }
    }
}
