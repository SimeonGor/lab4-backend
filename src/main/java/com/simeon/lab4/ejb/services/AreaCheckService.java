package com.simeon.lab4.ejb.services;

import com.simeon.lab4.dto.AreaCheckRequest;
import com.simeon.lab4.dto.AreaCheckResponse;

public interface AreaCheckService {
    AreaCheckResponse handle(AreaCheckRequest request);
}
