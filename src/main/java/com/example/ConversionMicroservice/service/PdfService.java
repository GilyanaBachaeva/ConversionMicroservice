package com.example.ConversionMicroservice.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class PdfService {

    private final MinioClient minioClient;

    public String createPdf(MultipartFile file) throws IOException {
        String content = new String(file.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        document.add(new Paragraph(content));
        document.close();

        byte[] pdfBytes = outputStream.toByteArray();
        String fileName = "converted.pdf";
        saveToMinIO(pdfBytes, fileName);

        return "Bucket: my-bucket, Path: " + fileName;
    }

    private void saveToMinIO(byte[] pdfBytes, String fileName) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("my-bucket")
                            .object(fileName)
                            .stream(new ByteArrayInputStream(pdfBytes), pdfBytes.length, -1)
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
