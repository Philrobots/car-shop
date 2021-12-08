package ca.ulaval.glo4003.evulution.api.mappers;

import ca.ulaval.glo4003.evulution.api.mappers.mapping.HTTPExceptionMapping;
import ca.ulaval.glo4003.evulution.service.assemblyLine.exceptions.ServiceErrorException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadOrderOfOperationsException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadRequestException;

import java.util.HashMap;

public class ProductionExceptionMapper implements HTTPExceptionMapper {
    private static HTTPExceptionMapping badOrderOfOperations = new HTTPExceptionMapping(400, "bad order of operations");
    private static HTTPExceptionMapping badRequest = new HTTPExceptionMapping(400, "Bad request");
    private static HTTPExceptionMapping error = new HTTPExceptionMapping(500, "Error");
    private static HashMap<Object, HTTPExceptionMapping> map = new HashMap() {
        {
            put(ServiceBadRequestException.class, badRequest);
            put(ServiceBadOrderOfOperationsException.class, badOrderOfOperations);
            put(ServiceErrorException.class, error);
        }
    };

    @Override
    public HTTPExceptionMapping map(Object genericExceptionClass) {
        return map.get(genericExceptionClass);
    }
}
