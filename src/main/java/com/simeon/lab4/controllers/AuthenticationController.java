package com.simeon.lab4.controllers;

import com.simeon.lab4.dto.AuthenticationRequest;
import com.simeon.lab4.dto.AuthenticationResponse;
import com.simeon.lab4.dto.ErrorMessage;
import com.simeon.lab4.ejb.expetions.InvalidAuthenticationException;
import com.simeon.lab4.ejb.expetions.UserExistException;
import com.simeon.lab4.ejb.services.AuthenticationService;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/auth")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class AuthenticationController {
    @EJB
    private AuthenticationService authenticationService;

    @Path("/login")
    @POST
    public Response login(@Valid @NotNull AuthenticationRequest request) {
        try {
            AuthenticationResponse token = authenticationService.login(request.getUsername(), request.getPassword());
            return Response.ok(token).build();
        }
        catch (InvalidAuthenticationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorMessage(e.getMessage())).build();
        }
    }

    @Path("/register")
    @POST
    public Response register(@Valid @NotNull AuthenticationRequest request) {
        try {
            AuthenticationResponse token = authenticationService.register(request.getUsername(), request.getPassword());
            return Response.ok(token).build();
        }
        catch (UserExistException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorMessage(e.getMessage())).build();
        }
    }
}
