package ca.ulaval.glo4003.evulution.domain.sale;

import java.util.Objects;

public class SaleId {
    private final Integer saleId;

    public SaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public String getId() {
        return this.saleId.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SaleId that = (SaleId) o;
        return saleId.equals(that.saleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId);
    }
}
