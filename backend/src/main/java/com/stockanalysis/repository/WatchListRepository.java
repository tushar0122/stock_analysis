package com.stockanalysis.repository;

import com.stockanalysis.entity.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList, Long> {
    List<WatchList> findByUserId(Long userId);
}
