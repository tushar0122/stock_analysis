package com.stockanalysis.controller;

import com.stockanalysis.dto.StockDTO;
import com.stockanalysis.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class StockController {
    private final StockService stockService;

    @GetMapping
    public Page<StockDTO> getAllStocks(Pageable pageable) {
        return stockService.getAllStocks(pageable);
    }

    @GetMapping("/search")
    public Page<StockDTO> searchStocks(@RequestParam String q, Pageable pageable) {
        return stockService.searchStocks(q, pageable);
    }

    @GetMapping("/{id}")
    public StockDTO getStockById(@PathVariable Long id) {
        return stockService.getStockById(id);
    }

    @GetMapping("/symbol/{symbol}")
    public StockDTO getStockBySymbol(@PathVariable String symbol) {
        return stockService.getStockBySymbol(symbol);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StockDTO createStock(@RequestBody StockDTO stockDTO) {
        return stockService.createStock(stockDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public StockDTO updateStock(@PathVariable Long id, @RequestBody StockDTO stockDTO) {
        return stockService.updateStock(id, stockDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
    }
}
