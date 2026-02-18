package com.stockanalysis.controller;

import com.stockanalysis.dto.UploadResponseDTO;
import com.stockanalysis.service.CsvUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class UploadController {
    private final CsvUploadService csvUploadService;

    @PostMapping("/financial-data")
    public UploadResponseDTO uploadFinancialData(@RequestParam("file") MultipartFile file) {
        return csvUploadService.uploadFinancialData(file);
    }
}
