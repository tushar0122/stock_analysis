package com.stockanalysis.entity;

import com.stockanalysis.entity.enums.PeriodType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "financial_data", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"stock_id", "period_type", "year", "quarter"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PeriodType periodType;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = true)
    private Integer quarter; // Q1, Q2, Q3, Q4 (1-4) for QUARTERLY

    // VALUATION RATIOS
    @Column(nullable = true)
    private Double pe; // Price to Earnings

    @Column(nullable = true)
    private Double pb; // Price to Book

    @Column(nullable = true)
    private Double ps; // Price to Sales

    @Column(nullable = true)
    private Double peg; // PEG Ratio

    @Column(nullable = true)
    private Double evEbitda; // EV/EBITDA

    @Column(nullable = true)
    private Double dividendYield; // Dividend Yield %

    // RETURN RATIOS
    @Column(nullable = true)
    private Double roe; // Return on Equity

    @Column(nullable = true)
    private Double roa; // Return on Assets

    @Column(nullable = true)
    private Double roic; // Return on Invested Capital

    @Column(nullable = true)
    private Double roce; // Return on Capital Employed

    // SOLVENCY RATIOS
    @Column(nullable = true)
    private Double debtEquity; // Debt to Equity

    @Column(nullable = true)
    private Double debtAssets; // Debt to Assets

    @Column(nullable = true)
    private Double interestCoverage; // Interest Coverage

    @Column(nullable = true)
    private Double currentRatio; // Current Ratio

    // EFFICIENCY RATIOS
    @Column(nullable = true)
    private Double assetTurnover; // Asset Turnover

    @Column(nullable = true)
    private Double receivablesTurnover; // Receivables Turnover

    @Column(nullable = true)
    private Double inventoryTurnover; // Inventory Turnover

    // GROWTH RATIOS
    @Column(nullable = true)
    private Double revenueGrowth; // Revenue Growth %

    @Column(nullable = true)
    private Double earningsGrowth; // Earnings Growth %

    @Column(nullable = true)
    private Double bookValueGrowth; // Book Value Growth %

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDraft = true; // Draft status

    @Column(nullable = true)
    private LocalDateTime publishedAt; // When published

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
