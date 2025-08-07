package com.example.ConversionMicroservice.controller;
import com.example.ConversionMicroservice.service.PdfService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PdfController {
    private final PdfService pdfService;

    @PostMapping("/convert")
    public String convertToPdf(@RequestParam("file") MultipartFile file) {
        try {
            return pdfService.createPdf(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error during conversion";
        }
    }
}
