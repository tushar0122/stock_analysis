package com.stockanalysis.service.impl;

import com.stockanalysis.dto.FinancialDataDTO;
import com.stockanalysis.entity.FinancialData;
import com.stockanalysis.entity.Stock;
import com.stockanalysis.entity.enums.PeriodType;
import com.stockanalysis.exception.DuplicateRecordException;
import com.stockanalysis.exception.ResourceNotFoundException;
import com.stockanalysis.repository.FinancialDataRepository;
import com.stockanalysis.repository.StockRepository;
import com.stockanalysis.service.FinancialDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinancialDataServiceImpl implements FinancialDataService {
    private final FinancialDataRepository financialDataRepository;
    private final StockRepository stockRepository;

    @Override
    public FinancialDataDTO createOrUpdate(FinancialDataDTO dto) {
        Stock stock = stockRepository.findById(dto.getStockId())
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + dto.getStockId()));

        FinancialData existingData = financialDataRepository
                .findByStockIdAndPeriodTypeAndYearAndQuarter(
                        dto.getStockId(), dto.getPeriodType(), dto.getYear(), dto.getQuarter())
                .orElse(null);

        if (existingData != null) {
            return updateFinancialData(existingData, dto);
        } else {
            return createFinancialData(stock, dto);
        }
    }

    @Override
    public FinancialDataDTO getById(Long id) {
        FinancialData data = financialDataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Financial data not found with id: " + id));
        return mapToDTO(data);
    }

    @Override
    public void delete(Long id) {
        if (!financialDataRepository.existsById(id)) {
            throw new ResourceNotFoundException("Financial data not found with id: " + id);
        }
        financialDataRepository.deleteById(id);
    }

    @Override
    public List<FinancialDataDTO> getByStockId(Long stockId) {
        return financialDataRepository.findByStockIdOrderByYearDescQuarterDesc(stockId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FinancialDataDTO> getByStockAndPeriodType(Long stockId, PeriodType periodType) {
        return financialDataRepository.findByStockIdAndPeriodTypeOrderByYearDescQuarterDesc(stockId, periodType)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FinancialDataDTO getByStockAndPeriod(Long stockId, PeriodType periodType, Integer year, Integer quarter) {
        FinancialData data = financialDataRepository
                .findByStockIdAndPeriodTypeAndYearAndQuarter(stockId, periodType, year, quarter)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Financial data not found for stock " + stockId + " for " + periodType + " " + year));
        return mapToDTO(data);
    }

    private FinancialDataDTO createFinancialData(Stock stock, FinancialDataDTO dto) {
        FinancialData data = FinancialData.builder()
                .stock(stock)
                .periodType(dto.getPeriodType())
                .year(dto.getYear())
                .quarter(dto.getQuarter())
                .pe(dto.getPe())
                .pb(dto.getPb())
                .ps(dto.getPs())
                .peg(dto.getPeg())
                .roe(dto.getRoe())
                .roa(dto.getRoa())
                .roic(dto.getRoic())
                .debtEquity(dto.getDebtEquity())
                .debtAssets(dto.getDebtAssets())
                .interestCoverage(dto.getInterestCoverage())
                .assetTurnover(dto.getAssetTurnover())
                .receivablesTurnover(dto.getReceivablesTurnover())
                .inventoryTurnover(dto.getInventoryTurnover())
                .revenueGrowth(dto.getRevenueGrowth())
                .earningsGrowth(dto.getEarningsGrowth())
                .bookValueGrowth(dto.getBookValueGrowth())
                .build();

        FinancialData savedData = financialDataRepository.save(data);
        return mapToDTO(savedData);
    }

    private FinancialDataDTO updateFinancialData(FinancialData data, FinancialDataDTO dto) {
        if (dto.getPe() != null) data.setPe(dto.getPe());
        if (dto.getPb() != null) data.setPb(dto.getPb());
        if (dto.getPs() != null) data.setPs(dto.getPs());
        if (dto.getPeg() != null) data.setPeg(dto.getPeg());
        if (dto.getRoe() != null) data.setRoe(dto.getRoe());
        if (dto.getRoa() != null) data.setRoa(dto.getRoa());
        if (dto.getRoic() != null) data.setRoic(dto.getRoic());
        if (dto.getDebtEquity() != null) data.setDebtEquity(dto.getDebtEquity());
        if (dto.getDebtAssets() != null) data.setDebtAssets(dto.getDebtAssets());
        if (dto.getInterestCoverage() != null) data.setInterestCoverage(dto.getInterestCoverage());
        if (dto.getAssetTurnover() != null) data.setAssetTurnover(dto.getAssetTurnover());
        if (dto.getReceivablesTurnover() != null) data.setReceivablesTurnover(dto.getReceivablesTurnover());
        if (dto.getInventoryTurnover() != null) data.setInventoryTurnover(dto.getInventoryTurnover());
        if (dto.getRevenueGrowth() != null) data.setRevenueGrowth(dto.getRevenueGrowth());
        if (dto.getEarningsGrowth() != null) data.setEarningsGrowth(dto.getEarningsGrowth());
        if (dto.getBookValueGrowth() != null) data.setBookValueGrowth(dto.getBookValueGrowth());

        FinancialData updatedData = financialDataRepository.save(data);
        return mapToDTO(updatedData);
    }

    private FinancialDataDTO mapToDTO(FinancialData data) {
        return FinancialDataDTO.builder()
                .id(data.getId())
                .stockId(data.getStock().getId())
                .stockSymbol(data.getStock().getSymbol())
                .periodType(data.getPeriodType())
                .year(data.getYear())
                .quarter(data.getQuarter())
                .pe(data.getPe())
                .pb(data.getPb())
                .ps(data.getPs())
                .peg(data.getPeg())
                .roe(data.getRoe())
                .roa(data.getRoa())
                .roic(data.getRoic())
                .debtEquity(data.getDebtEquity())
                .debtAssets(data.getDebtAssets())
                .interestCoverage(data.getInterestCoverage())
                .assetTurnover(data.getAssetTurnover())
                .receivablesTurnover(data.getReceivablesTurnover())
                .inventoryTurnover(data.getInventoryTurnover())
                .revenueGrowth(data.getRevenueGrowth())
                .earningsGrowth(data.getEarningsGrowth())
                .bookValueGrowth(data.getBookValueGrowth())
                .build();
    }
}
