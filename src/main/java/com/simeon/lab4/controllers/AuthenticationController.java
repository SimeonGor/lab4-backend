package com.simeon.lab4.controllers;

import com.simeon.lab4.dto.AuthenticationRequest;
import com.simeon.lab4.dto.AuthenticationResponse;
import com.simeon.lab4.ejb.services.AuthenticationService;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/auth")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class AuthenticationController {
    @EJB
    private AuthenticationService authenticationService;

    @Path("/login")
    @POST
    public AuthenticationResponse login(@Valid @NotNull AuthenticationRequest request) {
        return authenticationService.login(request.getUsername(), request.getPassword());
    }

    @Path("/register")
    @POST
    public AuthenticationResponse register(@Valid @NotNull AuthenticationRequest request) {
        return authenticationService.register(request.getUsername(), request.getPassword());
    }
}
