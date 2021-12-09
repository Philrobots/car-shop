package ca.ulaval.glo4003.evulution.service.sale;

import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.invoice.exceptions.InvalidInvoiceException;
import ca.ulaval.glo4003.evulution.domain.manufacture.ManufactureDomainService;
import ca.ulaval.glo4003.evulution.domain.sale.*;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MissingElementsForSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleAlreadyCompleteException;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.token.exceptions.TokenNotFoundException;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadOrderOfOperationsException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadRequestException;
import ca.ulaval.glo4003.evulution.service.sale.dto.InvoiceDto;
import ca.ulaval.glo4003.evulution.service.sale.dto.SaleCreatedDto;

public class SaleService {
    private final SaleRepository saleRepository;
    private final SaleCreatedAssembler saleCreatedAssembler;
    private final SaleFactory saleFactory;
    private final ManufactureDomainService manufactureDomainService;
    private final SaleDomainService saleDomainService;
    private final SaleIdFactory saleIdFactory;
    private final TokenAssembler tokenAssembler;

    public SaleService(SaleRepository saleRepository, SaleCreatedAssembler saleCreatedAssembler,
            SaleFactory saleFactory, ManufactureDomainService manufactureDomainService,
            SaleDomainService saleDomainService, SaleIdFactory saleIdFactory, TokenAssembler tokenAssembler) {
        this.saleRepository = saleRepository;
        this.saleCreatedAssembler = saleCreatedAssembler;
        this.saleFactory = saleFactory;
        this.manufactureDomainService = manufactureDomainService;
        this.saleDomainService = saleDomainService;
        this.saleIdFactory = saleIdFactory;
        this.tokenAssembler = tokenAssembler;
    }

    public SaleCreatedDto initSale(TokenDto tokenDto) {
        try {
            Token token = this.tokenAssembler.assembleTokenFromDto(tokenDto);
            Sale sale = this.saleFactory.create(token);
            this.saleRepository.registerSale(sale);

            DeliveryId deliveryId = this.manufactureDomainService.createManufactureWithDelivery(sale.getAccountId(),
                    sale.getSaleId());
            return this.saleCreatedAssembler.assemble(sale.getSaleId(), deliveryId);
        } catch (TokenNotFoundException e) {
            throw new ServiceBadRequestException();
        } catch (DeliveryIncompleteException e) {
            throw new ServiceBadOrderOfOperationsException();
        }
    }

    public void completeSale(int saleIdRequest, InvoiceDto invoiceDto) {
        try {
            SaleId saleId = this.saleIdFactory.createFromInt(saleIdRequest);
            this.manufactureDomainService.setManufactureReadyToProduce(saleId);

            this.saleDomainService.completeSale(saleId, Integer.parseInt(invoiceDto.bankNumber),
                    Integer.parseInt(invoiceDto.accountNumber), invoiceDto.frequency);
        } catch (MissingElementsForSaleException | SaleAlreadyCompleteException e) {
            throw new ServiceBadOrderOfOperationsException();
        } catch (SaleNotFoundException e) {
            throw new ServiceBadRequestException();
        } catch (InvalidInvoiceException e) {
            throw new ServiceBadInputParameterException();
        }
    }
}
