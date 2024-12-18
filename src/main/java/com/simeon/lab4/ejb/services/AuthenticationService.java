package com.simeon.lab4.ejb.services;

import com.simeon.lab4.dto.AuthenticationResponse;
import com.simeon.lab4.ejb.expetions.InvalidAuthenticationException;
import com.simeon.lab4.ejb.expetions.UserExistException;
import com.simeon.lab4.ejb.repo.UserRepository;
import com.simeon.lab4.ejb.utils.PasswordUtil;
import com.simeon.lab4.entities.User;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.Date;

@Stateless
public class AuthenticationService {
    @EJB
    private TokenService tokenService;

    @EJB
    private UserRepository userRepository;

    private static final long TOKEN_DURATION = (long) 24 * 60 * 60 * 1000;

    public AuthenticationResponse login(String username, String password) throws InvalidAuthenticationException {
        User user = userRepository.getByUsername(username);

        if (user == null || !PasswordUtil.validatePassword(user, password)) {
            throw new InvalidAuthenticationException("Invalid username or password");
        }

        Date expirationDate = new Date(System.currentTimeMillis() + TOKEN_DURATION);
        String token = tokenService.generateToken(username, expirationDate);

        return new AuthenticationResponse(token, expirationDate.toInstant().toEpochMilli());
    }

    public AuthenticationResponse register(String username, String password) throws UserExistException {
        if (userRepository.getByUsername(username) != null) {
            throw new UserExistException("User with username " + username + " already exists");
        }

        User user = new User();
        user.setUsername(username);
        PasswordUtil.setPassword(user, password);
        userRepository.save(user);

        Date expirationDate = new Date(System.currentTimeMillis() + TOKEN_DURATION);
        String token = tokenService.generateToken(username, expirationDate);

        return new AuthenticationResponse(token, expirationDate.toInstant().toEpochMilli());
    }
}
