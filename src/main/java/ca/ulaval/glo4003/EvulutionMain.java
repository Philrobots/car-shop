package ca.ulaval.glo4003;

import ca.ulaval.glo4003.evulution.api.customer.CustomerResource;
import ca.ulaval.glo4003.evulution.api.customer.CustomerResourceImpl;
import ca.ulaval.glo4003.evulution.api.login.LoginResource;
import ca.ulaval.glo4003.evulution.api.login.LoginResourceImpl;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerFactory;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerRepository;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerValidator;
import ca.ulaval.glo4003.evulution.domain.date.DateFormat;
import ca.ulaval.glo4003.evulution.domain.login.LoginValidator;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import ca.ulaval.glo4003.evulution.http.CORSResponseFilter;
import ca.ulaval.glo4003.evulution.infrastructure.customer.CustomerRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.token.TokenRepositoryInMemory;
import ca.ulaval.glo4003.evulution.service.customer.CustomerAssembler;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import ca.ulaval.glo4003.evulution.service.login.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.login.TokenRepository;
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

    public static void main(String[] args) throws Exception {

        // Setup resources (API)
        CustomerRepository customerRepository = new CustomerRepositoryInMemory();
        CustomerResource customerResource = createAccountResource(customerRepository);
        LoginResource loginResource = createLoginResource(customerRepository);

        final AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(customerResource).to(CustomerResource.class);
                bind(loginResource).to(LoginResource.class);
            }
        };

        final ResourceConfig config = new ResourceConfig();
        config.register(binder);
        config.register(new CORSResponseFilter());
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
        DateFormat dateFormat = new DateFormat();
        CustomerAssembler customerAssembler = new CustomerAssembler(customerFactory, dateFormat);
        CustomerValidator customerValidator = new CustomerValidator(customerRepository);
        CustomerService customerService = new CustomerService(customerRepository, customerAssembler, customerValidator);

        return new CustomerResourceImpl(customerService);

    }

    private static LoginResource createLoginResource(CustomerRepository customerRepository) {
        TokenFactory tokenFactory = new TokenFactory();
        TokenRepository tokenRepository = new TokenRepositoryInMemory(tokenFactory);
        TokenAssembler tokenAssembler = new TokenAssembler();
        LoginValidator loginValidator = new LoginValidator();
        LoginService loginService = new LoginService(tokenRepository, tokenAssembler, customerRepository,
                loginValidator);

        return new LoginResourceImpl(loginService);
    }
}
