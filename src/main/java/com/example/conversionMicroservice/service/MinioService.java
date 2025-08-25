package com.example.conversionMicroservice.service;

import com.example.conversionMicroservice.exception.MinioStorageException;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@RequiredArgsConstructor
@Service
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String defaultBucket;

    public void save(byte[] pdfBytes, String fileName, String bucket) {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .stream(new ByteArrayInputStream(pdfBytes), pdfBytes.length, -1)
                            .build()
            );
        } catch (Exception e) {
            throw new MinioStorageException("Error saving file to MinIO: " + e.getMessage());
        }
    }

    public String getDefaultBucket() {
        return defaultBucket;
    }
}