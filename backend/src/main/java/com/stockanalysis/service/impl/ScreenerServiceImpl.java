package com.stockanalysis.service.impl;

import com.stockanalysis.dto.StockDTO;
import com.stockanalysis.dto.ScreenerFilterDTO;
import com.stockanalysis.dto.ScreenerRuleDTO;
import com.stockanalysis.entity.FinancialData;
import com.stockanalysis.entity.Stock;
import com.stockanalysis.exception.InvalidDataException;
import com.stockanalysis.repository.FinancialDataRepository;
import com.stockanalysis.repository.StockRepository;
import com.stockanalysis.service.ColumnMetadataService;
import com.stockanalysis.service.ScreenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScreenerServiceImpl implements ScreenerService {
    private final StockRepository stockRepository;
    private final FinancialDataRepository financialDataRepository;
    private final ColumnMetadataService columnMetadataService;

    @Override
    public List<StockDTO> applyFilters(ScreenerFilterDTO filters) {
        // Support both old condition format and new rules format
        boolean hasRules = filters.getRules() != null && !filters.getRules().isEmpty();
        boolean hasConditions = filters.getConditions() != null && !filters.getConditions().isEmpty();

        if (!hasRules && !hasConditions) {
            return new ArrayList<>();
        }

        List<Stock> allStocks = stockRepository.findAll();
        String operator = filters.getOperator() != null ? filters.getOperator() : "AND";

        if (hasRules) {
            return allStocks.stream()
                    .filter(stock -> evaluateStockByRules(stock, filters.getRules(), operator))
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } else {
            return allStocks.stream()
                    .filter(stock -> evaluateStock(stock, filters.getConditions(), operator))
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Evaluate stock against advanced rules with column-to-column comparisons
     */
    private boolean evaluateStockByRules(Stock stock, List<ScreenerRuleDTO> rules, String operator) {
        if (rules.isEmpty()) {
            return true;
        }

        // Sort rules by position
        List<ScreenerRuleDTO> sortedRules = rules.stream()
                .sorted(Comparator.comparingInt(ScreenerRuleDTO::getPosition))
                .collect(Collectors.toList());

        List<FinancialData> stockData = financialDataRepository.findByStockIdOrderByYearDescQuarterDesc(stock.getId());
        if (stockData.isEmpty()) {
            return false;
        }

        boolean result = evaluateRule(stock, sortedRules.get(0), stockData);

        for (int i = 1; i < sortedRules.size(); i++) {
            ScreenerRuleDTO currentRule = sortedRules.get(i);
            ScreenerRuleDTO previousRule = sortedRules.get(i - 1);
            String logicalOp = previousRule.getLogicalOperator() != null ? previousRule.getLogicalOperator() : "AND";

            boolean currentResult = evaluateRule(stock, currentRule, stockData);

            if ("AND".equals(logicalOp)) {
                result = result && currentResult;
                if (!result) break; // Short-circuit
            } else if ("OR".equals(logicalOp)) {
                result = result || currentResult;
            }
        }

        return result;
    }

    /**
     * Evaluate a single advanced rule
     */
    private boolean evaluateRule(Stock stock, ScreenerRuleDTO rule, List<FinancialData> stockData) {
        // Validate column exists
        if (!columnMetadataService.columnExists(rule.getColumnName())) {
            throw new InvalidDataException("Column does not exist: " + rule.getColumnName());
        }

        if (!columnMetadataService.isNumericColumn(rule.getColumnName())) {
            throw new InvalidDataException("Column is not numeric: " + rule.getColumnName());
        }

        Double value1 = getFieldValueFromData(stockData.get(0), rule.getColumnName());

        if (value1 == null) {
            return false;
        }

        Double value2;

        if ("VALUE".equals(rule.getComparisonType())) {
            value2 = rule.getValue();
            if (value2 == null) {
                return false;
            }
        } else if ("COLUMN".equals(rule.getComparisonType())) {
            // Validate comparison column
            if (!columnMetadataService.columnExists(rule.getComparisonColumnName())) {
                throw new InvalidDataException("Comparison column does not exist: " + rule.getComparisonColumnName());
            }

            if (!columnMetadataService.isNumericColumn(rule.getComparisonColumnName())) {
                throw new InvalidDataException("Comparison column is not numeric: " + rule.getComparisonColumnName());
            }

            // Get the data with period offset
            int offset = rule.getComparisonPeriodOffset() != null ? rule.getComparisonPeriodOffset() : 0;
            FinancialData comparisonData = getDataWithPeriodOffset(stockData, offset);

            if (comparisonData == null) {
                return false;
            }

            value2 = getFieldValueFromData(comparisonData, rule.getComparisonColumnName());
            if (value2 == null) {
                return false;
            }
        } else {
            return false;
        }

        return applyComparison(value1, value2, rule.getOperator());
    }

    /**
     * Get financial data with period offset (0 = same year, -1 = previous year, etc.)
     */
    private FinancialData getDataWithPeriodOffset(List<FinancialData> sortedData, int offset) {
        if (offset == 0) {
            return sortedData.get(0);
        }

        if (Math.abs(offset) >= sortedData.size()) {
            return null;
        }

        return sortedData.get(Math.abs(offset));
    }

    /**
     * Dynamically extract numeric field value from FinancialData using reflection
     */
    private Double getFieldValueFromData(FinancialData data, String fieldName) {
        try {
            Field field = FinancialData.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(data);
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }
            return null;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
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
