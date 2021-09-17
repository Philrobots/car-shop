package ca.ulaval.glo4003.ws.domain.login;

import ca.ulaval.glo4003.ws.api.login.dto.LoginDto;
import ca.ulaval.glo4003.ws.domain.customer.Customer;

public class LoginValidator {

    public void validateLogin(Customer customer, LoginDto loginDto) throws NoAccountFoundException {
        if (customer == null || !customer.isRightCustomerInfo(loginDto.email, loginDto.password)) {
            throw new NoAccountFoundException("Unable to login");
        }
    }
}
