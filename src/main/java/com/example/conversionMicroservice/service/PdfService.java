package com.example.conversionMicroservice.service;

import com.example.conversionMicroservice.exception.MinioStorageException;
import com.example.conversionMicroservice.exception.PdfConversionException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class PdfService {

    private final BucketService bucketService;

    public String createPdf(MultipartFile file, String bucket) {
        validateFileType(file);

        try {
            String content = new String(file.getBytes());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph(content));
            document.close();

            byte[] pdfBytes = outputStream.toByteArray();
            String fileName = file.getOriginalFilename().replaceAll
                    ("\\s+", "_").replaceAll("[^a-zA-Z0-9_.-]", "");
            bucketService.saveFileToBucket(bucket, pdfBytes, fileName);

            return "Bucket: " + bucket + ", Path: " + fileName;
        } catch (IOException e) {
            throw new PdfConversionException("Error during PDF conversion: " + e.getMessage());
        } catch (MinioStorageException e) {
            throw new PdfConversionException(e.getMessage());
        }
    }

    private void validateFileType(MultipartFile file) {
        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();
        if (contentType == null || !contentType.equals("text/plain")) {
            throw new PdfConversionException("Unsupported file type: " + contentType);
        }
        if (originalFilename == null || !originalFilename.endsWith(".txt")) {
            throw new PdfConversionException("Unsupported file extension: " + originalFilename);
        }
    }
}
