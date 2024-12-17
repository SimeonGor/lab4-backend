package com.simeon.lab4.ejb.services;

import com.simeon.lab4.dto.AreaCheckRequest;
import com.simeon.lab4.dto.AreaCheckResponse;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.time.LocalDateTime;

@Stateless
public class AreaCheckServiceImpl implements AreaCheckService {
    @EJB
    private HistoryService history;

    @Override
    public AreaCheckResponse handle(AreaCheckRequest request) {
        long start = System.currentTimeMillis();

        boolean hit = CheckUtil.check(request.getX(), request.getY(), request.getR());

        long end = System.currentTimeMillis();
        long workingTime = end - start;

        AreaCheckResponse result = new AreaCheckResponse(request.getX(), request.getY(), request.getR(), hit, workingTime, LocalDateTime.now());

//        history.addResult(result);

        return result;
    }
}
