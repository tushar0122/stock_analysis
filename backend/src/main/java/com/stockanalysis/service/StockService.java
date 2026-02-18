package com.stockanalysis.service;

import com.stockanalysis.dto.StockDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockService {
    StockDTO createStock(StockDTO stockDTO);
    StockDTO updateStock(Long id, StockDTO stockDTO);
    StockDTO getStockById(Long id);
    StockDTO getStockBySymbol(String symbol);
    void deleteStock(Long id);
    Page<StockDTO> getAllStocks(Pageable pageable);
    Page<StockDTO> searchStocks(String searchTerm, Pageable pageable);
}
