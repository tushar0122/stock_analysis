package com.stockanalysis.service.impl;

import com.stockanalysis.dto.StockDTO;
import com.stockanalysis.entity.Stock;
import com.stockanalysis.exception.DuplicateRecordException;
import com.stockanalysis.exception.ResourceNotFoundException;
import com.stockanalysis.repository.StockRepository;
import com.stockanalysis.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;

    @Override
    public StockDTO createStock(StockDTO stockDTO) {
        if (stockRepository.existsBySymbol(stockDTO.getSymbol())) {
            throw new DuplicateRecordException("Stock with symbol " + stockDTO.getSymbol() + " already exists");
        }

        Stock stock = Stock.builder()
                .name(stockDTO.getName())
                .symbol(stockDTO.getSymbol())
                .sector(stockDTO.getSector())
                .industry(stockDTO.getIndustry())
                .exchange(stockDTO.getExchange())
                .marketCap(stockDTO.getMarketCap())
                .build();

        Stock savedStock = stockRepository.save(stock);
        return mapToDTO(savedStock);
    }

    @Override
    public StockDTO updateStock(Long id, StockDTO stockDTO) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + id));

        stock.setName(stockDTO.getName());
        stock.setSector(stockDTO.getSector());
        stock.setIndustry(stockDTO.getIndustry());
        stock.setExchange(stockDTO.getExchange());
        stock.setMarketCap(stockDTO.getMarketCap());

        Stock updatedStock = stockRepository.save(stock);
        return mapToDTO(updatedStock);
    }

    @Override
    public StockDTO getStockById(Long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + id));
        return mapToDTO(stock);
    }

    @Override
    public StockDTO getStockBySymbol(String symbol) {
        Stock stock = stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with symbol: " + symbol));
        return mapToDTO(stock);
    }

    @Override
    public void deleteStock(Long id) {
        if (!stockRepository.existsById(id)) {
            throw new ResourceNotFoundException("Stock not found with id: " + id);
        }
        stockRepository.deleteById(id);
    }

    @Override
    public Page<StockDTO> getAllStocks(Pageable pageable) {
        return stockRepository.findAll(pageable).map(this::mapToDTO);
    }

    @Override
    public Page<StockDTO> searchStocks(String searchTerm, Pageable pageable) {
        return stockRepository.searchByNameOrSymbol(searchTerm, pageable).map(this::mapToDTO);
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
