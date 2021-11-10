package ca.ulaval.glo4003.evulution.service.delivery;

import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryCompleteDto;
import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryLocationDto;
import ca.ulaval.glo4003.evulution.domain.delivery.*;
import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.service.invoice.InvoiceService;

public class DeliveryService {
    private final DeliveryIdFactory deliveryIdFactory;
    private final DeliveryDetailsFactory deliveryDetailsFactory;
    private final SaleRepository saleRepository;
    private final InvoiceService invoiceService;
    private final DeliveryCompleteAssembler deliveryCompleteAssembler;

    public DeliveryService(DeliveryIdFactory deliveryIdFactory, DeliveryDetailsFactory deliveryDetailsFactory,
            SaleRepository saleRepository, InvoiceService invoiceService,
            DeliveryCompleteAssembler deliveryCompleteAssembler) {
        this.deliveryIdFactory = deliveryIdFactory;
        this.deliveryDetailsFactory = deliveryDetailsFactory;
        this.saleRepository = saleRepository;
        this.invoiceService = invoiceService;
        this.deliveryCompleteAssembler = deliveryCompleteAssembler;
    }

    public void chooseDeliveryLocation(int deliveryIdInt, DeliveryLocationDto deliveryLocationDto) {
        DeliveryId deliveryId = this.deliveryIdFactory.createFromInt(deliveryIdInt);
        Sale sale = this.saleRepository.getSaleFromDeliveryId(deliveryId);
        DeliveryDetails deliveryDetails = deliveryDetailsFactory.create(deliveryLocationDto.mode,
                deliveryLocationDto.location);
        sale.setDeliveryDetails(deliveryDetails);
    }

    public DeliveryCompleteDto completeDelivery(int deliveryIdInt) {
        DeliveryId deliveryId = this.deliveryIdFactory.createFromInt(deliveryIdInt);
        Sale sale = this.saleRepository.getSaleFromDeliveryId(deliveryId);
        sale.setDeliveryStatus(DeliveryStatus.COMPLETED);
        Invoice invoice = invoiceService.makeInvoiceActive(sale.getTransactionId());

        return deliveryCompleteAssembler.assemble(invoice.getPaymentsTaken(), invoice.getPaymentsLeft());
    }
}
