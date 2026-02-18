package com.stockanalysis.controller;

import com.stockanalysis.dto.ScreenerFilterDTO;
import com.stockanalysis.dto.StockDTO;
import com.stockanalysis.service.ScreenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/screener")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ScreenerController {
    private final ScreenerService screenerService;

    @PostMapping("/filter")
    public List<StockDTO> filterStocks(@RequestBody ScreenerFilterDTO filters) {
        return screenerService.applyFilters(filters);
    }
}
