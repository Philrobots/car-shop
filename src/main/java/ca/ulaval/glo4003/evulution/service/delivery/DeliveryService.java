package ca.ulaval.glo4003.evulution.service.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.*;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryModeException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoicePayment;
import ca.ulaval.glo4003.evulution.domain.sale.SaleDomainService;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.domain.sale.SaleValidator;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.IncompleteSaleException;
import ca.ulaval.glo4003.evulution.infrastructure.delivery.exceptions.DeliveryNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;
import ca.ulaval.glo4003.evulution.service.delivery.dto.DeliveryCompleteDto;
import ca.ulaval.glo4003.evulution.service.delivery.dto.DeliveryLocationDto;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadOrderOfOperationsException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadRequestException;

public class DeliveryService {
    private final DeliveryIdFactory deliveryIdFactory;
    private final DeliveryDetailsFactory deliveryDetailsFactory; // TODO: no usage
    private final InvoicePayment invoicePayment; // TODO: no usage
    private final DeliveryCompleteAssembler deliveryCompleteAssembler;
    private final DeliveryRepository deliveryRepository;
    private final SaleValidator saleValidator;
    private DeliveryDomainService deliveryDomainService;
    private SaleDomainService saleDomainService;

    public DeliveryService(DeliveryIdFactory deliveryIdFactory, DeliveryDetailsFactory deliveryDetailsFactory,
                           InvoicePayment invoicePayment, DeliveryCompleteAssembler deliveryCompleteAssembler,
                           DeliveryRepository deliveryRepository, SaleValidator saleValidator,
                           DeliveryDomainService deliveryDomainService, SaleDomainService saleDomainService) {
        this.deliveryIdFactory = deliveryIdFactory;
        this.deliveryDetailsFactory = deliveryDetailsFactory;
        this.invoicePayment = invoicePayment;
        this.deliveryCompleteAssembler = deliveryCompleteAssembler;
        this.deliveryRepository = deliveryRepository;
        this.saleValidator = saleValidator;
        this.deliveryDomainService = deliveryDomainService;
        this.saleDomainService = saleDomainService;
    }

    public void chooseDeliveryLocation(int deliveryIdInt, DeliveryLocationDto deliveryLocationDto) {
        try {
            DeliveryId deliveryId = saleValidator.validateCompleteStatusFromDeliveryId(deliveryIdInt);
            deliveryDomainService.setDeliveryModeLocationAndConfirmDelivery(deliveryId, deliveryLocationDto.mode,
                    deliveryLocationDto.location);
        } catch (SaleNotFoundException | DeliveryNotFoundException e) {
            throw new ServiceBadRequestException();
        } catch (IncompleteSaleException | DeliveryIncompleteException e) {
            throw new ServiceBadOrderOfOperationsException();
        } catch (BadDeliveryModeException | BadDeliveryLocationException e) {
            throw new ServiceBadInputParameterException();
        }
    }

    public DeliveryCompleteDto completeDelivery(int deliveryIdInt) {
        try {
            DeliveryId deliveryId = this.deliveryIdFactory.createFromInt(deliveryIdInt);
            SaleId saleId = this.deliveryRepository.getSaleId(deliveryId);

            Invoice invoice = saleDomainService.activateInvoice(saleId);
            deliveryDomainService.completeDelivery(deliveryId);

            return deliveryCompleteAssembler.assemble(invoice.getPaymentsTaken(), invoice.getPaymentsLeft());
        } catch (SaleNotFoundException | DeliveryNotFoundException e) {
            throw new ServiceBadRequestException();
        } catch (IncompleteSaleException | DeliveryIncompleteException e) {
            throw new ServiceBadOrderOfOperationsException();
        }
    }
}
