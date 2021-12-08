package ca.ulaval.glo4003.evulution.api.mappers;

import ca.ulaval.glo4003.evulution.api.mappers.mapping.HTTPExceptionMapping;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadOrderOfOperationsException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadRequestException;

import java.util.HashMap;

public class DeliveryExceptionMapper implements HTTPExceptionMapper {
    private static HTTPExceptionMapping badOrderOfOperations = new HTTPExceptionMapping(400, "bad order of operations");
    private static HTTPExceptionMapping badRequest = new HTTPExceptionMapping(400, "Bad request");
    private static HTTPExceptionMapping badInputParameter = new HTTPExceptionMapping(400, "bad input parameter");

    private static HashMap<Object, HTTPExceptionMapping> map = new HashMap() {
        {
            put(ServiceBadInputParameterException.class, badInputParameter);
            put(ServiceBadRequestException.class, badRequest);
            put(ServiceBadOrderOfOperationsException.class, badOrderOfOperations);
        }
    };

    @Override
    public HTTPExceptionMapping map(Object genericExceptionClass) {
        return map.get(genericExceptionClass);
    }
}
