package ca.ulaval.glo4003.evulution.service.delivery;

import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryLocationDto;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;

public class DeliveryService {
    private DeliveryIdFactory deliveryIdFactory;
    private DeliveryFactory deliveryFactory;
    private SaleRepository saleRepository;

    public DeliveryService(DeliveryIdFactory deliveryIdFactory, DeliveryFactory deliveryFactory,
            SaleRepository saleRepository) {
        this.deliveryIdFactory = deliveryIdFactory;
        this.deliveryFactory = deliveryFactory;
        this.saleRepository = saleRepository;
    }

    public void chooseDeliveryLocation(int deliveryIdInt, DeliveryLocationDto deliveryLocationDto) {
        DeliveryId deliveryId = this.deliveryIdFactory.createFromInt(deliveryIdInt);
        Sale sale = this.saleRepository.getSaleFromDeliveryId(deliveryId);
        Delivery delivery = deliveryFactory.create(deliveryLocationDto.mode, deliveryLocationDto.location);
        sale.chooseDelivery(delivery);
    }
}
