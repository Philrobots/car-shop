package ca.ulaval.glo4003.evulution.domain.account;

public interface AccountRepository {
    void addAccount(Account account);

    Account getAccountByEmail(String email);
}
