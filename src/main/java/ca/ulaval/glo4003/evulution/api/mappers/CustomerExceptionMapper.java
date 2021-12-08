package ca.ulaval.glo4003.evulution.api.mappers;

import ca.ulaval.glo4003.evulution.api.mappers.mapping.HTTPExceptionMapping;
import ca.ulaval.glo4003.evulution.service.customer.exceptions.ServiceCustomerAlreadyExistsException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;

import java.util.HashMap;

public class CustomerExceptionMapper implements HTTPExceptionMapper {
    private static HTTPExceptionMapping customerAlreadyExists = new HTTPExceptionMapping(409,
            "an existing customer already exists");
    private static HTTPExceptionMapping badInputParameter = new HTTPExceptionMapping(400, "bad input parameter");

    private static HashMap<Object, HTTPExceptionMapping> map = new HashMap() {
        {
            put(ServiceCustomerAlreadyExistsException.class, customerAlreadyExists);
            put(ServiceBadInputParameterException.class, badInputParameter);
        }
    };

    @Override
    public HTTPExceptionMapping map(Object genericExceptionClass) {
        return map.get(genericExceptionClass);
    }
}
