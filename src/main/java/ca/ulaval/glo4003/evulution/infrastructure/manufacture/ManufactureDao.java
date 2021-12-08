package ca.ulaval.glo4003.evulution.infrastructure.manufacture;

import ca.ulaval.glo4003.evulution.domain.manufacture.Manufacture;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;

import java.util.*;

public class ManufactureDao {
    private Map<SaleId, Manufacture> manufactures = new HashMap<>();

    public void add(SaleId saleId, Manufacture manufacture) {
        manufactures.put(saleId, manufacture);
    }

    public List<Manufacture> getManufactures() {
        return new ArrayList<>(manufactures.values());
    }

    public Set<Map.Entry<SaleId, Manufacture>> getManufacturesMapEntries() {
        return manufactures.entrySet();
    }

    public Manufacture getBySaleId(SaleId saleId) {
        return manufactures.get(saleId);
    }

    public Map<SaleId, Manufacture> getManufacturesReadyForProduction() {

        Map<SaleId, Manufacture> manufacturesReadyForProduction = new HashMap<>();

        for (Map.Entry<SaleId, Manufacture> manufactureEntry : this.manufactures.entrySet()) {
            if (manufactureEntry.getValue().isReadyToProduce()) {
                manufacturesReadyForProduction.put(manufactureEntry.getKey(), manufactureEntry.getValue());
            }
        }

        return manufacturesReadyForProduction;

    }
}
