package com.stockanalysis.repository;

import com.stockanalysis.entity.SavedScreener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedScreenerRepository extends JpaRepository<SavedScreener, Long> {
    List<SavedScreener> findByUserId(Long userId);
    List<SavedScreener> findByUserIdOrIsPublic(Long userId, Boolean isPublic);
}
