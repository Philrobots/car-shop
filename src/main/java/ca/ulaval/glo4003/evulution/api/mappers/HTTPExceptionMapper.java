package ca.ulaval.glo4003.evulution.api.mappers;

import ca.ulaval.glo4003.evulution.api.customer.exception.InvalidDateFormatException;
import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParametersException;
import ca.ulaval.glo4003.evulution.api.mappers.mapping.HTTPExceptionMapping;
import ca.ulaval.glo4003.evulution.domain.car.exception.BadCarSpecsException;
import ca.ulaval.glo4003.evulution.domain.customer.exception.CustomerAlreadyExistsException;
import ca.ulaval.glo4003.evulution.domain.exception.GenericException;
import ca.ulaval.glo4003.evulution.domain.login.exception.NoAccountFoundException;
import ca.ulaval.glo4003.evulution.domain.token.exception.UnauthorizedRequestException;

import java.util.HashMap;

public class HTTPExceptionMapper {
    private static HTTPExceptionMapping badInputParameter = new HTTPExceptionMapping(400, "bad input parameter");
    private static HTTPExceptionMapping customerAlreadyExists = new HTTPExceptionMapping(409, "customer already exists");
    private static HTTPExceptionMapping unableToLogin = new HTTPExceptionMapping(400, "Unable to login");
    // pas sur de celle la, cest pas spécifié dans la spec api
    private static HTTPExceptionMapping unauthorized = new HTTPExceptionMapping(401, "Unauthorized");

    private static HashMap<Class, HTTPExceptionMapping> map = new HashMap() {{
        put(NoAccountFoundException.class, unableToLogin);
        put(CustomerAlreadyExistsException.class, customerAlreadyExists);
        put(BadCarSpecsException.class, badInputParameter);
        put(InvalidDateFormatException.class, badInputParameter);
        put(BadInputParametersException.class, badInputParameter);
        put(UnauthorizedRequestException.class, unauthorized);
    }};

    public HTTPExceptionMapping map(Class genericExceptionClass){
        return map.get(genericExceptionClass);
    }
}
