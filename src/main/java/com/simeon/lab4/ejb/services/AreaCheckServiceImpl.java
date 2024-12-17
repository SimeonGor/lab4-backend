package com.simeon.lab4.ejb.services;

import com.simeon.lab4.dto.AreaCheckRequest;
import com.simeon.lab4.dto.AreaCheckResponse;
import com.simeon.lab4.ejb.repo.UserRepository;
import com.simeon.lab4.entities.User;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class AreaCheckServiceImpl implements AreaCheckService {
    @EJB
    private UserRepository userRepository;

    @EJB
    private HistoryService history;

    @Override
    public AreaCheckResponse handle(AreaCheckRequest request) {
        long start = System.currentTimeMillis();

        boolean hit = CheckUtil.check(request.getX(), request.getY(), request.getR());

        long end = System.currentTimeMillis();
        long workingTime = end - start;

        AreaCheckResponse result = new AreaCheckResponse(request.getX(), request.getY(), request.getR(), hit, workingTime, LocalDateTime.now());

        history.addResult(result);

        return result;
    }

    @Override
    public List<AreaCheckResponse> findAllByUser(String username) {
        User user = userRepository.getByUsername(username);
        return history.getResultList(user);
    }
}
