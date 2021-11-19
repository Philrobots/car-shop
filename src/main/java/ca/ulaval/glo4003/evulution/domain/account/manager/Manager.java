package ca.ulaval.glo4003.evulution.domain.account.manager;

import ca.ulaval.glo4003.evulution.domain.account.Account;

public class Manager extends Account {
    public Manager(String email, String password) {
        super(email, password);
        this.isAdmin = true;
    }
}
