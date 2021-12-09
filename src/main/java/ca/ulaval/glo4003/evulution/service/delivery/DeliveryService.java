package ca.ulaval.glo4003.evulution.service.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryDomainService;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryRepository;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryModeException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
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
    private final DeliveryCompleteAssembler deliveryCompleteAssembler;
    private final DeliveryRepository deliveryRepository;
    private final SaleValidator saleValidator;
    private final DeliveryDomainService deliveryDomainService;
    private final SaleDomainService saleDomainService;

    public DeliveryService(DeliveryIdFactory deliveryIdFactory, DeliveryCompleteAssembler deliveryCompleteAssembler,
            DeliveryRepository deliveryRepository, SaleValidator saleValidator,
            DeliveryDomainService deliveryDomainService, SaleDomainService saleDomainService) {
        this.deliveryIdFactory = deliveryIdFactory;
        this.deliveryCompleteAssembler = deliveryCompleteAssembler;
        this.deliveryRepository = deliveryRepository;
        this.saleValidator = saleValidator;
        this.deliveryDomainService = deliveryDomainService;
        this.saleDomainService = saleDomainService;
    }

    public void chooseDeliveryLocation(int deliveryIdRequest, DeliveryLocationDto deliveryLocationDto) {
        try {
            DeliveryId deliveryId = this.deliveryIdFactory.createFromInt(deliveryIdRequest);
            saleValidator.validateCompleteStatus(deliveryId);

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

    public DeliveryCompleteDto completeDelivery(int deliveryIdRequest) {
        try {
            DeliveryId deliveryId = this.deliveryIdFactory.createFromInt(deliveryIdRequest);
            saleValidator.validateCompleteStatus(deliveryId);

            SaleId saleId = this.deliveryRepository.getSaleId(deliveryId);
            Invoice invoice = saleDomainService.startPayments(saleId);
            deliveryDomainService.completeDelivery(deliveryId);

            return deliveryCompleteAssembler.assemble(invoice.getPaymentsTaken(), invoice.getPaymentsLeft());
        } catch (SaleNotFoundException | DeliveryNotFoundException e) {
            throw new ServiceBadRequestException();
        } catch (IncompleteSaleException | DeliveryIncompleteException e) {
            throw new ServiceBadOrderOfOperationsException();
        }
    }
}
