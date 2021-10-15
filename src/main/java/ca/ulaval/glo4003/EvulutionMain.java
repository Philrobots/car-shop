package ca.ulaval.glo4003;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLineFacade;
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
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.admin.Admin;
import ca.ulaval.glo4003.evulution.domain.admin.AdminRepository;
import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.car.CarFactory;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerFactory;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerRepository;
import ca.ulaval.glo4003.evulution.domain.customer.AccountValidator;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceFactory;
import ca.ulaval.glo4003.evulution.domain.login.LoginValidator;
import ca.ulaval.glo4003.evulution.domain.sale.SaleFactory;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionIdFactory;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import ca.ulaval.glo4003.evulution.http.CORSResponseFilter;
import ca.ulaval.glo4003.evulution.infrastructure.account.AccountRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.admin.AdminRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.customer.CustomerRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.mappers.JsonFileMapper;
import ca.ulaval.glo4003.evulution.infrastructure.sale.SaleRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.token.TokenRepositoryInMemory;
import ca.ulaval.glo4003.evulution.service.assemblyLine.VehicleAssemblyLineService;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.TokenRepository;
import ca.ulaval.glo4003.evulution.service.customer.CustomerAssembler;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import ca.ulaval.glo4003.evulution.service.delivery.DeliveryService;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import ca.ulaval.glo4003.evulution.service.sale.EstimatedRangeAssembler;
import ca.ulaval.glo4003.evulution.service.sale.SaleService;
import ca.ulaval.glo4003.evulution.service.sale.TransactionIdAssembler;
import io.github.cdimascio.dotenv.Dotenv;
import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public class EvulutionMain {
    public static final String ENV_WEEK_TO_SECONDS_KEY = "EQUIVALENCE_OF_ONE_WEEK_IN_SECONDS";
    public static final String BASE_URI = "http://localhost:8080/";
    public static String ADMIN_EMAIL = "catherineleuf@evul.ulaval.ca";
    public static String ADMIN_PASSWORD = "RoulezVert2021!";
    public static Admin ADMIN = new Admin(ADMIN_EMAIL, ADMIN_PASSWORD);

    public static int equivalenceOfOneWeekInSeconds = 30;
    public static void main(String[] args) throws Exception {
        // Load env
        Map<String, String> env = System.getenv();
        String envVariable = env.get(ENV_WEEK_TO_SECONDS_KEY);
        if (env.get(ENV_WEEK_TO_SECONDS_KEY) != null){
            equivalenceOfOneWeekInSeconds = Integer.parseInt(envVariable);
        }
        // add to delivery factory in corresponding PR
        List<String> deliveryLocationNameStrings = JsonFileMapper.parseDeliveryLocations();
        // Setup exception mapping
        HTTPExceptionMapper httpExceptionMapper = new HTTPExceptionMapper();
        ConstraintsValidator constraintsValidator = new ConstraintsValidator();

        // Setup repositories
        AccountRepository accountRepository = new AccountRepositoryInMemory();
        CustomerRepository customerRepository = new CustomerRepositoryInMemory(accountRepository);
        TokenRepository tokenRepository = new TokenRepositoryInMemory();
        SaleRepository saleRepository = new SaleRepositoryInMemory();
        AdminRepository adminRepository = new AdminRepositoryInMemory(accountRepository);
        adminRepository.addAdmin(ADMIN);

        // Setup assemblers
        HTTPExceptionResponseAssembler httpExceptionResponseAssembler = new HTTPExceptionResponseAssembler(
                httpExceptionMapper);
        TokenAssembler tokenAssembler = new TokenAssembler();
        TokenDtoAssembler tokenDtoAssembler = new TokenDtoAssembler();
        TransactionIdFactory transactionIdFactory = new TransactionIdFactory();
        DeliveryIdFactory deliveryIdFactory = new DeliveryIdFactory();

        // Setup resources (API)
        CustomerResource customerResource = createAccountResource(customerRepository, httpExceptionResponseAssembler,
                constraintsValidator, accountRepository);
        LoginResource loginResource = createLoginResource(accountRepository, tokenRepository, tokenAssembler,
                httpExceptionResponseAssembler, constraintsValidator);
        SaleResource saleResource = createSaleResource(saleRepository, tokenRepository, customerRepository,
                tokenAssembler, tokenDtoAssembler, httpExceptionResponseAssembler, constraintsValidator,
                transactionIdFactory);
        DeliveryResource deliveryResource = createDeliveryResource(constraintsValidator, httpExceptionResponseAssembler,
                saleRepository, deliveryIdFactory);

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

    private static CustomerResource createAccountResource(CustomerRepository customerRepository,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, ConstraintsValidator constraintsValidator,
            AccountRepository accountRepository) {
        CustomerFactory customerFactory = new CustomerFactory();
        CustomerAssembler customerAssembler = new CustomerAssembler(customerFactory);
        AccountValidator accountValidator = new AccountValidator(accountRepository);
        CustomerService customerService = new CustomerService(customerRepository, customerAssembler, accountValidator);

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
            CustomerRepository customerRepository, TokenAssembler tokenAssembler, TokenDtoAssembler tokenDtoAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, ConstraintsValidator constraintsValidator,
            TransactionIdFactory transactionIdFactory) {
        TransactionIdAssembler transactionIdAssembler = new TransactionIdAssembler();
        DeliveryIdFactory deliveryIdFactory = new DeliveryIdFactory();
        DeliveryFactory deliveryFactory = new DeliveryFactory(deliveryIdFactory,
                JsonFileMapper.parseDeliveryLocations());
        SaleFactory saleFactory = new SaleFactory(transactionIdFactory, deliveryFactory);
        CarFactory carFactory = new CarFactory(JsonFileMapper.parseModels());
        BatteryFactory batteryFactory = new BatteryFactory(JsonFileMapper.parseBatteries());
        InvoiceFactory invoiceFactory = new InvoiceFactory();
        EstimatedRangeAssembler estimatedRangeAssembler = new EstimatedRangeAssembler();
        VehicleAssemblyLineFacade vehicleAssemblyLineFacade = new VehicleAssemblyLineFacade(
                JsonFileMapper.parseModels());
        VehicleAssemblyLine vehicleAssemblyLine = new VehicleAssemblyLine(vehicleAssemblyLineFacade, equivalenceOfOneWeekInSeconds);
        VehicleAssemblyLineService vehicleAssemblyLineService = new VehicleAssemblyLineService(vehicleAssemblyLine);
        SaleService saleService = new SaleService(saleRepository, tokenRepository, customerRepository, tokenAssembler,
                transactionIdAssembler, saleFactory, transactionIdFactory, carFactory, batteryFactory, invoiceFactory,
                estimatedRangeAssembler, vehicleAssemblyLineService);

        return new SaleResourceImpl(saleService, tokenDtoAssembler, httpExceptionResponseAssembler,
                constraintsValidator);
    }

    private static DeliveryResource createDeliveryResource(ConstraintsValidator constraintsValidator,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, SaleRepository saleRepository,
            DeliveryIdFactory deliveryIdFactory) {
        DeliveryService deliveryService = new DeliveryService(deliveryIdFactory, saleRepository);

        return new DeliveryResourceImpl(deliveryService, constraintsValidator, httpExceptionResponseAssembler);
    }

    private static SecuredAuthorizationFilter createSecuredAuthorizationFilter(
            AuthorizationService authorizationService, TokenDtoAssembler tokenDtoAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler) {
        return new SecuredAuthorizationFilter(authorizationService, tokenDtoAssembler, httpExceptionResponseAssembler);
    }
}
