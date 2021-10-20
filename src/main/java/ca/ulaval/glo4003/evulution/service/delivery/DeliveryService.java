package ca.ulaval.glo4003.evulution.service.delivery;

import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryLocationDto;
import ca.ulaval.glo4003.evulution.domain.delivery.*;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;

public class DeliveryService {
    private DeliveryIdFactory deliveryIdFactory;
    private DeliveryDetailsFactory deliveryDetailsFactory;
    private SaleRepository saleRepository;

    public DeliveryService(DeliveryIdFactory deliveryIdFactory, DeliveryDetailsFactory deliveryDetailsFactory,
            SaleRepository saleRepository) {
        this.deliveryIdFactory = deliveryIdFactory;
        this.deliveryDetailsFactory = deliveryDetailsFactory;
        this.saleRepository = saleRepository;
    }

    public void chooseDeliveryLocation(int deliveryIdInt, DeliveryLocationDto deliveryLocationDto) {
        DeliveryId deliveryId = this.deliveryIdFactory.createFromInt(deliveryIdInt);
        Sale sale = this.saleRepository.getSaleFromDeliveryId(deliveryId);
        DeliveryDetails deliveryDetails = deliveryDetailsFactory.create(deliveryLocationDto.mode, deliveryLocationDto.location);
        sale.setDeliveryDetails(deliveryDetails);
    }
}
