package com.stockanalysis.service;

import com.stockanalysis.dto.ScreenerFilterDTO;
import java.util.List;

public interface SavedScreenerService {
    Object createSavedScreener(Long userId, String name, String description, ScreenerFilterDTO filters, Boolean isPublic);
    Object getSavedScreener(Long id);
    List<Object> getUserSavedScreeners(Long userId);
    List<Object> getPublicScreeners();
    void deleteSavedScreener(Long id);
    Object updateSavedScreener(Long id, String name, String description, ScreenerFilterDTO filters);
}
