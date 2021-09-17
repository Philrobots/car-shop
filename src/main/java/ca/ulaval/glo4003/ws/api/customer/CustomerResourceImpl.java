package ca.ulaval.glo4003.ws.api.customer;

import ca.ulaval.glo4003.ws.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.ws.domain.customer.exception.CannotCreateCustomerException;
import ca.ulaval.glo4003.ws.service.customer.CustomerService;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class CustomerResourceImpl implements CustomerResource {

    private CustomerService customerService;

    public CustomerResourceImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public List<CustomerDto> getAll() {
        return this.customerService.getCustomers();
    }

    @Override
    public Response addCustomer(CustomerDto customerDto) {
        try {
            this.customerService.addCustomer(customerDto);
            return Response.status(201, "Customer created").build();

        } catch (CannotCreateCustomerException e) {
            return Response.status(e.getStatusCode(), e.getMessage()).entity(e.getMessage()).build();
        }
    }
}
