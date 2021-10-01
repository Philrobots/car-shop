package ca.ulaval.glo4003.evulution.api.customer;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.api.validators.DateFormatValidator;
import ca.ulaval.glo4003.evulution.domain.exception.GenericException;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import jakarta.ws.rs.core.Response;

public class CustomerResourceImpl implements CustomerResource {
    private final CustomerService customerService;
    private final DateFormatValidator dateFormatValidator;
    private final HTTPExceptionResponseAssembler httpExceptionResponseAssembler;
    private final ConstraintsValidator constraintsValidator;

    public CustomerResourceImpl(CustomerService customerService, DateFormatValidator dateFormatValidator,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, ConstraintsValidator constraintsValidator) {
        this.customerService = customerService;
        this.dateFormatValidator = dateFormatValidator;
        this.httpExceptionResponseAssembler = httpExceptionResponseAssembler;
        this.constraintsValidator = constraintsValidator;
    }

    @Override
    public Response addCustomer(CustomerDto customerDto) {
        try {
            this.constraintsValidator.validate(customerDto);
            this.dateFormatValidator.validate(customerDto.birthdate);

            this.customerService.addCustomer(customerDto);
            return Response.status(201, "Customer created").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }
}
