package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryRepository;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.IncompleteSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MismatchAccountIdWithSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleAlreadyCompleteException;
import ca.ulaval.glo4003.evulution.infrastructure.delivery.exceptions.DeliveryNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;

public class SaleValidator {
    private final SaleRepository saleRepository;
    private DeliveryRepository deliveryRepository;
    private SaleIdFactory saleIdFactory;
    private DeliveryIdFactory deliveryIdFactory;

    public SaleValidator(SaleRepository saleRepository, DeliveryRepository deliveryRepository,
            SaleIdFactory saleIdFactory, DeliveryIdFactory deliveryIdFactory) {
        this.saleRepository = saleRepository;
        this.deliveryRepository = deliveryRepository;
        this.saleIdFactory = saleIdFactory;
        this.deliveryIdFactory = deliveryIdFactory;
    }

    public void validateSaleOwner(SaleId saleId, AccountId accountId)
            throws SaleNotFoundException, MismatchAccountIdWithSaleException {
        Sale sale = this.saleRepository.getSale(saleId);
        sale.verifyAccountId(accountId);
    }

    public DeliveryId validateCompleteStatusFromDeliveryId(int deliveryIdRequest)
            throws SaleNotFoundException, DeliveryNotFoundException, IncompleteSaleException {
        DeliveryId deliveryId = this.deliveryIdFactory.createFromInt(deliveryIdRequest);
        SaleId saleId = this.deliveryRepository.getSaleId(deliveryId);
        Sale sale = this.saleRepository.getSale(saleId);
        sale.verifyIsCompleted();
        return deliveryId;
    }

    public SaleId validateNotCompleteStatus(int saleIdRequest)
            throws SaleNotFoundException, SaleAlreadyCompleteException {
        SaleId saleId = this.saleIdFactory.createFromInt(saleIdRequest);
        Sale sale = this.saleRepository.getSale(saleId);
        sale.verifyNotCompleted();
        return saleId;
    }
}
