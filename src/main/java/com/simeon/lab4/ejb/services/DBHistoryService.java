package com.simeon.lab4.ejb.services;

import com.simeon.lab4.dto.AreaCheckResponse;
import com.simeon.lab4.ejb.repo.CheckResultRepository;
import com.simeon.lab4.entities.CheckResult;
import com.simeon.lab4.entities.User;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class DBHistoryService implements HistoryService {
    @EJB
    private CheckResultRepository checkResultRepository;

    @Override
    public List<AreaCheckResponse> getResultList(User user) {
        return checkResultRepository.findAllByUser(user).stream()
                .map(AreaCheckResponse::of)
                .toList();
    }

    @Override
    public void addResult(AreaCheckResponse result) {
        checkResultRepository.save(AreaCheckResponse.toCheckResult(result));
    }
}
