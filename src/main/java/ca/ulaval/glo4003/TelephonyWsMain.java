package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ws.service.customer.CustomerAssembler;
import ca.ulaval.glo4003.ws.api.customer.CustomerResource;
import ca.ulaval.glo4003.ws.api.customer.CustomerResourceImpl;
import ca.ulaval.glo4003.ws.api.calllog.CallLogResource;
import ca.ulaval.glo4003.ws.api.calllog.CallLogResourceImpl;
import ca.ulaval.glo4003.ws.api.contact.ContactResource;
import ca.ulaval.glo4003.ws.api.contact.ContactResourceImpl;
import ca.ulaval.glo4003.ws.api.login.LoginResource;
import ca.ulaval.glo4003.ws.api.login.LoginResourceImpl;
import ca.ulaval.glo4003.ws.domain.customer.*;
import ca.ulaval.glo4003.ws.service.login.TokenAssembler;
import ca.ulaval.glo4003.ws.domain.token.TokenFactory;
import ca.ulaval.glo4003.ws.domain.calllog.CallLog;
import ca.ulaval.glo4003.ws.domain.calllog.CallLogAssembler;
import ca.ulaval.glo4003.ws.domain.calllog.CallLogRepository;
import ca.ulaval.glo4003.ws.domain.calllog.CallLogService;
import ca.ulaval.glo4003.ws.domain.contact.Contact;
import ca.ulaval.glo4003.ws.domain.contact.ContactAssembler;
import ca.ulaval.glo4003.ws.domain.contact.ContactRepository;
import ca.ulaval.glo4003.ws.domain.contact.ContactService;
import ca.ulaval.glo4003.ws.domain.date.DateFormat;
import ca.ulaval.glo4003.ws.domain.login.LoginValidator;
import ca.ulaval.glo4003.ws.http.CORSResponseFilter;
import ca.ulaval.glo4003.ws.infrastructure.customer.CustomerRepositoryInMemory;
import ca.ulaval.glo4003.ws.infrastructure.calllog.CallLogDevDataFactory;
import ca.ulaval.glo4003.ws.infrastructure.calllog.CallLogRepositoryInMemory;
import ca.ulaval.glo4003.ws.infrastructure.contact.ContactDevDataFactory;
import ca.ulaval.glo4003.ws.infrastructure.contact.ContactRepositoryInMemory;
import ca.ulaval.glo4003.ws.infrastructure.token.TokenRepositoryInMemory;
import ca.ulaval.glo4003.ws.service.customer.CustomerService;
import ca.ulaval.glo4003.ws.service.login.LoginService;
import ca.ulaval.glo4003.ws.service.login.TokenRepository;
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
public class TelephonyWsMain {
    public static boolean isDev = true; // Would be a JVM argument or in a .property file
    public static final String BASE_URI = "http://localhost:8080/";

    public static void main(String[] args) throws Exception {

        // Setup resources (API)
        CustomerRepository customerRepository = new CustomerRepositoryInMemory();
        ContactResource contactResource = createContactResource();
        CallLogResource callLogResource = createCallLogResource();
        CustomerResource customerResource = createAccountResource(customerRepository);
        LoginResource loginResource = createLoginResource(customerRepository);

        final AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(contactResource).to(ContactResource.class);
                bind(callLogResource).to(CallLogResource.class);
                bind(customerResource).to(CustomerResource.class);
                bind(loginResource).to(LoginResource.class);
            }
        };

        final ResourceConfig config = new ResourceConfig();
        config.register(binder);
        config.register(new CORSResponseFilter());
        config.packages("ca.ulaval.glo4003.ws.api");

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

    private static ContactResource createContactResource() {
        // Setup resources' dependencies (DOMAIN + INFRASTRUCTURE)
        ContactRepository contactRepository = new ContactRepositoryInMemory();

        // For development ease
        if (isDev) {
            ContactDevDataFactory contactDevDataFactory = new ContactDevDataFactory();
            List<Contact> contacts = contactDevDataFactory.createMockData();
            contacts.stream().forEach(contactRepository::save);
        }

        ContactAssembler contactAssembler = new ContactAssembler();
        ContactService contactService = new ContactService(contactRepository, contactAssembler);

        return new ContactResourceImpl(contactService);
    }

    private static CallLogResource createCallLogResource() {
        // Setup resources' dependencies (DOMAIN + INFRASTRUCTURE)
        CallLogRepository callLogRepository = new CallLogRepositoryInMemory();

        // For development ease
        if (isDev) {
            CallLogDevDataFactory callLogDevDataFactory = new CallLogDevDataFactory();
            List<CallLog> callLogs = callLogDevDataFactory.createMockData();
            callLogs.stream().forEach(callLogRepository::save);
        }

        CallLogAssembler callLogAssembler = new CallLogAssembler();
        CallLogService callLogService = new CallLogService(callLogRepository, callLogAssembler);

        return new CallLogResourceImpl(callLogService);
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
