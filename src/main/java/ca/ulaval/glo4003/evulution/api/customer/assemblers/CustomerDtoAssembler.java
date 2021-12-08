package ca.ulaval.glo4003.evulution.api.customer.assemblers;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerRequest;
import ca.ulaval.glo4003.evulution.service.customer.dto.CustomerDto;

public class CustomerDtoAssembler {
    public CustomerDto fromRequest(CustomerRequest customerRequest) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.name = customerRequest.name;
        customerDto.email = customerRequest.email;
        customerDto.birthdate = customerRequest.birthdate;
        customerDto.password = customerRequest.password;
        customerDto.sex = customerRequest.sex;
        return customerDto;
    }
}
