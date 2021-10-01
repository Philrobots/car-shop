package ca.ulaval.glo4003.evulution.service.customer;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerFactory;

public class CustomerAssembler {
    private final CustomerFactory customerFactory;

    public CustomerAssembler(CustomerFactory customerFactory) {
        this.customerFactory = customerFactory;
    }

    public Customer DtoToCustomer(CustomerDto customerDto) {
        return this.customerFactory.create(customerDto.name, customerDto.birthdate, customerDto.email,
                customerDto.password, customerDto.sex);
    }
}
