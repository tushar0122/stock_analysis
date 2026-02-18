package com.stockanalysis.repository;

import com.stockanalysis.entity.WatchListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchListItemRepository extends JpaRepository<WatchListItem, Long> {
    Optional<WatchListItem> findByWatchListIdAndStockId(Long watchListId, Long stockId);
    void deleteByWatchListIdAndStockId(Long watchListId, Long stockId);
}
