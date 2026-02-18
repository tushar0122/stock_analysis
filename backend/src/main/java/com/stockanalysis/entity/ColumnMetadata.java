package com.stockanalysis.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "column_metadata")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String columnName;

    @Column(nullable = false)
    private String tableName;

    @Column(nullable = false)
    private Boolean isNumeric;

    @Column(nullable = false)
    private String displayName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = true)
    private String category; // VALUATION, PROFITABILITY, SOLVENCY, EFFICIENCY, GROWTH, OTHER

    @Column(nullable = true)
    private Double minValue;

    @Column(nullable = true)
    private Double maxValue;

    @Column(nullable = false)
    private String dataType; // DOUBLE, INTEGER, STRING, etc.

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
