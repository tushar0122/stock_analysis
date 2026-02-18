package com.stockanalysis.dto;

import com.stockanalysis.entity.enums.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialDataDTO {
    private Long id;
    private Long stockId;
    private String stockSymbol;
    private PeriodType periodType;
    private Integer year;
    private Integer quarter;

    // Valuation Ratios
    private Double pe;
    private Double pb;
    private Double ps;
    private Double peg;
    private Double evEbitda;
    private Double dividendYield;

    // Return Ratios
    private Double roe;
    private Double roa;
    private Double roic;
    private Double roce;

    // Solvency Ratios
    private Double debtEquity;
    private Double debtAssets;
    private Double interestCoverage;
    private Double currentRatio;

    // Efficiency Ratios
    private Double assetTurnover;
    private Double receivablesTurnover;
    private Double inventoryTurnover;

    // Growth Ratios
    private Double revenueGrowth;
    private Double earningsGrowth;
    private Double bookValueGrowth;

    // Draft & Publish
    private Boolean isDraft;
    private LocalDateTime publishedAt;

}
