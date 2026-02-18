package com.stockanalysis.repository;

import com.stockanalysis.entity.FinancialData;
import com.stockanalysis.entity.enums.PeriodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialDataRepository extends JpaRepository<FinancialData, Long> {
    Optional<FinancialData> findByStockIdAndPeriodTypeAndYearAndQuarter(
            Long stockId, PeriodType periodType, Integer year, Integer quarter);

    List<FinancialData> findByStockIdAndPeriodTypeOrderByYearDescQuarterDesc(
            Long stockId, PeriodType periodType);

    List<FinancialData> findByStockIdOrderByYearDescQuarterDesc(Long stockId);

    @Query("SELECT fd FROM FinancialData fd WHERE fd.stock.id = :stockId AND fd.periodType = :periodType AND fd.year = :year")
    Optional<FinancialData> findYearlyData(
            @Param("stockId") Long stockId,
            @Param("periodType") PeriodType periodType,
            @Param("year") Integer year);

    @Query(value = "SELECT DISTINCT year FROM financial_data WHERE stock_id = :stockId AND period_type = :periodType ORDER BY year DESC", nativeQuery = true)
    List<Integer> findDistinctYears(@Param("stockId") Long stockId, @Param("periodType") String periodType);
}
