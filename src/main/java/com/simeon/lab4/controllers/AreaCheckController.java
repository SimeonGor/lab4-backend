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
import jakarta.ws.rs.core.Response;

import java.util.Set;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/check")
@Secured
public class AreaCheckController {
    @EJB
    private AreaCheckService areaCheckService;

    private static final Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response check(AreaCheckRequest request) {
        Set<ConstraintViolation<AreaCheckRequest>> validates = validator.validate(request);

        if (!validates.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(String.join(", ", validates.stream().map(ConstraintViolation::getMessage).toList())))
                    .build();
        }

        AreaCheckResponse areaCheckResponse = areaCheckService.handle(request);

        return Response.ok(areaCheckResponse).build();
    }
}
