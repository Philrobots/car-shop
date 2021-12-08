package ca.ulaval.glo4003.evulution.domain.account;

import java.util.Objects;
import java.util.UUID;

public class AccountId {
    private final String accountId;

    public AccountId() {
        this.accountId = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AccountId))
            return false;
        AccountId accountId1 = (AccountId) o;
        return Objects.equals(accountId, accountId1.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
}
