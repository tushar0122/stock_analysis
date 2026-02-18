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
public class ScreenerFilterDTO {
    private String operator; // AND or OR

    @Builder.Default
    private List<Condition> conditions = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Condition {
        private String field; // e.g., "roe", "pe"
        private String comparison; // e.g., ">", "<", "=", ">=", "<="
        private String value; // The value to compare
        private String period; // "current", "previous", "trend" for multi-year comparisons
    }
}
