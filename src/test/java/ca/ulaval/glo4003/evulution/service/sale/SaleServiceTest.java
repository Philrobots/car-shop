package ca.ulaval.glo4003.evulution.service.sale;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {
    private static final int A_SALE_ID_REQUEST = 2;
    private static final String A_BANK_NUMBER = "123";
    private static final String AN_ACCOUNT_NUMBER = "456";
    private static final String A_FREQUENCY = "Weekly";

    @Mock
    private AccountId accountId;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private SaleCreatedAssembler saleCreatedAssembler;

    @Mock
    private SaleFactory saleFactory;

    @Mock
    private ManufactureDomainService manufactureDomainService;

    @Mock
    private SaleDomainService saleDomainService;

    @Mock
    private SaleIdFactory saleIdFactory;

    @Mock
    private TokenAssembler tokenAssembler;

    @Mock
    private Token token;

    @Mock
    private SaleId saleId;

    @Mock
    private DeliveryId deliveryId;

    @Mock
    private TokenDto tokenDto;

    @Mock
    private Sale sale;

    @Mock
    private InvoiceDto invoiceDto;

    private SaleService saleService;

    @BeforeEach
    public void setUp() {
        invoiceDto.bankNumber = A_BANK_NUMBER;
        invoiceDto.accountNumber = AN_ACCOUNT_NUMBER;
        invoiceDto.frequency = A_FREQUENCY;
        this.saleService = new SaleService(saleRepository, saleCreatedAssembler, saleFactory, manufactureDomainService,
                saleDomainService, saleIdFactory, tokenAssembler);
    }

    @Test
    public void whenInitSale_thenTokenAssemblerAssemblesToken() throws TokenNotFoundException {
        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(saleFactory.create(token)).willReturn(sale);

        // when
        saleService.initSale(tokenDto);

        // then
        Mockito.verify(tokenAssembler).assembleTokenFromDto(tokenDto);
    }

    @Test
    public void whenInitSale_thenSaleFactoryCreates() throws TokenNotFoundException {
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(saleFactory.create(token)).willReturn(sale);

        // when
        saleService.initSale(tokenDto);

        // then
        Mockito.verify(saleFactory).create(token);
    }

    @Test
    public void whenInitSale_thenSaleRepositoryRegisterSale() throws TokenNotFoundException {
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(saleFactory.create(token)).willReturn(sale);

        // when
        saleService.initSale(tokenDto);

        // then
        Mockito.verify(saleRepository).registerSale(sale);
    }

    @Test
    public void whenInitSale_thenManufactureDomainServiceCreatesManufactureWithDelivery()
            throws TokenNotFoundException, DeliveryIncompleteException {
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(saleFactory.create(token)).willReturn(sale);
        BDDMockito.given(sale.getAccountId()).willReturn(accountId);
        BDDMockito.given(sale.getSaleId()).willReturn(saleId);

        // when
        saleService.initSale(tokenDto);

        // then
        Mockito.verify(manufactureDomainService).createManufactureWithDelivery(accountId, saleId);
    }

    @Test
    public void whenInitSale_thenSaleCreatedAssemblerAssembles()
            throws TokenNotFoundException, DeliveryIncompleteException {
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(saleFactory.create(token)).willReturn(sale);
        BDDMockito.given(sale.getAccountId()).willReturn(accountId);
        BDDMockito.given(sale.getSaleId()).willReturn(saleId);
        BDDMockito.given(manufactureDomainService.createManufactureWithDelivery(accountId, saleId))
                .willReturn(deliveryId);
        BDDMockito.given(sale.getSaleId()).willReturn(saleId);

        // when
        saleService.initSale(tokenDto);

        // then
        Mockito.verify(saleCreatedAssembler).assemble(saleId, deliveryId);
    }

    @Test
    public void givenDeliveryIncompleteException_whenInitSale_thenThrowsServiceBadOrderOfOperationsException()
            throws DeliveryIncompleteException, TokenNotFoundException {

        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(saleFactory.create(token)).willReturn(sale);
        BDDMockito.given(sale.getSaleId()).willReturn(saleId);
        BDDMockito.given(sale.getAccountId()).willReturn(accountId);
        BDDMockito.doThrow(DeliveryIncompleteException.class).when(manufactureDomainService)
                .createManufactureWithDelivery(accountId, saleId);

        // when & then
        Assertions.assertThrows(ServiceBadOrderOfOperationsException.class, () -> saleService.initSale(tokenDto));
    }

    @Test
    public void givenInvalidSaleFactory_whenInitSale_thenThrowsServiceBadRequestException()
            throws TokenNotFoundException {

        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.doThrow(TokenNotFoundException.class).when(saleFactory).create(token);

        // when & then
        Assertions.assertThrows(ServiceBadRequestException.class, () -> saleService.initSale(tokenDto));
    }

    @Test
    public void whenCompleteSale_thenSaleIdFactoryCreatesFromInt() {
        // when
        saleService.completeSale(A_SALE_ID_REQUEST, invoiceDto);

        // then
        Mockito.verify(saleIdFactory).createFromInt(A_SALE_ID_REQUEST);
    }

    @Test
    public void whenCompleteSale_thenManufactureDomainServiceSetsManufactureReadyToProduce()
            throws MissingElementsForSaleException {

        // given
        BDDMockito.given(saleIdFactory.createFromInt(A_SALE_ID_REQUEST)).willReturn(saleId);
        // when
        saleService.completeSale(A_SALE_ID_REQUEST, invoiceDto);

        // then
        Mockito.verify(manufactureDomainService).setManufactureReadyToProduce(saleId);
    }

    @Test
    public void whenCompleteSale_thenSaleDomainServiceCompletesSale()
            throws SaleNotFoundException, SaleAlreadyCompleteException, InvalidInvoiceException {

        // given
        BDDMockito.given(saleIdFactory.createFromInt(A_SALE_ID_REQUEST)).willReturn(saleId);

        // when
        saleService.completeSale(A_SALE_ID_REQUEST, invoiceDto);

        // then
        Mockito.verify(saleDomainService).completeSale(saleId, Integer.parseInt(invoiceDto.bankNumber),
                Integer.parseInt(invoiceDto.accountNumber), invoiceDto.frequency);
    }

    @Test
    public void givenServiceBadRequestException_whenCompleteSale_thenServiceBadOrderOfOperationsException()
            throws MissingElementsForSaleException {

        // given
        BDDMockito.given(saleIdFactory.createFromInt(A_SALE_ID_REQUEST)).willReturn(saleId);
        Mockito.doThrow(MissingElementsForSaleException.class).when(manufactureDomainService)
                .setManufactureReadyToProduce(saleId);

        // when & then
        Assertions.assertThrows(ServiceBadOrderOfOperationsException.class,
                () -> saleService.completeSale(A_SALE_ID_REQUEST, invoiceDto));

    }

    @Test
    public void givenSaleAlreadyCompleteException_whenCompleteSale_thenThrowsSaleAlreadyCompleteException()
            throws SaleNotFoundException, SaleAlreadyCompleteException, InvalidInvoiceException {

        // given
        BDDMockito.given(saleIdFactory.createFromInt(A_SALE_ID_REQUEST)).willReturn(saleId);
        Mockito.doThrow(SaleAlreadyCompleteException.class).when(saleDomainService).completeSale(saleId,
                Integer.parseInt(invoiceDto.bankNumber), Integer.parseInt(invoiceDto.accountNumber),
                invoiceDto.frequency);

        // when & then
        Assertions.assertThrows(ServiceBadOrderOfOperationsException.class,
                () -> saleService.completeSale(A_SALE_ID_REQUEST, invoiceDto));

    }

    @Test
    public void givenSaleNotFoundException_whenCompleteSale_thenThrowsServiceBadRequestException()
            throws SaleNotFoundException, SaleAlreadyCompleteException, InvalidInvoiceException {

        // given
        BDDMockito.given(saleIdFactory.createFromInt(A_SALE_ID_REQUEST)).willReturn(saleId);
        Mockito.doThrow(new SaleNotFoundException()).when(saleDomainService).completeSale(saleId,
                Integer.parseInt(invoiceDto.bankNumber), Integer.parseInt(invoiceDto.accountNumber),
                invoiceDto.frequency);

        // when & then
        Assertions.assertThrows(ServiceBadRequestException.class,
                () -> saleService.completeSale(A_SALE_ID_REQUEST, invoiceDto));

    }

    @Test
    public void givenInvalidInvoiceException_whenCompleteSale_thenThrowsServiceBadInputParameterException()
            throws SaleNotFoundException, SaleAlreadyCompleteException, InvalidInvoiceException {

        // given
        BDDMockito.given(saleIdFactory.createFromInt(A_SALE_ID_REQUEST)).willReturn(saleId);
        Mockito.doThrow(new InvalidInvoiceException()).when(saleDomainService).completeSale(saleId,
                Integer.parseInt(invoiceDto.bankNumber), Integer.parseInt(invoiceDto.accountNumber),
                invoiceDto.frequency);

        // when & then
        Assertions.assertThrows(ServiceBadInputParameterException.class,
                () -> saleService.completeSale(A_SALE_ID_REQUEST, invoiceDto));

    }
}
