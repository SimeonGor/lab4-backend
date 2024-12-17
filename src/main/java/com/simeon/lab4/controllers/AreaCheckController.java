package com.simeon.lab4.controllers;

import com.simeon.lab4.dto.AreaCheckRequest;
import com.simeon.lab4.dto.AreaCheckResponse;
import com.simeon.lab4.dto.ErrorMessage;
import com.simeon.lab4.ejb.services.AreaCheckService;
import com.simeon.lab4.security.Secured;
import jakarta.ejb.EJB;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.Set;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/check")
@Secured
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class AreaCheckController {
    @Context
    private SecurityContext securityContext;

    @EJB
    private AreaCheckService areaCheckService;

    private static final Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @GET
    public List<AreaCheckResponse> getAll() {
        String username = securityContext.getUserPrincipal().getName();
        return areaCheckService.findAllByUser(username);
    }


    @POST
    public Response check(AreaCheckRequest request) {
        Set<ConstraintViolation<AreaCheckRequest>> validates = validator.validate(request);

        if (!validates.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(String.join(", ", validates.stream().map(ConstraintViolation::getMessage).toList())))
                    .build();
        }
        String username = securityContext.getUserPrincipal().getName();
        AreaCheckResponse areaCheckResponse = areaCheckService.handle(request, username);

        return Response.ok(areaCheckResponse).build();
    }
}
