package ca.ulaval.glo4003.evulution.domain.account;

import ca.ulaval.glo4003.evulution.domain.account.exceptions.FailedLoginException;
import ca.ulaval.glo4003.evulution.domain.account.exceptions.UserIsNotAdminException;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import ca.ulaval.glo4003.evulution.domain.token.TokenRepository;

public abstract class Account {
    public AccountId id;
    public String email;
    public String password;

    protected boolean isAdmin = false;

    public Account(String email, String password) {
        this.id = new AccountId();
        this.email = email;
        this.password = password;
    }

    public Token login(String email, String password, TokenFactory tokenFactory, TokenRepository tokenRepository)
            throws FailedLoginException {
        if (!(this.email.equals(email) && this.password.equals(password)))
            throw new FailedLoginException();
        Token token = tokenFactory.generateNewToken();
        tokenRepository.addToken(token, id);
        return token;
    }

    public AccountId getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void verifyIfAdmin() throws UserIsNotAdminException {
        if (!isAdmin)
            throw new UserIsNotAdminException();
    }
}
