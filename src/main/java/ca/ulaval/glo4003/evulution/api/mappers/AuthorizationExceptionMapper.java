package ca.ulaval.glo4003.evulution.api.mappers;

import ca.ulaval.glo4003.evulution.api.mappers.mapping.HTTPExceptionMapping;
import ca.ulaval.glo4003.evulution.service.authorization.exceptions.ServiceUnauthorizedException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;

import java.util.HashMap;

public class AuthorizationExceptionMapper implements HTTPExceptionMapper {
    private static HTTPExceptionMapping notFound = new HTTPExceptionMapping(404, "Not found");
    private static HTTPExceptionMapping badInputParameter = new HTTPExceptionMapping(400, "bad input parameter");

    private static HashMap<Object, HTTPExceptionMapping> map = new HashMap() {
        {
            put(ServiceBadInputParameterException.class, badInputParameter);
            put(ServiceUnauthorizedException.class, notFound);
        }
    };

    @Override
    public HTTPExceptionMapping map(Object genericExceptionClass) {
        return map.get(genericExceptionClass);
    }
}
