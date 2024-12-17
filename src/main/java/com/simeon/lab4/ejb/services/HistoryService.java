package com.simeon.lab4.ejb.services;

import com.simeon.lab4.dto.AreaCheckResponse;
import com.simeon.lab4.entities.User;

import java.util.List;

public interface HistoryService {
    List<AreaCheckResponse> getResultList(User user);

    void addResult(AreaCheckResponse result, User user);
}
