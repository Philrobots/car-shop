package ca.ulaval.glo4003.evulution.api.mappers;

import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParametersException;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidRequestFormatMapper implements ExceptionMapper<ValidationException> {
    private static final Integer STATUS_CODE = 400;

    @Override
    public Response toResponse(ValidationException e) {
        return Response.status(STATUS_CODE).entity(new BadInputParametersException()).build();
    }
}
