package com.simeon.lab4.ejb.services;

import com.simeon.lab4.dto.AuthenticationResponse;
import com.simeon.lab4.ejb.repo.UserRepository;
import com.simeon.lab4.ejb.utils.PasswordUtil;
import com.simeon.lab4.entities.User;
import jakarta.ejb.EJB;

import java.util.Date;

public class AuthenticationService {
    @EJB
    private TokenService tokenService;

    @EJB
    private UserRepository userRepository;

    private static final long tokenDuration = 24 * 60 * 60 * 1000;

    public AuthenticationResponse login(String username, String password) {
        User user = userRepository.getByUsername(username);

        if (user == null || PasswordUtil.validatePassword(user, password)) {
            return null;
        }

        Date expirationDate = new Date(System.currentTimeMillis() + tokenDuration);
        String token = tokenService.generateToken(username, expirationDate);

        return new AuthenticationResponse(token, expirationDate.toInstant().toEpochMilli());
    }

    public AuthenticationResponse register(String username, String password) {
        if (userRepository.getByUsername(username) != null) {
            return null;
        }

        User user = new User();
        user.setUsername(username);
        PasswordUtil.setPassword(user, password);
        userRepository.save(user);

        Date expirationDate = new Date(System.currentTimeMillis() + tokenDuration);
        String token = tokenService.generateToken(username, expirationDate);

        return new AuthenticationResponse(token, expirationDate.toInstant().toEpochMilli());
    }
}
