package com.stockanalysis.service.impl;

import com.stockanalysis.dto.UploadResponseDTO;
import com.stockanalysis.entity.FinancialData;
import com.stockanalysis.entity.Stock;
import com.stockanalysis.entity.enums.PeriodType;
import com.stockanalysis.exception.InvalidDataException;
import com.stockanalysis.repository.FinancialDataRepository;
import com.stockanalysis.repository.StockRepository;
import com.stockanalysis.service.CsvUploadService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CsvUploadServiceImpl implements CsvUploadService {
    private final FinancialDataRepository financialDataRepository;
    private final StockRepository stockRepository;

    private static final String[] HEADERS = {
            "stock_symbol", "period_type", "year", "quarter",
            "pe", "pb", "ps", "peg", "roe", "roa", "roic",
            "debt_equity", "debt_assets", "interest_coverage",
            "asset_turnover", "receivables_turnover", "inventory_turnover",
            "revenue_growth", "earnings_growth", "book_value_growth"
    };

    @Override
    public UploadResponseDTO uploadFinancialData(MultipartFile file) {
        UploadResponseDTO response = UploadResponseDTO.builder()
                .totalRows(0)
                .successCount(0)
                .failedCount(0)
                .errors(new ArrayList<>())
                .build();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            int rowNumber = 1;
            for (CSVRecord record : csvParser) {
                rowNumber++;
                response.setTotalRows(response.getTotalRows() + 1);

                try {
                    FinancialData financialData = parseAndValidateRecord(record);
                    saveFinancialData(financialData);
                    response.setSuccessCount(response.getSuccessCount() + 1);
                } catch (InvalidDataException e) {
                    response.setFailedCount(response.getFailedCount() + 1);
                    response.getErrors().add(UploadResponseDTO.UploadError.builder()
                            .rowNumber(rowNumber)
                            .error(e.getMessage())
                            .build());
                }
            }
        } catch (IOException e) {
            throw new InvalidDataException("Error reading CSV file: " + e.getMessage());
        }

        return response;
    }

    private FinancialData parseAndValidateRecord(CSVRecord record) {
        String symbol = getFieldValue(record, "stock_symbol");
        String periodTypeStr = getFieldValue(record, "period_type");
        String yearStr = getFieldValue(record, "year");
        String quarterStr = getFieldValue(record, "quarter");

        if (symbol == null || symbol.isEmpty()) {
            throw new InvalidDataException("stock_symbol is required");
        }

        if (periodTypeStr == null || periodTypeStr.isEmpty()) {
            throw new InvalidDataException("period_type is required");
        }

        if (yearStr == null || yearStr.isEmpty()) {
            throw new InvalidDataException("year is required");
        }

        PeriodType periodType;
        try {
            periodType = PeriodType.valueOf(periodTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException("Invalid period_type: " + periodTypeStr);
        }

        Integer year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("year must be numeric");
        }

        Integer quarter = null;
        if (periodType == PeriodType.QUARTERLY && quarterStr != null && !quarterStr.isEmpty()) {
            try {
                quarter = Integer.parseInt(quarterStr);
                if (quarter < 1 || quarter > 4) {
                    throw new InvalidDataException("quarter must be between 1 and 4");
                }
            } catch (NumberFormatException e) {
                throw new InvalidDataException("quarter must be numeric");
            }
        }

        Stock stock = stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> new InvalidDataException("Stock not found with symbol: " + symbol));

        FinancialData data = FinancialData.builder()
                .stock(stock)
                .periodType(periodType)
                .year(year)
                .quarter(quarter)
                .pe(parseDouble(getFieldValue(record, "pe")))
                .pb(parseDouble(getFieldValue(record, "pb")))
                .ps(parseDouble(getFieldValue(record, "ps")))
                .peg(parseDouble(getFieldValue(record, "peg")))
                .roe(parseDouble(getFieldValue(record, "roe")))
                .roa(parseDouble(getFieldValue(record, "roa")))
                .roic(parseDouble(getFieldValue(record, "roic")))
                .debtEquity(parseDouble(getFieldValue(record, "debt_equity")))
                .debtAssets(parseDouble(getFieldValue(record, "debt_assets")))
                .interestCoverage(parseDouble(getFieldValue(record, "interest_coverage")))
                .assetTurnover(parseDouble(getFieldValue(record, "asset_turnover")))
                .receivablesTurnover(parseDouble(getFieldValue(record, "receivables_turnover")))
                .inventoryTurnover(parseDouble(getFieldValue(record, "inventory_turnover")))
                .revenueGrowth(parseDouble(getFieldValue(record, "revenue_growth")))
                .earningsGrowth(parseDouble(getFieldValue(record, "earnings_growth")))
                .bookValueGrowth(parseDouble(getFieldValue(record, "book_value_growth")))
                .build();

        return data;
    }

    private String getFieldValue(CSVRecord record, String fieldName) {
        try {
            return record.isMapped(fieldName) ? record.get(fieldName) : null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Invalid numeric value: " + value);
        }
    }

    private void saveFinancialData(FinancialData data) {
        Optional<FinancialData> existing = financialDataRepository
                .findByStockIdAndPeriodTypeAndYearAndQuarter(
                        data.getStock().getId(),
                        data.getPeriodType(),
                        data.getYear(),
                        data.getQuarter());

        if (existing.isPresent()) {
            FinancialData existing_data = existing.get();
            if (data.getPe() != null) existing_data.setPe(data.getPe());
            if (data.getPb() != null) existing_data.setPb(data.getPb());
            if (data.getPs() != null) existing_data.setPs(data.getPs());
            if (data.getPeg() != null) existing_data.setPeg(data.getPeg());
            if (data.getRoe() != null) existing_data.setRoe(data.getRoe());
            if (data.getRoa() != null) existing_data.setRoa(data.getRoa());
            if (data.getRoic() != null) existing_data.setRoic(data.getRoic());
            if (data.getDebtEquity() != null) existing_data.setDebtEquity(data.getDebtEquity());
            if (data.getDebtAssets() != null) existing_data.setDebtAssets(data.getDebtAssets());
            if (data.getInterestCoverage() != null) existing_data.setInterestCoverage(data.getInterestCoverage());
            if (data.getAssetTurnover() != null) existing_data.setAssetTurnover(data.getAssetTurnover());
            if (data.getReceivablesTurnover() != null) existing_data.setReceivablesTurnover(data.getReceivablesTurnover());
            if (data.getInventoryTurnover() != null) existing_data.setInventoryTurnover(data.getInventoryTurnover());
            if (data.getRevenueGrowth() != null) existing_data.setRevenueGrowth(data.getRevenueGrowth());
            if (data.getEarningsGrowth() != null) existing_data.setEarningsGrowth(data.getEarningsGrowth());
            if (data.getBookValueGrowth() != null) existing_data.setBookValueGrowth(data.getBookValueGrowth());

            financialDataRepository.save(existing_data);
        } else {
            financialDataRepository.save(data);
        }
    }
}
