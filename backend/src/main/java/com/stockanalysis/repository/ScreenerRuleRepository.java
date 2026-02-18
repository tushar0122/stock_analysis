package com.stockanalysis.repository;

import com.stockanalysis.entity.ScreenerRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScreenerRuleRepository extends JpaRepository<ScreenerRule, Long> {
    List<ScreenerRule> findBySavedScreenerIdOrderByPosition(Long savedScreenerId);
    List<ScreenerRule> findByColumnName(String columnName);
}
