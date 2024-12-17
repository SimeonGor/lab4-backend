package com.simeon.lab4.ejb.services;

import com.simeon.lab4.dto.AreaCheckRequest;
import com.simeon.lab4.dto.AreaCheckResponse;

import java.util.List;

public interface AreaCheckService {
    AreaCheckResponse handle(AreaCheckRequest request);
    List<AreaCheckResponse> findAllByUser(String username);
}
