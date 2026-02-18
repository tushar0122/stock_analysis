package com.stockanalysis.service;

import com.stockanalysis.dto.ColumnMetadataDTO;
import java.util.List;
import java.util.Optional;

public interface ColumnMetadataService {
    List<ColumnMetadataDTO> getAllNumericColumns();
    List<ColumnMetadataDTO> getColumnsByCategory(String category);
    List<ColumnMetadataDTO> getColumnsByTableName(String tableName);
    Optional<ColumnMetadataDTO> getColumnMetadata(String columnName);
    boolean isNumericColumn(String columnName);
    boolean columnExists(String columnName);
}
