package ca.ulaval.glo4003;

import ca.ulaval.glo4003.evulution.Scheduler;
import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.authorization.SecuredAuthorizationFilter;
import ca.ulaval.glo4003.evulution.api.authorization.SecuredWithDeliveryIdAuthorizationFilter;
import ca.ulaval.glo4003.evulution.api.authorization.SecuredWithTransactionIdAuthorizationFilter;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.customer.CustomerResource;
import ca.ulaval.glo4003.evulution.api.customer.CustomerResourceImpl;
import ca.ulaval.glo4003.evulution.api.delivery.DeliveryResource;
import ca.ulaval.glo4003.evulution.api.delivery.DeliveryResourceImpl;
import ca.ulaval.glo4003.evulution.api.login.LoginResource;
import ca.ulaval.glo4003.evulution.api.login.LoginResourceImpl;
import ca.ulaval.glo4003.evulution.api.mappers.HTTPExceptionMapper;
import ca.ulaval.glo4003.evulution.api.sale.SaleResource;
import ca.ulaval.glo4003.evulution.api.sale.SaleResourceImpl;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.car_manufacture.BasicBatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.car_manufacture.BasicVehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.account.customer.AccountValidator;
import ca.ulaval.glo4003.evulution.domain.account.customer.CustomerFactory;
import ca.ulaval.glo4003.evulution.domain.account.manager.Manager;
import ca.ulaval.glo4003.evulution.domain.assemblyline.*;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediatorImpl;
import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.car.CarFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryDetailsFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.email.EmailSender;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceFactory;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceRepository;
import ca.ulaval.glo4003.evulution.domain.login.LoginValidator;
import ca.ulaval.glo4003.evulution.domain.production.ProductionAssembler;
import ca.ulaval.glo4003.evulution.domain.sale.SaleFactory;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionIdFactory;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import ca.ulaval.glo4003.evulution.http.CORSResponseFilter;
import ca.ulaval.glo4003.evulution.infrastructure.account.AccountRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.BatteryRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.VehicleRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.email.EmailSenderImpl;
import ca.ulaval.glo4003.evulution.infrastructure.invoice.InvoiceRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.mappers.JsonFileMapper;
import ca.ulaval.glo4003.evulution.infrastructure.sale.SaleRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.token.TokenRepositoryInMemory;
import ca.ulaval.glo4003.evulution.service.assemblyLine.AssemblyLineService;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.TokenRepository;
import ca.ulaval.glo4003.evulution.service.customer.CustomerAssembler;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import ca.ulaval.glo4003.evulution.service.delivery.DeliveryCompleteAssembler;
import ca.ulaval.glo4003.evulution.service.delivery.DeliveryService;
import ca.ulaval.glo4003.evulution.service.invoice.InvoiceService;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import ca.ulaval.glo4003.evulution.service.sale.EstimatedRangeAssembler;
import ca.ulaval.glo4003.evulution.service.sale.SaleService;
import ca.ulaval.glo4003.evulution.service.sale.TransactionIdAssembler;
import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.Map;

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public class EvulutionMain {
    public static final String ENV_WEEK_TO_SECONDS_KEY = "EQUIVALENCE_OF_ONE_WEEK_IN_SECONDS";
    public static final String BASE_URI = "http://localhost:8080/";
    public static final String MANAGER_EMAIL = "catherineleuf@evul.ulaval.ca";
    public static final String MANAGER_PASSWORD = "RoulezVert2021!";
    private static final String EMAIL = "evulution.equipe6@gmail.com";
    private static final String EMAIL_PASSWORD = "architecture6";
    private static final Integer ASSEMBLY_TIME_IN_WEEKS = 1;

    public static Manager Manager = new Manager(MANAGER_EMAIL, MANAGER_PASSWORD);

    public static int equivalenceOfOneWeekInSeconds = 30;

    public static void main(String[] args) throws Exception {
        // Load env
        Map<String, String> env = System.getenv();
        String envVariable = env.get(ENV_WEEK_TO_SECONDS_KEY);
        if (env.get(ENV_WEEK_TO_SECONDS_KEY) != null) {
            equivalenceOfOneWeekInSeconds = Integer.parseInt(envVariable);
        }

        // Setup exception mapping
        HTTPExceptionMapper httpExceptionMapper = new HTTPExceptionMapper();
        ConstraintsValidator constraintsValidator = new ConstraintsValidator();

        // Setup repositories
        InvoiceRepository invoiceRepository = new InvoiceRepositoryInMemory();
        AccountRepository accountRepository = new AccountRepositoryInMemory();
        TokenRepository tokenRepository = new TokenRepositoryInMemory();
        SaleRepository saleRepository = new SaleRepositoryInMemory();
        TransactionIdFactory transactionIdFactory = new TransactionIdFactory();
        DeliveryIdFactory deliveryIdFactory = new DeliveryIdFactory();
        DeliveryFactory deliveryFactory = new DeliveryFactory(ASSEMBLY_TIME_IN_WEEKS, deliveryIdFactory);
        DeliveryDetailsFactory deliveryDetailsFactory = new DeliveryDetailsFactory(
                JsonFileMapper.parseDeliveryLocations());
        accountRepository.addAccount(Manager);

        // Setup email infra
        EmailSender emailSender = new EmailSenderImpl(EMAIL, EMAIL_PASSWORD);
        EmailFactory emailFactory = new EmailFactory();

        // Setup assembly lines
        VehicleAssemblyLineAdapter vehicleAssemblyLineAdapter = new VehicleAssemblyLineAdapter(
                new BasicVehicleAssemblyLine(), JsonFileMapper.parseModels());
        BatteryAssemblyLineAdapter batteryAssemblyLineAdapter = new BatteryAssemblyLineAdapter(
                new BasicBatteryAssemblyLine(), JsonFileMapper.parseBatteries());
        VehicleRepository vehicleRepository = new VehicleRepositoryInMemory();
        BatteryRepository batteryRepository = new BatteryRepositoryInMemory();
        BatteryAssemblyLine batteryAssemblyLine = new BatteryAssemblyLine(batteryAssemblyLineAdapter,
                batteryRepository);
        VehicleAssemblyLine vehicleAssemblyLine = new VehicleAssemblyLine(vehicleAssemblyLineAdapter,
                vehicleRepository);
        CompleteCarAssemblyLine completeCarAssemblyLine = new CompleteCarAssemblyLine(emailFactory, emailSender,
                vehicleRepository, batteryRepository);
        AssemblyLineMediator assemblyLineMediator = new AssemblyLineMediatorImpl(batteryAssemblyLine,
                completeCarAssemblyLine);
        vehicleAssemblyLine.setMediator(assemblyLineMediator);
        batteryAssemblyLine.setMediator(assemblyLineMediator);

        // Setup services
        AssemblyLineService assemblyLineService = new AssemblyLineService(vehicleAssemblyLine, batteryAssemblyLine,
                completeCarAssemblyLine, new ProductionAssembler());
        InvoiceService invoiceService = new InvoiceService(invoiceRepository, saleRepository);

        // Setup scheduler
        Scheduler scheduler = new Scheduler(equivalenceOfOneWeekInSeconds, assemblyLineService, invoiceService);

        // Setup assemblers
        HTTPExceptionResponseAssembler httpExceptionResponseAssembler = new HTTPExceptionResponseAssembler(
                httpExceptionMapper);
        TokenAssembler tokenAssembler = new TokenAssembler();
        TokenDtoAssembler tokenDtoAssembler = new TokenDtoAssembler();

        // Setup resources (API)
        CustomerResource customerResource = createAccountResource(httpExceptionResponseAssembler, constraintsValidator,
                accountRepository);
        LoginResource loginResource = createLoginResource(accountRepository, tokenRepository, tokenAssembler,
                httpExceptionResponseAssembler, constraintsValidator);
        SaleResource saleResource = createSaleResource(saleRepository, tokenRepository, invoiceRepository,
                tokenAssembler, tokenDtoAssembler, httpExceptionResponseAssembler, constraintsValidator,
                transactionIdFactory, deliveryFactory, assemblyLineService);
        DeliveryResource deliveryResource = createDeliveryResource(constraintsValidator, httpExceptionResponseAssembler,
                saleRepository, invoiceService, deliveryIdFactory, deliveryDetailsFactory);

        final AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(customerResource).to(CustomerResource.class);
                bind(loginResource).to(LoginResource.class);
                bind(saleResource).to(SaleResource.class);
                bind(deliveryResource).to(DeliveryResource.class);
            }
        };

        final ResourceConfig config = new ResourceConfig();
        AuthorizationService authorizationService = new AuthorizationService(tokenAssembler, tokenRepository,
                saleRepository, transactionIdFactory, deliveryIdFactory);
        config.register(binder);
        config.register(new CORSResponseFilter());
        config.register(createSecuredAuthorizationFilter(authorizationService, tokenDtoAssembler,
                httpExceptionResponseAssembler));
        config.register(createSecuredWithTransactionIdAuthorizationFilter(authorizationService, tokenDtoAssembler,
                httpExceptionResponseAssembler));
        config.register(createSecuredWithDeliveryIdAuthorizationFilter(authorizationService, tokenDtoAssembler,
                httpExceptionResponseAssembler));
        config.packages("ca.ulaval.glo4003.evulution.api");

        try {
            // Setup http server
            final Server server = JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);
            scheduler.start();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println("Shutting down the application...");
                    server.stop();
                    System.out.println("Done, exit.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));

            System.out.println(String.format("Application started.%nStop the application using CTRL+C"));

            // block and wait shut down signal, like CTRL+C
            Thread.currentThread().join();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

    private static SecuredWithDeliveryIdAuthorizationFilter createSecuredWithDeliveryIdAuthorizationFilter(
            AuthorizationService authorizationService, TokenDtoAssembler tokenDtoAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler) {
        return new SecuredWithDeliveryIdAuthorizationFilter(authorizationService, tokenDtoAssembler,
                httpExceptionResponseAssembler);
    }

    private static SecuredWithTransactionIdAuthorizationFilter createSecuredWithTransactionIdAuthorizationFilter(
            AuthorizationService authorizationService, TokenDtoAssembler tokenDtoAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler) {
        return new SecuredWithTransactionIdAuthorizationFilter(authorizationService, tokenDtoAssembler,
                httpExceptionResponseAssembler);
    }

    private static CustomerResource createAccountResource(HTTPExceptionResponseAssembler httpExceptionResponseAssembler,
            ConstraintsValidator constraintsValidator, AccountRepository accountRepository) {
        CustomerFactory customerFactory = new CustomerFactory();
        CustomerAssembler customerAssembler = new CustomerAssembler(customerFactory);
        AccountValidator accountValidator = new AccountValidator(accountRepository);
        CustomerService customerService = new CustomerService(accountRepository, customerAssembler, accountValidator);

        return new CustomerResourceImpl(customerService, httpExceptionResponseAssembler, constraintsValidator);

    }

    private static LoginResource createLoginResource(AccountRepository accountRepository,
            TokenRepository tokenRepository, TokenAssembler tokenAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, ConstraintsValidator constraintsValidator) {
        TokenFactory tokenFactory = new TokenFactory();
        LoginValidator loginValidator = new LoginValidator();
        LoginService loginService = new LoginService(tokenFactory, tokenRepository, tokenAssembler, accountRepository,
                loginValidator);

        return new LoginResourceImpl(loginService, httpExceptionResponseAssembler, constraintsValidator);
    }

    private static SaleResource createSaleResource(SaleRepository saleRepository, TokenRepository tokenRepository,
            InvoiceRepository invoiceRepository, TokenAssembler tokenAssembler, TokenDtoAssembler tokenDtoAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, ConstraintsValidator constraintsValidator,
            TransactionIdFactory transactionIdFactory, DeliveryFactory deliveryFactory,
            AssemblyLineService assemblyLineService) {
        TransactionIdAssembler transactionIdAssembler = new TransactionIdAssembler();
        SaleFactory saleFactory = new SaleFactory(transactionIdFactory, deliveryFactory);
        CarFactory carFactory = new CarFactory(JsonFileMapper.parseModels());
        BatteryFactory batteryFactory = new BatteryFactory(JsonFileMapper.parseBatteries());
        InvoiceFactory invoiceFactory = new InvoiceFactory();

        EstimatedRangeAssembler estimatedRangeAssembler = new EstimatedRangeAssembler();

        SaleService saleService = new SaleService(saleRepository, tokenRepository, invoiceRepository, tokenAssembler,
                transactionIdAssembler, saleFactory, transactionIdFactory, carFactory, batteryFactory, invoiceFactory,
                estimatedRangeAssembler, assemblyLineService);

        return new SaleResourceImpl(saleService, tokenDtoAssembler, httpExceptionResponseAssembler,
                constraintsValidator);
    }

    private static DeliveryResource createDeliveryResource(ConstraintsValidator constraintsValidator,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, SaleRepository saleRepository,
            InvoiceService invoiceService, DeliveryIdFactory deliveryIdFactory,
            DeliveryDetailsFactory deliveryDetailsFactory) {
        DeliveryCompleteAssembler deliveryCompleteAssembler = new DeliveryCompleteAssembler();
        DeliveryService deliveryService = new DeliveryService(deliveryIdFactory, deliveryDetailsFactory, saleRepository,
                invoiceService, deliveryCompleteAssembler);

        return new DeliveryResourceImpl(deliveryService, constraintsValidator, httpExceptionResponseAssembler);
    }

    private static SecuredAuthorizationFilter createSecuredAuthorizationFilter(
            AuthorizationService authorizationService, TokenDtoAssembler tokenDtoAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler) {
        return new SecuredAuthorizationFilter(authorizationService, tokenDtoAssembler, httpExceptionResponseAssembler);
    }
}
