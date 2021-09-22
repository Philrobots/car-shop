package ca.ulaval.glo4003.evulution.api.customer;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.api.customer.validator.DateFormatValidator;
import ca.ulaval.glo4003.evulution.exception.GenericException;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class CustomerResourceImpl implements CustomerResource {
    private final CustomerService customerService;
    private final DateFormatValidator dateFormatValidator;

    public CustomerResourceImpl(CustomerService customerService, DateFormatValidator dateFormatValidator) {
        this.customerService = customerService;
        this.dateFormatValidator = dateFormatValidator;
    }

    @Override
    public List<CustomerDto> getAll() {
        return this.customerService.getCustomers();
    }

    @Override
    public Response addCustomer(@Valid CustomerDto customerDto) {
        try {
            this.dateFormatValidator.validate(customerDto.birthdate);

            this.customerService.addCustomer(customerDto);
            return Response.status(201, "Customer created").build();

        } catch (GenericException e) {
            return Response.status(e.getErrorCode(), e.getErrorMessage()).entity(e.getErrorMessage()).build();
        }
    }
}
