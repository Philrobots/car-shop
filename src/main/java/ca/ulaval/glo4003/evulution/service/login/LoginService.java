package ca.ulaval.glo4003.evulution.service.login;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.login.LoginValidator;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.TokenRepository;

public class LoginService {
    private TokenFactory tokenFactory;
    private TokenRepository tokenRepository;
    private TokenAssembler tokenAssembler;
    private AccountRepository accountRepository;
    private LoginValidator loginValidator;

    public LoginService(TokenFactory tokenFactory, TokenRepository tokenRepository, TokenAssembler tokenAssembler,
            AccountRepository accountRepository, LoginValidator loginValidator) {
        this.tokenFactory = tokenFactory;
        this.tokenRepository = tokenRepository;
        this.tokenAssembler = tokenAssembler;
        this.accountRepository = accountRepository;
        this.loginValidator = loginValidator;
    }

    public TokenDto loginCustomer(LoginDto loginDto) {
        Account account = this.accountRepository.getAccountByEmail(loginDto.email);
        this.loginValidator.validateLogin(account, loginDto);

        Token token = tokenFactory.generateNewToken(account.getIsAdmin());

        this.tokenRepository.addTokenWithEmail(token, loginDto.email);
        return this.tokenAssembler.tokenToDto(token);
    }
}
