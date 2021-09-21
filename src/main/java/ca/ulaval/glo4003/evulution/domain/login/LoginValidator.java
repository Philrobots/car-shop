package ca.ulaval.glo4003.evulution.domain.login;

import ca.ulaval.glo4003.evulution.api.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.domain.customer.Customer;

public class LoginValidator {

    public void validateLogin(Customer customer, LoginDto loginDto) throws NoAccountFoundException {
        if (customer == null || !customer.isAuthenticationValid(loginDto.email, loginDto.password)) {
            throw new NoAccountFoundException("Unable to login");
        }
    }
}
