package ca.ulaval.glo4003;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.authorization.AuthorizationFilter;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.customer.CustomerResource;
import ca.ulaval.glo4003.evulution.api.customer.CustomerResourceImpl;
import ca.ulaval.glo4003.evulution.api.login.LoginResource;
import ca.ulaval.glo4003.evulution.api.login.LoginResourceImpl;
import ca.ulaval.glo4003.evulution.api.mappers.HTTPExceptionMapper;
import ca.ulaval.glo4003.evulution.api.sale.SaleResource;
import ca.ulaval.glo4003.evulution.api.sale.SaleResourceImpl;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.api.validators.DateFormatValidator;
import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.car.CarFactory;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerFactory;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerRepository;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerValidator;
import ca.ulaval.glo4003.evulution.domain.customer.GenderFactory;
import ca.ulaval.glo4003.evulution.domain.login.LoginValidator;
import ca.ulaval.glo4003.evulution.domain.sale.SaleFactory;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionIdFactory;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import ca.ulaval.glo4003.evulution.http.CORSResponseFilter;
import ca.ulaval.glo4003.evulution.infrastructure.customer.CustomerRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.mappers.JsonFileMapper;
import ca.ulaval.glo4003.evulution.infrastructure.sale.SaleRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.token.TokenRepositoryInMemory;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.TokenRepository;
import ca.ulaval.glo4003.evulution.service.customer.CustomerAssembler;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import ca.ulaval.glo4003.evulution.service.sale.EstimatedRangeAssembler;
import ca.ulaval.glo4003.evulution.service.sale.SaleService;
import ca.ulaval.glo4003.evulution.service.sale.TransactionIdAssembler;
import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.List;

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public class EvulutionMain {
    public static final String BASE_URI = "http://localhost:8080/";
    public static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    public static void main(String[] args) throws Exception {
        // add to delivery factory in corresponding PR
        List<String> deliveryLocationNameStrings = JsonFileMapper.parseDeliveryLocations();

        // Setup exception mapping
        HTTPExceptionMapper httpExceptionMapper = new HTTPExceptionMapper();
        ConstraintsValidator constraintsValidator = new ConstraintsValidator();

        // Setup repositories
        CustomerRepository customerRepository = new CustomerRepositoryInMemory();
        TokenRepository tokenRepository = new TokenRepositoryInMemory();
        SaleRepository saleRepository = new SaleRepositoryInMemory();

        // Setup assemblers
        HTTPExceptionResponseAssembler httpExceptionResponseAssembler = new HTTPExceptionResponseAssembler(
                httpExceptionMapper);
        TokenAssembler tokenAssembler = new TokenAssembler();
        TokenDtoAssembler tokenDtoAssembler = new TokenDtoAssembler();

        // Setup resources (API)
        CustomerResource customerResource = createAccountResource(customerRepository, httpExceptionResponseAssembler,
                constraintsValidator);
        LoginResource loginResource = createLoginResource(customerRepository, tokenRepository, tokenAssembler,
                httpExceptionResponseAssembler, constraintsValidator);
        SaleResource saleResource = createSaleResource(saleRepository, tokenRepository, tokenAssembler,
                tokenDtoAssembler, httpExceptionResponseAssembler, constraintsValidator);

        final AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(customerResource).to(CustomerResource.class);
                bind(loginResource).to(LoginResource.class);
                bind(saleResource).to(SaleResource.class);
            }
        };

        final ResourceConfig config = new ResourceConfig();
        config.register(binder);
        config.register(new CORSResponseFilter());
        config.register(createAuthorizationFilter(tokenRepository, tokenAssembler, tokenDtoAssembler,
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

    private static CustomerResource createAccountResource(CustomerRepository customerRepository,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, ConstraintsValidator constraintsValidator) {
        CustomerFactory customerFactory = new CustomerFactory();
        GenderFactory genderFactory = new GenderFactory();
        CustomerAssembler customerAssembler = new CustomerAssembler(customerFactory, genderFactory);
        CustomerValidator customerValidator = new CustomerValidator(customerRepository);
        DateFormatValidator dateFormatValidator = new DateFormatValidator(DATE_REGEX);
        CustomerService customerService = new CustomerService(customerRepository, customerAssembler, customerValidator);

        return new CustomerResourceImpl(customerService, dateFormatValidator, httpExceptionResponseAssembler,
                constraintsValidator);

    }

    private static LoginResource createLoginResource(CustomerRepository customerRepository,
            TokenRepository tokenRepository, TokenAssembler tokenAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, ConstraintsValidator constraintsValidator) {
        TokenFactory tokenFactory = new TokenFactory();
        LoginValidator loginValidator = new LoginValidator();
        LoginService loginService = new LoginService(tokenFactory, tokenRepository, tokenAssembler, customerRepository,
                loginValidator);

        return new LoginResourceImpl(loginService, httpExceptionResponseAssembler, constraintsValidator);
    }

    private static SaleResource createSaleResource(SaleRepository saleRepository, TokenRepository tokenRepository,
            TokenAssembler tokenAssembler, TokenDtoAssembler tokenDtoAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, ConstraintsValidator constraintsValidator) {
        TransactionIdFactory transactionIdFactory = new TransactionIdFactory();
        SaleFactory saleFactory = new SaleFactory(transactionIdFactory);
        CarFactory carFactory = new CarFactory(JsonFileMapper.parseModels());
        BatteryFactory batteryFactory = new BatteryFactory(JsonFileMapper.parseBatteries());
        TransactionIdAssembler transactionIdAssembler = new TransactionIdAssembler();
        EstimatedRangeAssembler estimatedRangeAssembler = new EstimatedRangeAssembler();
        SaleService saleService = new SaleService(saleFactory, saleRepository, tokenRepository, tokenAssembler,
                transactionIdAssembler, transactionIdFactory, carFactory, batteryFactory, estimatedRangeAssembler);

        return new SaleResourceImpl(saleService, tokenDtoAssembler, httpExceptionResponseAssembler,
                constraintsValidator);
    }

    private static AuthorizationFilter createAuthorizationFilter(TokenRepository tokenRepository,
            TokenAssembler tokenAssembler, TokenDtoAssembler tokenDtoAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler) {
        AuthorizationService authorizationService = new AuthorizationService(tokenAssembler, tokenRepository);

        return new AuthorizationFilter(authorizationService, tokenDtoAssembler, httpExceptionResponseAssembler);
    }
}
