package com.stockanalysis.service;

import com.stockanalysis.dto.FinancialDataDTO;
import com.stockanalysis.entity.enums.PeriodType;
import java.util.List;

public interface FinancialDataService {
    FinancialDataDTO createOrUpdate(FinancialDataDTO financialDataDTO);
    FinancialDataDTO getById(Long id);
    void delete(Long id);
    List<FinancialDataDTO> getByStockId(Long stockId);
    List<FinancialDataDTO> getByStockAndPeriodType(Long stockId, PeriodType periodType);
    FinancialDataDTO getByStockAndPeriod(Long stockId, PeriodType periodType, Integer year, Integer quarter);
}
