package com.stockanalysis.service;

import com.stockanalysis.dto.WatchListDTO;

import java.util.List;

public interface WatchListService {
    WatchListDTO createWatchList(Long userId, String name, String description);
    WatchListDTO getWatchListById(Long id);
    List<WatchListDTO> getWatchListsByUser(Long userId);
    void deleteWatchList(Long id);
    WatchListDTO addStockToWatchList(Long watchListId, Long stockId);
    void removeStockFromWatchList(Long watchListId, Long stockId);
}
