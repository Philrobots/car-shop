package ca.ulaval.glo4003.evulution.service.login;

import ca.ulaval.glo4003.evulution.api.customer.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerRepository;
import ca.ulaval.glo4003.evulution.domain.login.LoginValidator;
import ca.ulaval.glo4003.evulution.domain.login.NoAccountFoundException;
import ca.ulaval.glo4003.evulution.domain.token.Token;

public class LoginService {

    private TokenRepository tokenRepository;
    private TokenAssembler tokenAssembler;
    private CustomerRepository customerRepository;
    private LoginValidator loginValidator;

    public LoginService(TokenRepository tokenRepository, TokenAssembler tokenAssembler,
            CustomerRepository customerRepository, LoginValidator loginValidator) {
        this.tokenRepository = tokenRepository;
        this.tokenAssembler = tokenAssembler;
        this.customerRepository = customerRepository;
        this.loginValidator = loginValidator;
    }

    public TokenDto loginCustomer(LoginDto loginDto) throws NoAccountFoundException {
        Customer customer = this.customerRepository.getAccountByEmail(loginDto.email);
        this.loginValidator.validateLogin(customer, loginDto);
        Token token = this.tokenRepository.loginCustomer(loginDto.email);
        return this.tokenAssembler.toDto(token);
    }
}
