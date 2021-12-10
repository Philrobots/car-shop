package ca.ulaval.glo4003;

import ca.ulaval.glo4003.evulution.Scheduler;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.domain.account.AccountValidator;
import ca.ulaval.glo4003.evulution.domain.account.manager.Manager;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryValidator;
import ca.ulaval.glo4003.evulution.domain.email.EmailSender;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoicePayment;
import ca.ulaval.glo4003.evulution.domain.sale.SaleValidator;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountAlreadyExistsException;
import ca.ulaval.glo4003.evulution.infrastructure.email.EmailSenderImpl;
import ca.ulaval.glo4003.mainResources.*;
import org.eclipse.jetty.server.Server;
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

    public static Manager manager = new Manager(MANAGER_EMAIL, MANAGER_PASSWORD);

    public static int equivalenceOfOneWeekInSeconds = 5;

    public static void main(String[] args) throws Exception {
        // Load env
        Map<String, String> env = System.getenv();
        String envVariable = env.get(ENV_WEEK_TO_SECONDS_KEY);
        if (env.get(ENV_WEEK_TO_SECONDS_KEY) != null) {
            equivalenceOfOneWeekInSeconds = Integer.parseInt(envVariable);
        }

        ConstraintsValidator constraintsValidator = new ConstraintsValidator();

        // Setup email infra -----------------------------------------------------------------------------------------
        EmailSender emailSender = new EmailSenderImpl(EMAIL, EMAIL_PASSWORD);

        // EXCEPTION MAPPERS -----------------------------------------------------------------------------------------
        ExceptionMapperResources exceptionMapperResources = new ExceptionMapperResources();

        // REPOSITORIES ----------------------------------------------------------------------------------------------
        RepositoryResources repositoryResources = new RepositoryResources();

        // ACCOUNT VALIDATOR -----------------------------------------------------------------------------------------
        AccountValidator accountValidator = new AccountValidator(repositoryResources.getAccountRepository());

        // INVOICE ---------------------------------------------------------------------------------------------------
        InvoicePayment invoicePayment = new InvoicePayment(repositoryResources.getSaleRepository());

        // FACTORIES -------------------------------------------------------------------------------------------------
        FactoryResources factoryResources = new FactoryResources(ASSEMBLY_TIME_IN_WEEKS, emailSender,
                repositoryResources.getTokenRepository());

        // VALIDATORS ------------------------------------------------------------------------------------------------
        DeliveryValidator deliveryValidator = new DeliveryValidator(repositoryResources.getDeliveryRepository());
        SaleValidator saleValidator = new SaleValidator(repositoryResources.getSaleRepository(),
                repositoryResources.getDeliveryRepository(), factoryResources.getSaleIdFactory());

        // ASSEMBLERS ------------------------------------------------------------------------------------------------
        AssemblerResources assemblerResources = new AssemblerResources();

        // ASSEMBLY LINES --------------------------------------------------------------------------------------------
        AssemblyLineResources assemblyLineResources = new AssemblyLineResources(factoryResources, repositoryResources);

        // SALE DOMAIN SERVICE ---------------------------------------------------------------------------------------
        SaleDomainServiceResources saleDomainServiceResources = new SaleDomainServiceResources(factoryResources,
                repositoryResources, invoicePayment);

        // PRODUCTION LINE -------------------------------------------------------------------------------------------
        ProductionLineResources productionLineResources = new ProductionLineResources(factoryResources,
                repositoryResources, saleDomainServiceResources, assemblyLineResources);

        // SCHEDULER -------------------------------------------------------------------------------------------------
        Scheduler scheduler = new Scheduler(equivalenceOfOneWeekInSeconds, productionLineResources.getProductionLine(),
                invoicePayment);

        // SERVICES --------------------------------------------------------------------------------------------------
        ServiceResources serviceResources = new ServiceResources(factoryResources, repositoryResources,
                productionLineResources, assemblerResources, saleDomainServiceResources, saleValidator);

        // RESOURCES API ---------------------------------------------------------------------------------------------
        ApiResources apiResources = new ApiResources(factoryResources, repositoryResources, assemblerResources,
                exceptionMapperResources, serviceResources, constraintsValidator, accountValidator, deliveryValidator,
                saleValidator);

        ResourceConfig config = apiResources.getConfigurations();
        config.packages("ca.ulaval.glo4003.evulution.api");

        try {
            // Setup http server
            final Server server = JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);
            repositoryResources.getAccountRepository().addAccount(manager);
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

        } catch (InterruptedException | AccountAlreadyExistsException ex) {
            ex.printStackTrace();
        }

    }

}
