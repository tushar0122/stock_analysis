package com.stockanalysis.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "screener_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenerRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "saved_screener_id", nullable = true)
    private SavedScreener savedScreener;

    @Column(nullable = true)
    private String name;

    @Column(nullable = false)
    private String columnName;

    @Column(nullable = false)
    private String operator; // >, <, >=, <=, =, !=

    @Column(nullable = false)
    private String comparisonType; // VALUE or COLUMN

    @Column(nullable = true)
    private Double value; // Used when comparisonType = VALUE

    @Column(nullable = true)
    private String comparisonColumnName; // Used when comparisonType = COLUMN

    @Column(nullable = true)
    private Integer comparisonPeriodOffset; // For comparing across years/quarters

    @Column(nullable = false)
    private String logicalOperator = "AND"; // AND or OR with next rule

    @Column(nullable = false)
    private Integer position; // Order of rule in the screener

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
