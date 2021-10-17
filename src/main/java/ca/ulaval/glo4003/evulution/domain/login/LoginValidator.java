package ca.ulaval.glo4003.evulution.domain.login;

import ca.ulaval.glo4003.evulution.api.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.login.exceptions.NoAccountFoundException;

public class LoginValidator {

    public void validateLogin(Account account, LoginDto loginDto) {
        if (account == null || !account.isAuthenticationValid(loginDto.email, loginDto.password)) {
            throw new NoAccountFoundException();
        }
    }
}
