package com.simeon.lab4.ejb.services;

import com.simeon.lab4.dto.AreaCheckResponse;

import java.util.List;

public interface HistoryService {
    List<AreaCheckResponse> getResultList();

    void addResult(AreaCheckResponse result);
}
