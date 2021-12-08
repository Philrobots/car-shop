package ca.ulaval.glo4003.evulution.api.mappers.assemblers;

import ca.ulaval.glo4003.evulution.api.mappers.HTTPExceptionMapper;
import ca.ulaval.glo4003.evulution.api.mappers.mapping.HTTPExceptionMapping;
import jakarta.ws.rs.core.Response;

public class HTTPExceptionResponseAssembler {
    private HTTPExceptionMapper httpExceptionMapper;

    public HTTPExceptionResponseAssembler(HTTPExceptionMapper httpExceptionMapper) {
        this.httpExceptionMapper = httpExceptionMapper;
    }

    public Response assembleResponseFromExceptionClass(Object genericExceptionClass) {
        HTTPExceptionMapping mapping = httpExceptionMapper.map(genericExceptionClass);
        return Response.status(mapping.getStatusCode(), mapping.getErrorMessage()).entity(mapping.getErrorMessage())
                .build();
    }
}
