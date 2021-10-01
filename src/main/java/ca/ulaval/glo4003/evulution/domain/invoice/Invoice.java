package ca.ulaval.glo4003.evulution.domain.invoice;

public class Invoice {
    private int bank_no;
    private int account_no;
    private Frequency frequency;

    public Invoice(int bank_no, int account_no, Frequency frequency) {
        this.bank_no = bank_no;
        this.account_no = account_no;
        this.frequency = frequency;
    }

}
