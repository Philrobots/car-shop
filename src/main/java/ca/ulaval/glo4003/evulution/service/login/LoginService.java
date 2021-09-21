package ca.ulaval.glo4003.evulution.service.login;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerRepository;
import ca.ulaval.glo4003.evulution.domain.login.LoginValidator;
import ca.ulaval.glo4003.evulution.domain.login.NoAccountFoundException;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.TokenRepository;

public class LoginService {
    private TokenFactory tokenFactory;
    private TokenRepository tokenRepository;
    private TokenAssembler tokenAssembler;
    private CustomerRepository customerRepository;
    private LoginValidator loginValidator;

    public LoginService(TokenFactory tokenFactory, TokenRepository tokenRepository, TokenAssembler tokenAssembler,
            CustomerRepository customerRepository, LoginValidator loginValidator) {
        this.tokenFactory = tokenFactory;
        this.tokenRepository = tokenRepository;
        this.tokenAssembler = tokenAssembler;
        this.customerRepository = customerRepository;
        this.loginValidator = loginValidator;
    }

    public TokenDto loginCustomer(LoginDto loginDto) throws NoAccountFoundException {
        Customer customer = this.customerRepository.getAccountByEmail(loginDto.email);
        this.loginValidator.validateLogin(customer, loginDto);

        Token token = tokenFactory.generateNewToken();

        this.tokenRepository.addTokenWithEmail(token, loginDto.email);
        return this.tokenAssembler.tokenToDto(token);
    }
}
