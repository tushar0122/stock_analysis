package com.stockanalysis.repository;

import com.stockanalysis.entity.ColumnMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ColumnMetadataRepository extends JpaRepository<ColumnMetadata, Long> {
    Optional<ColumnMetadata> findByColumnName(String columnName);
    List<ColumnMetadata> findByIsNumericTrue();
    List<ColumnMetadata> findByCategory(String category);
    List<ColumnMetadata> findByTableName(String tableName);
}
