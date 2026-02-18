package com.stockanalysis.service;

import java.util.List;

public interface AuditService {
    void logAction(String entityType, Long entityId, String action, Long userId, String oldValues, String newValues, String remarks);
    List<Object> getEntityHistory(String entityType, Long entityId);
    List<Object> getUserAuditLog(Long userId);
}
