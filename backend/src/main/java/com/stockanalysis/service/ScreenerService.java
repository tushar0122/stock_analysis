package com.stockanalysis.service;

import com.stockanalysis.dto.StockDTO;
import com.stockanalysis.dto.ScreenerFilterDTO;
import java.util.List;

public interface ScreenerService {
    List<StockDTO> applyFilters(ScreenerFilterDTO filters);
}
