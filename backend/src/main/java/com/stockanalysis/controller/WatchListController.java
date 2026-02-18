package com.stockanalysis.controller;

import com.stockanalysis.dto.WatchListDTO;
import com.stockanalysis.service.WatchListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlists")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class WatchListController {
    private final WatchListService watchListService;

    @GetMapping
    public List<WatchListDTO> getMyWatchLists(@RequestParam Long userId) {
        return watchListService.getWatchListsByUser(userId);
    }

    @PostMapping
    public WatchListDTO createWatchList(
            @RequestParam Long userId,
            @RequestParam String name,
            @RequestParam(required = false) String description) {
        return watchListService.createWatchList(userId, name, description);
    }

    @GetMapping("/{id}")
    public WatchListDTO getWatchList(@PathVariable Long id) {
        return watchListService.getWatchListById(id);
    }

    @PostMapping("/{watchListId}/stocks/{stockId}")
    public WatchListDTO addStockToWatchList(
            @PathVariable Long watchListId,
            @PathVariable Long stockId) {
        return watchListService.addStockToWatchList(watchListId, stockId);
    }

    @DeleteMapping("/{watchListId}/stocks/{stockId}")
    public void removeStockFromWatchList(
            @PathVariable Long watchListId,
            @PathVariable Long stockId) {
        watchListService.removeStockFromWatchList(watchListId, stockId);
    }

    @DeleteMapping("/{id}")
    public void deleteWatchList(@PathVariable Long id) {
        watchListService.deleteWatchList(id);
    }
}
