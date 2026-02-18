package com.stockanalysis.service.impl;

import com.stockanalysis.dto.ColumnMetadataDTO;
import com.stockanalysis.entity.ColumnMetadata;
import com.stockanalysis.repository.ColumnMetadataRepository;
import com.stockanalysis.service.ColumnMetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColumnMetadataServiceImpl implements ColumnMetadataService {
    private final ColumnMetadataRepository columnMetadataRepository;

    @Override
    public List<ColumnMetadataDTO> getAllNumericColumns() {
        return columnMetadataRepository.findByIsNumericTrue()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<ColumnMetadataDTO> getColumnsByCategory(String category) {
        return columnMetadataRepository.findByCategory(category)
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<ColumnMetadataDTO> getColumnsByTableName(String tableName) {
        return columnMetadataRepository.findByTableName(tableName)
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<ColumnMetadataDTO> getColumnMetadata(String columnName) {
        return columnMetadataRepository.findByColumnName(columnName)
            .map(this::toDTO);
    }

    @Override
    public boolean isNumericColumn(String columnName) {
        return columnMetadataRepository.findByColumnName(columnName)
            .map(ColumnMetadata::getIsNumeric)
            .orElse(false);
    }

    @Override
    public boolean columnExists(String columnName) {
        return columnMetadataRepository.findByColumnName(columnName).isPresent();
    }

    private ColumnMetadataDTO toDTO(ColumnMetadata metadata) {
        return ColumnMetadataDTO.builder()
            .id(metadata.getId())
            .columnName(metadata.getColumnName())
            .tableName(metadata.getTableName())
            .isNumeric(metadata.getIsNumeric())
            .displayName(metadata.getDisplayName())
            .description(metadata.getDescription())
            .category(metadata.getCategory())
            .minValue(metadata.getMinValue())
            .maxValue(metadata.getMaxValue())
            .dataType(metadata.getDataType())
            .build();
    }
}
