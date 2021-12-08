package ca.ulaval.glo4003.evulution.domain.manufacture;

import java.util.Objects;
import java.util.UUID;

public class ProductionId {
    private final String productionId;

    public ProductionId() {
        this.productionId = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ProductionId))
            return false;
        ProductionId that = (ProductionId) o;
        return productionId.equals(that.productionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productionId);
    }
}
