package ca.ulaval.glo4003.evulution.domain.account;

import ca.ulaval.glo4003.evulution.domain.account.exceptions.FailedLoginException;
import ca.ulaval.glo4003.evulution.domain.account.exceptions.UserIsNotAdminException;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;

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

    public Token login(String email, String password, TokenFactory tokenFactory) throws FailedLoginException {
        if (!(this.email.equals(email) && this.password.equals(password)))
            throw new FailedLoginException();

        return tokenFactory.generateNewToken();
    }

    public AccountId getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public void verifyIfAdmin() throws UserIsNotAdminException {
        if (!isAdmin)
            throw new UserIsNotAdminException();
    }
}
