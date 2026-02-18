package com.stockanalysis.service.impl;

import com.stockanalysis.dto.StockDTO;
import com.stockanalysis.dto.ScreenerFilterDTO;
import com.stockanalysis.entity.FinancialData;
import com.stockanalysis.entity.Stock;
import com.stockanalysis.exception.InvalidDataException;
import com.stockanalysis.repository.FinancialDataRepository;
import com.stockanalysis.repository.StockRepository;
import com.stockanalysis.service.ScreenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScreenerServiceImpl implements ScreenerService {
    private final StockRepository stockRepository;
    private final FinancialDataRepository financialDataRepository;

    @Override
    public List<StockDTO> applyFilters(ScreenerFilterDTO filters) {
        if (filters.getConditions().isEmpty()) {
            return new ArrayList<>();
        }

        List<Stock> allStocks = stockRepository.findAll();
        String operator = filters.getOperator() != null ? filters.getOperator() : "AND";

        return allStocks.stream()
                .filter(stock -> evaluateStock(stock, filters.getConditions(), operator))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private boolean evaluateStock(Stock stock, List<ScreenerFilterDTO.Condition> conditions, String operator) {
        boolean result = "AND".equals(operator);

        for (ScreenerFilterDTO.Condition condition : conditions) {
            boolean conditionResult = evaluateCondition(stock, condition);

            if ("AND".equals(operator)) {
                result = result && conditionResult;
                if (!result) break;
            } else if ("OR".equals(operator)) {
                result = result || conditionResult;
            }
        }

        return result;
    }

    private boolean evaluateCondition(Stock stock, ScreenerFilterDTO.Condition condition) {
        List<FinancialData> stockData = financialDataRepository.findByStockIdOrderByYearDescQuarterDesc(stock.getId());

        if (stockData.isEmpty()) {
            return false;
        }

        FinancialData latestData = stockData.get(0);
        Double fieldValue = getFieldValue(latestData, condition.getField());

        if (fieldValue == null) {
            return false;
        }

        String comparison = condition.getComparison();
        Double compareValue = Double.parseDouble(condition.getValue());

        return applyComparison(fieldValue, compareValue, comparison);
    }

    private Double getFieldValue(FinancialData data, String field) {
        return switch (field.toLowerCase()) {
            case "pe" -> data.getPe();
            case "pb" -> data.getPb();
            case "ps" -> data.getPs();
            case "peg" -> data.getPeg();
            case "roe" -> data.getRoe();
            case "roa" -> data.getRoa();
            case "roic" -> data.getRoic();
            case "debtequity" -> data.getDebtEquity();
            case "debtassets" -> data.getDebtAssets();
            case "interestcoverage" -> data.getInterestCoverage();
            case "assetturnover" -> data.getAssetTurnover();
            case "receivablesturnover" -> data.getReceivablesTurnover();
            case "inventoryturnover" -> data.getInventoryTurnover();
            case "revenuegrowth" -> data.getRevenueGrowth();
            case "earningsgrowth" -> data.getEarningsGrowth();
            case "bookvaluegrowth" -> data.getBookValueGrowth();
            default -> null;
        };
    }

    private boolean applyComparison(Double fieldValue, Double compareValue, String comparison) {
        return switch (comparison) {
            case ">" -> fieldValue > compareValue;
            case "<" -> fieldValue < compareValue;
            case "=" -> fieldValue.equals(compareValue);
            case ">=" -> fieldValue >= compareValue;
            case "<=" -> fieldValue <= compareValue;
            default -> false;
        };
    }

    private StockDTO mapToDTO(Stock stock) {
        return StockDTO.builder()
                .id(stock.getId())
                .name(stock.getName())
                .symbol(stock.getSymbol())
                .sector(stock.getSector())
                .industry(stock.getIndustry())
                .exchange(stock.getExchange())
                .marketCap(stock.getMarketCap())
                .build();
    }
}
