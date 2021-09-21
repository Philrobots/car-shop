package ca.ulaval.glo4003.evulution.domain.sale;

import java.util.Objects;

public class TransactionId {
    private final Integer transactionId;

    public TransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getId() {
        return this.transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TransactionId that = (TransactionId) o;
        return transactionId.equals(that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }
}
