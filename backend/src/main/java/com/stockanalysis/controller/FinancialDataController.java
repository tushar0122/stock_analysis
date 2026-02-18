package com.stockanalysis.controller;

import com.stockanalysis.dto.FinancialDataDTO;
import com.stockanalysis.entity.enums.PeriodType;
import com.stockanalysis.service.FinancialDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financial-data")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class FinancialDataController {
    private final FinancialDataService financialDataService;

    @GetMapping("/{id}")
    public FinancialDataDTO getById(@PathVariable Long id) {
        return financialDataService.getById(id);
    }

    @GetMapping("/stock/{stockId}")
    public List<FinancialDataDTO> getByStockId(@PathVariable Long stockId) {
        return financialDataService.getByStockId(stockId);
    }

    @GetMapping("/stock/{stockId}/period/{periodType}")
    public List<FinancialDataDTO> getByStockAndPeriodType(
            @PathVariable Long stockId,
            @PathVariable PeriodType periodType) {
        return financialDataService.getByStockAndPeriodType(stockId, periodType);
    }

    @PostMapping
    public FinancialDataDTO createOrUpdate(@RequestBody FinancialDataDTO financialDataDTO) {
        return financialDataService.createOrUpdate(financialDataDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        financialDataService.delete(id);
    }
}
