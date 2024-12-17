package com.simeon.lab4.controllers;

import com.simeon.lab4.dto.AuthenticationResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/auth")
public class AuthenticationController {
    @Path("/login")
    @Produces(APPLICATION_JSON)
    public AuthenticationResponse login() {
        return null;
    }
}
