package com.stockanalysis.service;

import com.stockanalysis.dto.UploadResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface CsvUploadService {
    UploadResponseDTO uploadFinancialData(MultipartFile file);
}
