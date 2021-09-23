package ca.ulaval.glo4003;

import ca.ulaval.glo4003.evulution.api.authorization.AuthorizationFilter;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.customer.CustomerResource;
import ca.ulaval.glo4003.evulution.api.customer.CustomerResourceImpl;
import ca.ulaval.glo4003.evulution.api.customer.validator.DateFormatValidator;
import ca.ulaval.glo4003.evulution.api.login.LoginResource;
import ca.ulaval.glo4003.evulution.api.login.LoginResourceImpl;
import ca.ulaval.glo4003.evulution.api.mappers.InvalidRequestFormatMapper;
import ca.ulaval.glo4003.evulution.api.sale.SaleResource;
import ca.ulaval.glo4003.evulution.api.sale.SaleResourceImpl;
import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.car.CarFactory;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerFactory;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerRepository;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerValidator;
import ca.ulaval.glo4003.evulution.domain.login.LoginValidator;
import ca.ulaval.glo4003.evulution.domain.sale.SaleFactory;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionIdFactory;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import ca.ulaval.glo4003.evulution.http.CORSResponseFilter;
import ca.ulaval.glo4003.evulution.infrastructure.customer.CustomerRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.sale.SaleRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.token.TokenRepositoryInMemory;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.TokenRepository;
import ca.ulaval.glo4003.evulution.service.customer.CustomerAssembler;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import ca.ulaval.glo4003.evulution.service.sale.SaleService;
import ca.ulaval.glo4003.evulution.service.sale.TransactionIdAssembler;
import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public class EvulutionMain {
    public static final String BASE_URI = "http://localhost:8080/";
    public static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    public static void main(String[] args) throws Exception {
        // Setup repositories
        CustomerRepository customerRepository = new CustomerRepositoryInMemory();
        TokenRepository tokenRepository = new TokenRepositoryInMemory();
        SaleRepository saleRepository = new SaleRepositoryInMemory();

        // Setup assemblers
        TokenAssembler tokenAssembler = new TokenAssembler();
        TokenDtoAssembler tokenDtoAssembler = new TokenDtoAssembler();

        // Setup resources (API)
        CustomerResource customerResource = createAccountResource(customerRepository);
        LoginResource loginResource = createLoginResource(customerRepository, tokenRepository, tokenAssembler);
        SaleResource saleResource = createSaleResource(saleRepository, tokenRepository, tokenAssembler,
                tokenDtoAssembler);

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
        config.register(createAuthorizationFilter(tokenRepository, tokenAssembler, tokenDtoAssembler));
        config.register(new InvalidRequestFormatMapper());
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

    private static CustomerResource createAccountResource(CustomerRepository customerRepository) {
        CustomerFactory customerFactory = new CustomerFactory();
        CustomerAssembler customerAssembler = new CustomerAssembler(customerFactory);
        CustomerValidator customerValidator = new CustomerValidator(customerRepository);
        DateFormatValidator dateFormatValidator = new DateFormatValidator(DATE_REGEX);
        CustomerService customerService = new CustomerService(customerRepository, customerAssembler, customerValidator);

        return new CustomerResourceImpl(customerService, dateFormatValidator);

    }

    private static LoginResource createLoginResource(CustomerRepository customerRepository,
            TokenRepository tokenRepository, TokenAssembler tokenAssembler) {
        TokenFactory tokenFactory = new TokenFactory();
        LoginValidator loginValidator = new LoginValidator();
        LoginService loginService = new LoginService(tokenFactory, tokenRepository, tokenAssembler, customerRepository,
                loginValidator);

        return new LoginResourceImpl(loginService);
    }

    private static SaleResource createSaleResource(SaleRepository saleRepository, TokenRepository tokenRepository,
            TokenAssembler tokenAssembler, TokenDtoAssembler tokenDtoAssembler) {
        TransactionIdFactory transactionIdFactory = new TransactionIdFactory();
        SaleFactory saleFactory = new SaleFactory(transactionIdFactory);
        CarFactory carFactory = new CarFactory();
        BatteryFactory batteryFactory = new BatteryFactory();
        TransactionIdAssembler transactionIdAssembler = new TransactionIdAssembler();
        SaleService saleService = new SaleService(saleFactory, saleRepository, tokenRepository, tokenAssembler,
                transactionIdAssembler, transactionIdFactory, carFactory, batteryFactory);

        return new SaleResourceImpl(saleService, tokenDtoAssembler);
    }

    private static AuthorizationFilter createAuthorizationFilter(TokenRepository tokenRepository,
            TokenAssembler tokenAssembler, TokenDtoAssembler tokenDtoAssembler) {
        AuthorizationService authorizationService = new AuthorizationService(tokenAssembler, tokenRepository);

        return new AuthorizationFilter(authorizationService, tokenDtoAssembler);
    }
}
