package ca.ulaval.glo4003.evulution.api.mappers;

import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParameterException;
import ca.ulaval.glo4003.evulution.api.exceptions.InvalidDateFormatException;
import ca.ulaval.glo4003.evulution.api.mappers.mapping.HTTPExceptionMapping;
import ca.ulaval.glo4003.evulution.domain.car.exception.BadCarSpecsException;
import ca.ulaval.glo4003.evulution.domain.customer.exception.CustomerAlreadyExistsException;
import ca.ulaval.glo4003.evulution.domain.delivery.exception.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exception.BadDeliveryModeException;
import ca.ulaval.glo4003.evulution.domain.invoice.exceptions.InvalidInvoiceException;
import ca.ulaval.glo4003.evulution.domain.login.exception.NoAccountFoundException;
import ca.ulaval.glo4003.evulution.domain.sale.exception.CarNotChosenBeforeBatteryException;
import ca.ulaval.glo4003.evulution.domain.sale.exception.MissingElementsForSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exception.SaleCompleteException;
import ca.ulaval.glo4003.evulution.domain.sale.exception.SaleNotCompletedException;
import ca.ulaval.glo4003.evulution.domain.token.exception.UnauthorizedRequestException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exception.SaleNotFoundFromDeliveryIdException;

import java.util.HashMap;

public class HTTPExceptionMapper {
    private static HTTPExceptionMapping badInputParameter = new HTTPExceptionMapping(400, "bad input parameter");
    private static HTTPExceptionMapping badOrderOfOperations = new HTTPExceptionMapping(400, "bad order of operations");
    private static HTTPExceptionMapping customerAlreadyExists = new HTTPExceptionMapping(409,
            "an existing customer already exists");
    private static HTTPExceptionMapping unableToLogin = new HTTPExceptionMapping(400, "Unable to login");
    private static HTTPExceptionMapping unauthorized = new HTTPExceptionMapping(401, "Unauthorized");

    private static HashMap<Class, HTTPExceptionMapping> map = new HashMap() {
        {
            put(NoAccountFoundException.class, unableToLogin);
            put(CustomerAlreadyExistsException.class, customerAlreadyExists);
            put(BadCarSpecsException.class, badInputParameter);
            put(InvalidDateFormatException.class, badInputParameter);
            put(BadInputParameterException.class, badInputParameter);
            put(InvalidInvoiceException.class, badInputParameter);
            put(UnauthorizedRequestException.class, unauthorized);
            put(BadDeliveryModeException.class, badInputParameter);
            put(BadDeliveryLocationException.class, badInputParameter);
            put(SaleNotFoundFromDeliveryIdException.class, unauthorized);
            put(CarNotChosenBeforeBatteryException.class, badOrderOfOperations);
            put(MissingElementsForSaleException.class, badOrderOfOperations);
            put(SaleNotCompletedException.class, badOrderOfOperations);
            put(SaleCompleteException.class, unauthorized);
        }
    };

    public HTTPExceptionMapping map(Class genericExceptionClass) {
        return map.get(genericExceptionClass);
    }
}
