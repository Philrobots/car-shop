package ca.ulaval.glo4003.evulution.service.login;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.account.exceptions.FailedLoginException;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import ca.ulaval.glo4003.evulution.domain.token.TokenRepository;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.service.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.service.login.exceptions.ServiceUnableToLoginException;

public class LoginService {
    private TokenFactory tokenFactory;
    private TokenRepository tokenRepository;
    private AccountRepository accountRepository;
    private TokenAssembler tokenAssembler;

    public LoginService(TokenFactory tokenFactory, TokenRepository tokenRepository, AccountRepository accountRepository,
                        TokenAssembler tokenAssembler) {
        this.tokenFactory = tokenFactory;
        this.tokenRepository = tokenRepository;
        this.accountRepository = accountRepository;
        this.tokenAssembler = tokenAssembler;
    }

    public TokenDto loginCustomer(LoginDto loginDto) {
        try {
            Account account = this.accountRepository.findAccountByEmail(loginDto.email);
            Token token = account.login(loginDto.email, loginDto.password, tokenFactory, tokenRepository);

            return this.tokenAssembler.assembleDtoFromToken(token);
        } catch (FailedLoginException | AccountNotFoundException e) {
            throw new ServiceUnableToLoginException();
        }
    }
}
