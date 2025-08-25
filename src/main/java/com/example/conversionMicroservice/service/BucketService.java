package com.example.conversionMicroservice.service;

import com.example.conversionMicroservice.exception.MinioStorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BucketService {

    private final MinioService minioService;

    public void saveFileToBucket(String bucket, byte[] pdfBytes, String fileName) {
        try {
            minioService.save(pdfBytes, fileName, bucket);
        } catch (MinioStorageException e) {
            throw new MinioStorageException("Error saving file to bucket " + bucket + ": " + e.getMessage());
        }
    }

    public String getBucket(String bucket) {
        return bucket;
    }
}
