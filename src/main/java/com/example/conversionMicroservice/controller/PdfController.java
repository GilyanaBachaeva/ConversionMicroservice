package com.example.conversionMicroservice.controller;
import com.example.conversionMicroservice.service.PdfService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class PdfController {
    private final PdfService pdfService;

    @PostMapping("/convert")
    public String convertToPdf(@RequestParam("file") MultipartFile file) {
        return pdfService.createPdf(file);
    }
}
