package ca.ulaval.glo4003.evulution.infrastructure.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryRepository;
import ca.ulaval.glo4003.evulution.domain.manufacture.Manufacture;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.infrastructure.delivery.exceptions.DeliveryNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.manufacture.ManufactureDao;

import java.util.Map;

public class DeliveryRepositoryInMemory implements DeliveryRepository {
    private final ManufactureDao manufactureDao;

    public DeliveryRepositoryInMemory(ManufactureDao manufactureDao) {
        this.manufactureDao = manufactureDao;
    }

    @Override
    public Delivery getDelivery(DeliveryId deliveryId) throws DeliveryNotFoundException {
        for (Manufacture manufacture : manufactureDao.getManufactures()) {
            if (manufacture.getDeliveryId() == deliveryId)
                return manufacture.getDelivery();
        }
        throw new DeliveryNotFoundException();
    }

    @Override
    public SaleId getSaleId(DeliveryId deliveryId) throws DeliveryNotFoundException {
        for (Map.Entry<SaleId, Manufacture> entry : manufactureDao.getManufacturesMapEntries()) {
            if (entry.getValue().getDeliveryId() == deliveryId)
                return entry.getKey();
        }
        throw new DeliveryNotFoundException();
    }

    @Override
    public void updateDelivery(Delivery delivery) {
        for (Map.Entry<SaleId, Manufacture> entry : manufactureDao.getManufacturesMapEntries()) {
            if (entry.getValue().getDeliveryId() == delivery.getDeliveryId()) {
                Manufacture manufacture = entry.getValue();
                manufacture.setDelivery(delivery);
                manufactureDao.add(entry.getKey(), manufacture);
            }
        }
    }

}
