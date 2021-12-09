package ca.ulaval.glo4003.mainResources;

import ca.ulaval.glo4003.evulution.api.authorization.SecuredAdminAuthorizationFilter;
import ca.ulaval.glo4003.evulution.api.authorization.SecuredAuthorizationFilter;
import ca.ulaval.glo4003.evulution.api.authorization.SecuredWithDeliveryIdAuthorizationFilter;
import ca.ulaval.glo4003.evulution.api.authorization.SecuredWithSaleIdAuthorizationFilter;
import ca.ulaval.glo4003.evulution.api.authorization.assemblers.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.customer.CustomerResource;
import ca.ulaval.glo4003.evulution.api.customer.assemblers.CustomerDtoAssembler;
import ca.ulaval.glo4003.evulution.api.delivery.DeliveryResource;
import ca.ulaval.glo4003.evulution.api.delivery.assemblers.DeliveryCompletedResponseAssembler;
import ca.ulaval.glo4003.evulution.api.delivery.assemblers.DeliveryLocationDtoAssembler;
import ca.ulaval.glo4003.evulution.api.login.LoginResource;
import ca.ulaval.glo4003.evulution.api.login.assembler.LoginDtoAssembler;
import ca.ulaval.glo4003.evulution.api.login.assembler.TokenResponseAssembler;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.productions.ProductionResource;
import ca.ulaval.glo4003.evulution.api.sale.SaleResource;
import ca.ulaval.glo4003.evulution.api.sale.assemblers.*;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.domain.account.AccountValidator;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryValidator;
import ca.ulaval.glo4003.evulution.domain.sale.SaleValidator;
import ca.ulaval.glo4003.evulution.http.CORSResponseFilter;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import ca.ulaval.glo4003.evulution.service.delivery.DeliveryService;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import ca.ulaval.glo4003.evulution.service.manufacture.ManufactureService;
import ca.ulaval.glo4003.evulution.service.sale.SaleService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class ApiResources {
    private final CustomerResource customerResource;
    private final LoginResource loginResource;
    private final SaleResource saleResource;
    private final DeliveryResource deliveryResource;
    private final ExceptionMapperResources exceptionMapperResources;
    private final ServiceResources serviceResources;
    private final AssemblerResources assemblerResources;
    private final RepositoryResources repositoryResources;
    private final FactoryResources factoryResources;
    private final AccountValidator accountValidator;
    private final DeliveryValidator deliveryValidator;
    private final SaleValidator saleValidator;

    public ApiResources(FactoryResources factoryResources, RepositoryResources repositoryResources,
            AssemblerResources assemblerResources, ExceptionMapperResources exceptionMapperResources,
            ServiceResources serviceResources, ConstraintsValidator constraintsValidator,
            AccountValidator accountValidator, DeliveryValidator deliveryValidator, SaleValidator saleValidator) {

        this.exceptionMapperResources = exceptionMapperResources;
        this.serviceResources = serviceResources;
        this.assemblerResources = assemblerResources;
        this.repositoryResources = repositoryResources;
        this.factoryResources = factoryResources;
        this.accountValidator = accountValidator;
        this.deliveryValidator = deliveryValidator;
        this.saleValidator = saleValidator;

        customerResource = createAccountResource(serviceResources.getCustomerService(),
                exceptionMapperResources.getCustomerExceptionAssembler(), assemblerResources.getCustomerDtoAssembler(),
                constraintsValidator);

        loginResource = createLoginResource(serviceResources.getLoginService(),
                exceptionMapperResources.getLoginExceptionAssembler(), assemblerResources.getLoginDtoAssembler(),
                assemblerResources.getTokenResponseAssembler(), constraintsValidator);

        saleResource = createSaleResource(serviceResources.getSaleService(), assemblerResources.getTokenDtoAssembler(),
                exceptionMapperResources.getSaleExceptionAssembler(), assemblerResources.getChooseCarDtoAssembler(),
                assemblerResources.getChooseBatteryDtoAssembler(),
                assemblerResources.getEstimatedRangeResponseAssembler(), assemblerResources.getSaleResponseAssembler(),
                assemblerResources.getInvoiceDtoAssembler(), constraintsValidator,
                serviceResources.getManufactureService());

        deliveryResource = createDeliveryResource(serviceResources.getDeliveryService(), constraintsValidator,
                exceptionMapperResources.getDeliveryExceptionAssembler(),
                assemblerResources.getDeliveryCompletedResponseAssembler(),
                assemblerResources.getDeliveryLocationDtoAssembler());
    }

    private static SecuredWithDeliveryIdAuthorizationFilter createSecuredWithDeliveryIdAuthorizationFilter(
            AuthorizationService authorizationService, TokenDtoAssembler tokenDtoAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler) {
        return new SecuredWithDeliveryIdAuthorizationFilter(authorizationService, tokenDtoAssembler,
                httpExceptionResponseAssembler);
    }

    private static SecuredWithSaleIdAuthorizationFilter createSecuredWithSaleIdAuthorizationFilter(
            AuthorizationService authorizationService, TokenDtoAssembler tokenDtoAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler) {
        return new SecuredWithSaleIdAuthorizationFilter(authorizationService, tokenDtoAssembler,
                httpExceptionResponseAssembler);
    }

    private static CustomerResource createAccountResource(CustomerService customerService,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, CustomerDtoAssembler customerDtoAssembler,
            ConstraintsValidator constraintsValidator) {
        return new CustomerResource(customerService, httpExceptionResponseAssembler, customerDtoAssembler,
                constraintsValidator);

    }

    private static LoginResource createLoginResource(LoginService loginService,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, LoginDtoAssembler loginDtoAssembler,
            TokenResponseAssembler tokenResponseAssembler, ConstraintsValidator constraintsValidator) {
        return new LoginResource(loginService, httpExceptionResponseAssembler, loginDtoAssembler,
                tokenResponseAssembler, constraintsValidator);
    }

    private static SaleResource createSaleResource(SaleService saleService, TokenDtoAssembler tokenDtoAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, ChooseCarDtoAssembler chooseCarDtoAssembler,
            ChooseBatteryDtoAssembler chooseBatteryDtoAssembler,
            EstimatedRangeResponseAssembler estimatedRangeResponseAssembler,
            SaleResponseAssembler saleResponseAssembler, InvoiceDtoAssembler invoiceDtoAssembler,
            ConstraintsValidator constraintsValidator, ManufactureService manufactureService) {
        return new SaleResource(saleService, tokenDtoAssembler, httpExceptionResponseAssembler, chooseCarDtoAssembler,
                chooseBatteryDtoAssembler, estimatedRangeResponseAssembler, saleResponseAssembler, invoiceDtoAssembler,
                constraintsValidator, manufactureService);
    }

    private static DeliveryResource createDeliveryResource(DeliveryService deliveryService,
            ConstraintsValidator constraintsValidator, HTTPExceptionResponseAssembler httpExceptionResponseAssembler,
            DeliveryCompletedResponseAssembler deliveryCompletedResponseAssembler,
            DeliveryLocationDtoAssembler deliveryLocationDtoAssembler) {
        return new DeliveryResource(deliveryService, constraintsValidator, httpExceptionResponseAssembler,
                deliveryCompletedResponseAssembler, deliveryLocationDtoAssembler);
    }

    private static SecuredAuthorizationFilter createSecuredAuthorizationFilter(
            AuthorizationService authorizationService, TokenDtoAssembler tokenDtoAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler) {
        return new SecuredAuthorizationFilter(authorizationService, tokenDtoAssembler, httpExceptionResponseAssembler);
    }

    public ResourceConfig getConfigurations() {

        final AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(customerResource).to(CustomerResource.class);
                bind(loginResource).to(LoginResource.class);
                bind(saleResource).to(SaleResource.class);
                bind(deliveryResource).to(DeliveryResource.class);
                bind(new ProductionResource(exceptionMapperResources.getProductionExceptionAssembler(),
                        serviceResources.getAssemblyLineService())).to(ProductionResource.class);
            }
        };

        final ResourceConfig config = new ResourceConfig();
        AuthorizationService authorizationService = new AuthorizationService(assemblerResources.getTokenAssembler(),
                repositoryResources.getTokenRepository(), factoryResources.getSaleIdFactory(),
                factoryResources.getDeliveryIdFactory(), accountValidator, deliveryValidator, saleValidator);

        config.register(binder);
        config.register(new CORSResponseFilter());

        HTTPExceptionResponseAssembler authorizationExceptionAssembler = exceptionMapperResources
                .getAuthorizationExceptionAssembler();

        config.register(createSecuredAuthorizationFilter(authorizationService,
                assemblerResources.getTokenDtoAssembler(), authorizationExceptionAssembler));
        config.register(createSecuredWithSaleIdAuthorizationFilter(authorizationService,
                assemblerResources.getTokenDtoAssembler(), authorizationExceptionAssembler));
        config.register(createSecuredWithDeliveryIdAuthorizationFilter(authorizationService,
                assemblerResources.getTokenDtoAssembler(), authorizationExceptionAssembler));
        config.register(new SecuredAdminAuthorizationFilter(authorizationService,
                assemblerResources.getTokenDtoAssembler(), authorizationExceptionAssembler));
        return config;
    }
}
