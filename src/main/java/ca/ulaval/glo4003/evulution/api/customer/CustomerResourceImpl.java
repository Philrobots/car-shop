package ca.ulaval.glo4003.evulution.api.customer;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.domain.exception.GenericException;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import jakarta.ws.rs.core.Response;

public class CustomerResourceImpl implements CustomerResource {
    private final CustomerService customerService;
    private final HTTPExceptionResponseAssembler httpExceptionResponseAssembler;
    private final ConstraintsValidator constraintsValidator;

    public CustomerResourceImpl(CustomerService customerService,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, ConstraintsValidator constraintsValidator) {
        this.customerService = customerService;
        this.httpExceptionResponseAssembler = httpExceptionResponseAssembler;
        this.constraintsValidator = constraintsValidator;
    }

    @Override
    public Response addCustomer(CustomerDto customerDto) {
        try {
            this.constraintsValidator.validate(customerDto);

            this.customerService.addCustomer(customerDto);
            return Response.status(201, "Customer created").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }
}
