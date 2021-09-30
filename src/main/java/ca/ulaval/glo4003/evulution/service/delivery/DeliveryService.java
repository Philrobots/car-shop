package ca.ulaval.glo4003.evulution.service.delivery;

import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryLocationDto;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;

public class DeliveryService {
    private DeliveryIdFactory deliveryIdFactory;
    private SaleRepository saleRepository;

    public DeliveryService(DeliveryIdFactory deliveryIdFactory, SaleRepository saleRepository) {
        this.deliveryIdFactory = deliveryIdFactory;
        this.saleRepository = saleRepository;
    }

    public void chooseDeliveryLocation(int transactionIdInt, DeliveryLocationDto deliveryLocationDto) {
        DeliveryId deliveryId = this.deliveryIdFactory.createFromInt(transactionIdInt);
        Sale sale = this.saleRepository.getSaleFromDeliveryId(deliveryId);
        sale.chooseDelivery(deliveryLocationDto.mode, deliveryLocationDto.location);
    }
}
