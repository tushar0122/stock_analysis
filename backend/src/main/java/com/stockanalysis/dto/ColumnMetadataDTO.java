package com.stockanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnMetadataDTO {
    private Long id;
    private String columnName;
    private String tableName;
    private Boolean isNumeric;
    private String displayName;
    private String description;
    private String category;
    private Double minValue;
    private Double maxValue;
    private String dataType;
}
