package ca.ulaval.glo4003.evulution.service.customer;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerFactory;
import ca.ulaval.glo4003.evulution.domain.customer.Gender;
import ca.ulaval.glo4003.evulution.domain.customer.GenderFactory;

public class CustomerAssembler {
    private CustomerFactory customerFactory;
    private GenderFactory genderFactory;

    public CustomerAssembler(CustomerFactory customerFactory, GenderFactory genderFactory) {
        this.customerFactory = customerFactory;
        this.genderFactory = genderFactory;
    }

    public Customer DtoToCustomer(CustomerDto customerDto) {
        Gender customerGender = genderFactory.create(customerDto.sex);
        return this.customerFactory.create(customerDto.name, customerDto.birthdate, customerDto.email,
                customerDto.password, customerGender);
    }

    public CustomerDto CustomerToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.name = customer.getName();
        customerDto.email = customer.getEmail();
        customerDto.birthdate = customer.getBirthDate().toString();
        customerDto.password = customer.getPassword();
        customerDto.sex = genderFactory.genderDto(customer.getGender());
        return customerDto;
    }

}
