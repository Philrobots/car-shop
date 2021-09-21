package ca.ulaval.glo4003.evulution.domain.sale;

public class SaleFactory {
    TransactionIdFactory transactionIdFactory;

    public SaleFactory(TransactionIdFactory transactionIdFactory) {
        this.transactionIdFactory = transactionIdFactory;
    }

    public Sale create(String email) {
        TransactionId transactionId = this.transactionIdFactory.create();
        return new Sale(email, transactionId);
    }
}
