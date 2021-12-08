package ca.ulaval.glo4003.evulution.api.mappers;

import ca.ulaval.glo4003.evulution.api.mappers.mapping.HTTPExceptionMapping;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
import ca.ulaval.glo4003.evulution.service.login.exceptions.ServiceUnableToLoginException;

import java.util.HashMap;

public class LoginExceptionMapper implements HTTPExceptionMapper {
    private static HTTPExceptionMapping unableToLogin = new HTTPExceptionMapping(400, "Unable to login");
    private static HTTPExceptionMapping badInputParameter = new HTTPExceptionMapping(400, "bad input parameter");

    private static HashMap<Object, HTTPExceptionMapping> map = new HashMap() {
        {
            put(ServiceUnableToLoginException.class, unableToLogin);
            put(ServiceBadInputParameterException.class, badInputParameter);
        }
    };

    @Override
    public HTTPExceptionMapping map(Object genericExceptionClass) {
        return map.get(genericExceptionClass);
    }
}
