package ca.ulaval.glo4003.mainResources;

import ca.ulaval.glo4003.evulution.api.mappers.*;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;

public class ExceptionMapperResources {

    private final HTTPExceptionMapper saleExceptionMapper = new SaleExceptionMapper();
    private final HTTPExceptionResponseAssembler saleExceptionAssembler = new HTTPExceptionResponseAssembler(
            saleExceptionMapper);

    private final HTTPExceptionMapper deliveryExceptionMapper = new DeliveryExceptionMapper();
    private final HTTPExceptionResponseAssembler deliveryExceptionAssembler = new HTTPExceptionResponseAssembler(
            deliveryExceptionMapper);

    private final HTTPExceptionMapper customerExceptionMapper = new CustomerExceptionMapper();
    private final HTTPExceptionResponseAssembler customerExceptionAssembler = new HTTPExceptionResponseAssembler(
            customerExceptionMapper);

    private final HTTPExceptionMapper authorizationExceptionMapper = new AuthorizationExceptionMapper();
    private final HTTPExceptionResponseAssembler authorizationExceptionAssembler = new HTTPExceptionResponseAssembler(
            authorizationExceptionMapper);

    private final HTTPExceptionMapper loginExceptionMapper = new LoginExceptionMapper();
    private final HTTPExceptionResponseAssembler loginExceptionAssembler = new HTTPExceptionResponseAssembler(
            loginExceptionMapper);

    private final HTTPExceptionMapper productionExceptionMapper = new ProductionExceptionMapper();
    private final HTTPExceptionResponseAssembler productionExceptionAssembler = new HTTPExceptionResponseAssembler(
            productionExceptionMapper);

    public HTTPExceptionResponseAssembler getSaleExceptionAssembler() {
        return saleExceptionAssembler;
    }

    public HTTPExceptionResponseAssembler getDeliveryExceptionAssembler() {
        return deliveryExceptionAssembler;
    }

    public HTTPExceptionResponseAssembler getCustomerExceptionAssembler() {
        return customerExceptionAssembler;
    }

    public HTTPExceptionResponseAssembler getAuthorizationExceptionAssembler() {
        return authorizationExceptionAssembler;
    }

    public HTTPExceptionResponseAssembler getLoginExceptionAssembler() {
        return loginExceptionAssembler;
    }

    public HTTPExceptionResponseAssembler getProductionExceptionAssembler() {
        return productionExceptionAssembler;
    }

}
