package ca.ulaval.glo4003.evulution.api.customer;

import ca.ulaval.glo4003.evulution.api.customer.assemblers.CustomerDtoAssembler;
import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerRequest;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import ca.ulaval.glo4003.evulution.service.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.service.exceptions.GenericException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers")
public class CustomerResource {
    private final CustomerService customerService;
    private final HTTPExceptionResponseAssembler httpExceptionResponseAssembler;
    private final CustomerDtoAssembler customerDtoAssembler;
    private final ConstraintsValidator constraintsValidator;

    public CustomerResource(CustomerService customerService,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, CustomerDtoAssembler customerDtoAssembler,
            ConstraintsValidator constraintsValidator) {
        this.customerService = customerService;
        this.httpExceptionResponseAssembler = httpExceptionResponseAssembler;
        this.customerDtoAssembler = customerDtoAssembler;
        this.constraintsValidator = constraintsValidator;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCustomer(CustomerRequest customerRequest) {
        try {
            this.constraintsValidator.validate(customerRequest);

            CustomerDto customerDto = this.customerDtoAssembler.fromRequest(customerRequest);
            this.customerService.addCustomer(customerDto);

            return Response.status(201, "Customer created").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }
}
