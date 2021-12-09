package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryRepository;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.IncompleteSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MismatchAccountIdWithSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleAlreadyCompleteException;
import ca.ulaval.glo4003.evulution.infrastructure.delivery.exceptions.DeliveryNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;

public class SaleValidator {
    private final SaleRepository saleRepository;
    private final DeliveryRepository deliveryRepository;
    private final SaleIdFactory saleIdFactory;

    public SaleValidator(SaleRepository saleRepository, DeliveryRepository deliveryRepository,
            SaleIdFactory saleIdFactory) {
        this.saleRepository = saleRepository;
        this.deliveryRepository = deliveryRepository;
        this.saleIdFactory = saleIdFactory;
    }

    public void validateSaleOwner(SaleId saleId, AccountId accountId)
            throws SaleNotFoundException, MismatchAccountIdWithSaleException {
        Sale sale = this.saleRepository.getSale(saleId);
        sale.verifyAccountId(accountId);
    }

    public void validateCompleteStatus(DeliveryId deliveryId)
            throws SaleNotFoundException, DeliveryNotFoundException, IncompleteSaleException {
        SaleId saleId = this.deliveryRepository.getSaleId(deliveryId);
        Sale sale = this.saleRepository.getSale(saleId);
        sale.verifyIsCompleted();
    }

    public SaleId validateNotCompleteStatus(int saleIdRequest)
            throws SaleNotFoundException, SaleAlreadyCompleteException {
        SaleId saleId = this.saleIdFactory.createFromInt(saleIdRequest);
        Sale sale = this.saleRepository.getSale(saleId);
        sale.verifyNotCompleted();
        return saleId;
    }
}
