package com.stockanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenerRuleDTO {
    private Long id;
    private String name;
    private String columnName;
    private String operator; // >, <, >=, <=, =, !=
    private String comparisonType; // VALUE or COLUMN
    private Double value; // Used when comparisonType = VALUE
    private String comparisonColumnName; // Used when comparisonType = COLUMN
    private Integer comparisonPeriodOffset; // For comparing across years
    private String logicalOperator; // AND or OR with next rule
    private Integer position; // Order in the screener
}
