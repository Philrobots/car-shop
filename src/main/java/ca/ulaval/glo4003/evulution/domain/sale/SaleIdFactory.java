package ca.ulaval.glo4003.evulution.domain.sale;

public class SaleIdFactory {

    private int saleCounter = 0;

    public SaleId create() {
        saleCounter++;
        return new SaleId(saleCounter);
    }

    public SaleId createFromInt(int saleId) {
        return new SaleId(saleId);
    }
}
