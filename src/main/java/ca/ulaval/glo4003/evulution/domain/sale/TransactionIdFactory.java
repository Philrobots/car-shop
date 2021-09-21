package ca.ulaval.glo4003.evulution.domain.sale;

public class TransactionIdFactory {

    private int transactionCounter = 0;

    public TransactionId create() {
        transactionCounter++;
        return new TransactionId(transactionCounter);
    }

    public TransactionId createFromInt(int transactionId) {
        return new TransactionId(transactionId);
    }
}
