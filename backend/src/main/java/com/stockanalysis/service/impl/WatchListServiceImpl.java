package com.stockanalysis.service.impl;

import com.stockanalysis.dto.StockDTO;
import com.stockanalysis.dto.WatchListDTO;
import com.stockanalysis.entity.Stock;
import com.stockanalysis.entity.User;
import com.stockanalysis.entity.WatchList;
import com.stockanalysis.entity.WatchListItem;
import com.stockanalysis.exception.DuplicateRecordException;
import com.stockanalysis.exception.ResourceNotFoundException;
import com.stockanalysis.repository.StockRepository;
import com.stockanalysis.repository.UserRepository;
import com.stockanalysis.repository.WatchListItemRepository;
import com.stockanalysis.repository.WatchListRepository;
import com.stockanalysis.service.WatchListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchListServiceImpl implements WatchListService {
    private final WatchListRepository watchListRepository;
    private final WatchListItemRepository watchListItemRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;

    @Override
    public WatchListDTO createWatchList(Long userId, String name, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        WatchList watchList = WatchList.builder()
                .user(user)
                .name(name)
                .description(description)
                .build();

        WatchList savedWatchList = watchListRepository.save(watchList);
        return mapToDTO(savedWatchList);
    }

    @Override
    public WatchListDTO getWatchListById(Long id) {
        WatchList watchList = watchListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WatchList not found with id: " + id));
        return mapToDTO(watchList);
    }

    @Override
    public List<WatchListDTO> getWatchListsByUser(Long userId) {
        return watchListRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteWatchList(Long id) {
        if (!watchListRepository.existsById(id)) {
            throw new ResourceNotFoundException("WatchList not found with id: " + id);
        }
        watchListRepository.deleteById(id);
    }

    @Override
    public WatchListDTO addStockToWatchList(Long watchListId, Long stockId) {
        WatchList watchList = watchListRepository.findById(watchListId)
                .orElseThrow(() -> new ResourceNotFoundException("WatchList not found with id: " + watchListId));

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + stockId));

        if (watchListItemRepository.findByWatchListIdAndStockId(watchListId, stockId).isPresent()) {
            throw new DuplicateRecordException("Stock already exists in watchlist");
        }

        WatchListItem item = WatchListItem.builder()
                .watchList(watchList)
                .stock(stock)
                .build();

        watchListItemRepository.save(item);
        watchList.getItems().add(item);

        return mapToDTO(watchList);
    }

    @Override
    public void removeStockFromWatchList(Long watchListId, Long stockId) {
        WatchListItem item = watchListItemRepository.findByWatchListIdAndStockId(watchListId, stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found in watchlist"));
        watchListItemRepository.deleteByWatchListIdAndStockId(watchListId, stockId);
    }

    private WatchListDTO mapToDTO(WatchList watchList) {
        return WatchListDTO.builder()
                .id(watchList.getId())
                .name(watchList.getName())
                .description(watchList.getDescription())
                .stocks(watchList.getItems().stream()
                        .map(item -> mapStockToDTO(item.getStock()))
                        .collect(Collectors.toSet()))
                .build();
    }

    private StockDTO mapStockToDTO(Stock stock) {
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
