package com.stockanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadResponseDTO {
    private Integer totalRows;
    private Integer successCount;
    private Integer failedCount;

    @Builder.Default
    private List<UploadError> errors = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UploadError {
        private Integer rowNumber;
        private String error;
    }
}
