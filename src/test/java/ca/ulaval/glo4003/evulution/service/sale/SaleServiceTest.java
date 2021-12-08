package ca.ulaval.glo4003.evulution.service.sale;

import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseBatteryRequest;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseCarRequest;
import ca.ulaval.glo4003.evulution.api.sale.dto.EstimatedRangeResponse;
import ca.ulaval.glo4003.evulution.api.sale.dto.SaleResponse;
import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.car.CarFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceFactory;
import ca.ulaval.glo4003.evulution.domain.manufacture.ManufactureDomainService;
import ca.ulaval.glo4003.evulution.domain.sale.*;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenRepository;
import ca.ulaval.glo4003.evulution.infrastructure.token.exceptions.TokenNotFoundException;
import ca.ulaval.glo4003.evulution.service.assemblyLine.AssemblyLineService;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.service.manufacture.EstimatedRangeAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {
    private static final String AN_EMAIL = "jo@live.com";
    private static final String A_FREQUENCY = "weekly";
    private static final int A_SALE_ID = 1;
    private static final Integer A_BANK_NO_INT = 123;
    private static final Integer A_ACCOUNT_NO_INT = 1234567;
    private static final BigDecimal A_BALANCE = BigDecimal.valueOf(4);
    private static final int AN_ESTIMATED_RANGE = 200;

    @Mock
    private SaleFactory saleFactory;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenAssembler tokenAssembler;

    @Mock
    private SaleCreatedAssembler saleCreatedAssembler;

    @Mock
    private SaleIdFactory saleIdFactory;

    @Mock
    private CarFactory carFactory;

    @Mock
    private BatteryFactory batteryFactory;

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
    private SaleResponse saleResponse;

    @Mock
    private ChooseCarRequest chooseCarRequest;

    @Mock
    private Car car;

    @Mock
    private ChooseBatteryRequest chooseBatteryRequest;

    @Mock
    private Battery battery;

    @Mock
    private EstimatedRangeAssembler estimatedRangeAssembler;

    @Mock
    private EstimatedRangeResponse estimatedRangeResponse;

    @Mock
    private InvoiceFactory invoiceFactory;

    @Mock
    private Invoice invoice;

    @Mock
    private AssemblyLineService assemblyLineService;

    @Mock
    private ManufactureDomainService manufactureDomainService;

    @Mock
    private SaleDomainService saleDomainService;

    @Mock
    private AccountId accountId;

    private SaleService saleService;

    @BeforeEach
    public void setUp() {
        this.saleService = new SaleService(saleRepository, saleCreatedAssembler, saleFactory, invoiceFactory,
                assemblyLineService, manufactureDomainService, saleDomainService, saleIdFactory, tokenAssembler);
    }

    /*
     * @Test public void whenInitSale_thenTokenAssemblerAssembleToken() { // when saleService.initSale(tokenDto);
     * 
     * // then Mockito.verify(tokenAssembler).assembleTokenFromDto(tokenDto); }
     * 
     * @Test public void whenInitSale_thenSaleFactoryCreates() throws TokenNotFoundException {
     * BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
     * 
     * // when saleService.initSale(tokenDto);
     * 
     * // then Mockito.verify(saleFactory).create(token); }
     */
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
}
