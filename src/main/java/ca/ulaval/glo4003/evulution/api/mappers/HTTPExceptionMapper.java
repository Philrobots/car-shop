package ca.ulaval.glo4003.evulution.api.mappers;

import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParameterException;
import ca.ulaval.glo4003.evulution.api.exceptions.InvalidDateFormatException;
import ca.ulaval.glo4003.evulution.api.mappers.mapping.HTTPExceptionMapping;
import ca.ulaval.glo4003.evulution.domain.account.exceptions.CustomerAlreadyExistsException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.BatteryAssemblyException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.CompleteAssemblyException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.VehicleAssemblyException;
import ca.ulaval.glo4003.evulution.domain.car.exceptions.BadCarSpecsException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryModeException;
import ca.ulaval.glo4003.evulution.domain.invoice.exceptions.InvalidInvoiceException;
import ca.ulaval.glo4003.evulution.domain.login.exceptions.NoAccountFoundException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.CarNotChosenBeforeBatteryException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MissingElementsForSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleCompleteException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleNotCompletedException;
import ca.ulaval.glo4003.evulution.domain.token.exceptions.UnauthorizedRequestException;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundFromDeliveryIdException;

import java.util.HashMap;

public class HTTPExceptionMapper {
    private static HTTPExceptionMapping badInputParameter = new HTTPExceptionMapping(400, "bad input parameter");
    private static HTTPExceptionMapping badOrderOfOperations = new HTTPExceptionMapping(400, "bad order of operations");
    private static HTTPExceptionMapping badRequest = new HTTPExceptionMapping(400, "Bad request");
    private static HTTPExceptionMapping customerAlreadyExists = new HTTPExceptionMapping(409,
            "an existing customer already exists");
    private static HTTPExceptionMapping unableToLogin = new HTTPExceptionMapping(400, "Unable to login");
    private static HTTPExceptionMapping unauthorized = new HTTPExceptionMapping(401, "Unauthorized");
    private static HTTPExceptionMapping notFound = new HTTPExceptionMapping(404, "Not found");
    private static HTTPExceptionMapping internalServerError = new HTTPExceptionMapping(500, "Error");

    private static HashMap<Class, HTTPExceptionMapping> map = new HashMap() {
        {
            put(NoAccountFoundException.class, unableToLogin);
            put(CustomerAlreadyExistsException.class, customerAlreadyExists);
            put(BadCarSpecsException.class, badInputParameter);
            put(InvalidDateFormatException.class, badInputParameter);
            put(BadInputParameterException.class, badInputParameter);
            put(InvalidInvoiceException.class, badInputParameter);
            put(UnauthorizedRequestException.class, notFound);
            put(BadDeliveryModeException.class, badInputParameter);
            put(BadDeliveryLocationException.class, badInputParameter);
            put(SaleNotFoundFromDeliveryIdException.class, unauthorized);
            put(CarNotChosenBeforeBatteryException.class, badOrderOfOperations);
            put(MissingElementsForSaleException.class, badOrderOfOperations);
            put(SaleNotCompletedException.class, badOrderOfOperations);
            put(SaleCompleteException.class, badRequest);
            put(EmailException.class, internalServerError);
            put(BatteryAssemblyException.class, internalServerError);
            put(VehicleAssemblyException.class, internalServerError);
            put(CompleteAssemblyException.class, internalServerError);
        }
    };

    public HTTPExceptionMapping map(Class genericExceptionClass) {
        return map.get(genericExceptionClass);
    }
}
