package ca.ulaval.glo4003.evulution.service.sale;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.*;
import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.car.CarFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceFactory;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceRepository;
import ca.ulaval.glo4003.evulution.domain.sale.*;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.service.assemblyLine.AssemblyLineService;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {
    private static final String AN_EMAIL = "jo@live.com";
    private static final String A_FREQUENCY = "weekly";
    private static final int A_TRANSACTION_ID = 1;
    private static final String A_BANK_NO = "123";
    private static final Integer A_BANK_NO_INT = 123;
    private static final String A_ACCOUNT_NO = "1234567";
    private static final Integer A_ACCOUNT_NO_INT = 1234567;
    private static final BigDecimal A_BALANCE = BigDecimal.valueOf(4);

    private static final int AN_ESTIMATED_RANGE = 200;

    private InvoiceDto invoiceDto;

    @Mock
    private SaleFactory saleFactory;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenAssembler tokenAssembler;

    @Mock
    private TransactionIdAssembler transactionIdAssembler;

    @Mock
    private TransactionIdFactory transactionIdFactory;

    @Mock
    private CarFactory carFactory;

    @Mock
    private BatteryFactory batteryFactory;

    @Mock
    private Token token;

    @Mock
    private TransactionId transactionId;

    @Mock
    private DeliveryId deliveryId;

    @Mock
    private TokenDto tokenDto;

    @Mock
    private Sale sale;

    @Mock
    private SaleCreatedDto saleCreatedDto;

    @Mock
    private ChooseVehicleDto chooseVehicleDto;

    @Mock
    private Car car;

    @Mock
    private ChooseBatteryDto chooseBatteryDto;

    @Mock
    private Battery battery;

    @Mock
    private EstimatedRangeAssembler estimatedRangeAssembler;

    @Mock
    private EstimatedRangeDto estimatedRangeDto;

    @Mock
    private InvoiceFactory invoiceFactory;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private Invoice invoice;

    @Mock
    private AssemblyLineService assemblyLineService;

    private SaleService saleService;

    @BeforeEach
    public void setUp() {
        this.invoiceDto = new InvoiceDto();
        invoiceDto.bank_no = A_BANK_NO;
        invoiceDto.account_no = A_ACCOUNT_NO;
        invoiceDto.frequency = A_FREQUENCY;
        this.saleService = new SaleService(saleRepository, tokenRepository, invoiceRepository, tokenAssembler,
                transactionIdAssembler, saleFactory, transactionIdFactory, carFactory, batteryFactory, invoiceFactory,
                estimatedRangeAssembler, assemblyLineService);
    }

    @Test
    public void whenInitSale_thenTokenAssemblerCallsDtoToToken() {
        BDDMockito.given(tokenAssembler.dtoToToken(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getEmail(token)).willReturn(AN_EMAIL);
        BDDMockito.given(saleFactory.create(AN_EMAIL)).willReturn(sale);
        BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
        BDDMockito.given(sale.getDeliveryId()).willReturn(deliveryId);
        BDDMockito.given(transactionIdAssembler.transactionIdToDto(transactionId, deliveryId))
                .willReturn(saleCreatedDto);

        // when
        saleService.initSale(tokenDto);

        // then
        Mockito.verify(tokenAssembler).dtoToToken(tokenDto);
    }

    @Test
    public void whenInitSale_thenTokenRepositoryGetsEmail() {
        BDDMockito.given(tokenAssembler.dtoToToken(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getEmail(token)).willReturn(AN_EMAIL);
        BDDMockito.given(saleFactory.create(AN_EMAIL)).willReturn(sale);
        BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
        BDDMockito.given(sale.getDeliveryId()).willReturn(deliveryId);
        BDDMockito.given(transactionIdAssembler.transactionIdToDto(transactionId, deliveryId))
                .willReturn(saleCreatedDto);

        // when
        saleService.initSale(tokenDto);

        // then
        Mockito.verify(tokenRepository).getEmail(token);

    }

    @Test
    public void whenInitSale_thenSaleFactoryCreates() {
        BDDMockito.given(tokenAssembler.dtoToToken(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getEmail(token)).willReturn(AN_EMAIL);
        BDDMockito.given(saleFactory.create(AN_EMAIL)).willReturn(sale);
        BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
        BDDMockito.given(sale.getDeliveryId()).willReturn(deliveryId);
        BDDMockito.given(transactionIdAssembler.transactionIdToDto(transactionId, deliveryId))
                .willReturn(saleCreatedDto);

        // when
        saleService.initSale(tokenDto);

        // then
        Mockito.verify(saleFactory).create(AN_EMAIL);
    }

    @Test
    public void whenInitSale_thenSaleRepositoryRegistersSale() {
        BDDMockito.given(tokenAssembler.dtoToToken(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getEmail(token)).willReturn(AN_EMAIL);
        BDDMockito.given(saleFactory.create(AN_EMAIL)).willReturn(sale);
        BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
        BDDMockito.given(sale.getDeliveryId()).willReturn(deliveryId);
        BDDMockito.given(transactionIdAssembler.transactionIdToDto(transactionId, deliveryId))
                .willReturn(saleCreatedDto);

        // when
        saleService.initSale(tokenDto);

        // then
        Mockito.verify(saleRepository).registerSale(sale);
    }

    @Test
    public void whenIniStale_thenTransactionIdAssemblerCallsTransactionIdToDto() {
        BDDMockito.given(tokenAssembler.dtoToToken(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getEmail(token)).willReturn(AN_EMAIL);
        BDDMockito.given(saleFactory.create(AN_EMAIL)).willReturn(sale);
        BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
        BDDMockito.given(sale.getDeliveryId()).willReturn(deliveryId);
        BDDMockito.given(transactionIdAssembler.transactionIdToDto(transactionId, deliveryId))
                .willReturn(saleCreatedDto);

        // when
        saleService.initSale(tokenDto);

        // then
        Mockito.verify(transactionIdAssembler).transactionIdToDto(transactionId, deliveryId);
    }

    @Test
    public void whenInitSale_thenReturnsTransactionIdDto() {
        BDDMockito.given(tokenAssembler.dtoToToken(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getEmail(token)).willReturn(AN_EMAIL);
        BDDMockito.given(saleFactory.create(AN_EMAIL)).willReturn(sale);
        BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
        BDDMockito.given(sale.getDeliveryId()).willReturn(deliveryId);
        BDDMockito.given(transactionIdAssembler.transactionIdToDto(transactionId, deliveryId))
                .willReturn(this.saleCreatedDto);

        // when
        SaleCreatedDto saleCreatedDto = saleService.initSale(tokenDto);

        // then
        assertNotNull(saleCreatedDto);
    }

    @Test
    public void whenChooseVehicle_thenTransactionIdFactoryCreatesFromInt() {
        BDDMockito.given(transactionIdFactory.createFromInt(A_TRANSACTION_ID)).willReturn(transactionId);
        BDDMockito.given(saleRepository.getSale(transactionId)).willReturn(sale);
        BDDMockito.given(carFactory.create(chooseVehicleDto.name, chooseVehicleDto.color)).willReturn(car);

        // when
        saleService.chooseVehicle(A_TRANSACTION_ID, chooseVehicleDto);

        // then
        Mockito.verify(transactionIdFactory).createFromInt(A_TRANSACTION_ID);
    }

    @Test
    public void whenChooseVehicle_thenSaleRepositoryGetsSale() {
        BDDMockito.given(transactionIdFactory.createFromInt(A_TRANSACTION_ID)).willReturn(transactionId);
        BDDMockito.given(saleRepository.getSale(transactionId)).willReturn(sale);
        BDDMockito.given(carFactory.create(chooseVehicleDto.name, chooseVehicleDto.color)).willReturn(car);

        // when
        saleService.chooseVehicle(A_TRANSACTION_ID, chooseVehicleDto);

        // then
        Mockito.verify(saleRepository).getSale(transactionId);

    }

    @Test
    public void whenChooseVehicle_thenCarFactoryCreates() {
        BDDMockito.given(transactionIdFactory.createFromInt(A_TRANSACTION_ID)).willReturn(transactionId);
        BDDMockito.given(saleRepository.getSale(transactionId)).willReturn(sale);
        BDDMockito.given(carFactory.create(chooseVehicleDto.name, chooseVehicleDto.color)).willReturn(car);

        // when
        saleService.chooseVehicle(A_TRANSACTION_ID, chooseVehicleDto);

        // then
        Mockito.verify(carFactory).create(chooseVehicleDto.name, chooseVehicleDto.color);
    }

    @Test
    public void whenChooseVehicle_thenSaleChoosesCar() {
        BDDMockito.given(transactionIdFactory.createFromInt(A_TRANSACTION_ID)).willReturn(transactionId);
        BDDMockito.given(saleRepository.getSale(transactionId)).willReturn(sale);
        BDDMockito.given(carFactory.create(chooseVehicleDto.name, chooseVehicleDto.color)).willReturn(car);

        // when
        saleService.chooseVehicle(A_TRANSACTION_ID, chooseVehicleDto);

        // then
        Mockito.verify(sale).chooseCar(car);
    }

    @Test
    public void whenChooseBattery_thenTransactionIdFactoryCreatesFromInt() {
        BDDMockito.given(transactionIdFactory.createFromInt(A_TRANSACTION_ID)).willReturn(transactionId);
        BDDMockito.given(saleRepository.getSale(transactionId)).willReturn(sale);
        BDDMockito.given(batteryFactory.create(chooseBatteryDto.type)).willReturn(battery);
        BDDMockito.given(sale.getBatteryAutonomy()).willReturn(AN_ESTIMATED_RANGE);
        BDDMockito.given(estimatedRangeAssembler.EstimatedRangeToDto(AN_ESTIMATED_RANGE)).willReturn(estimatedRangeDto);

        // when
        saleService.chooseBattery(A_TRANSACTION_ID, chooseBatteryDto);

        // then
        Mockito.verify(transactionIdFactory).createFromInt(A_TRANSACTION_ID);
    }

    @Test
    public void whenChooseBattery_thenSaleRepositoryGetsSale() {
        BDDMockito.given(transactionIdFactory.createFromInt(A_TRANSACTION_ID)).willReturn(transactionId);
        BDDMockito.given(saleRepository.getSale(transactionId)).willReturn(sale);
        BDDMockito.given(batteryFactory.create(chooseBatteryDto.type)).willReturn(battery);
        BDDMockito.given(sale.getBatteryAutonomy()).willReturn(AN_ESTIMATED_RANGE);
        BDDMockito.given(estimatedRangeAssembler.EstimatedRangeToDto(AN_ESTIMATED_RANGE)).willReturn(estimatedRangeDto);

        // when
        saleService.chooseBattery(A_TRANSACTION_ID, chooseBatteryDto);

        // then
        Mockito.verify(saleRepository).getSale(transactionId);

    }

    @Test
    public void whenChooseBattery_thenBatteryFactoryCreates() {
        BDDMockito.given(transactionIdFactory.createFromInt(A_TRANSACTION_ID)).willReturn(transactionId);
        BDDMockito.given(saleRepository.getSale(transactionId)).willReturn(sale);
        BDDMockito.given(batteryFactory.create(chooseBatteryDto.type)).willReturn(battery);
        BDDMockito.given(sale.getBatteryAutonomy()).willReturn(AN_ESTIMATED_RANGE);
        BDDMockito.given(estimatedRangeAssembler.EstimatedRangeToDto(AN_ESTIMATED_RANGE)).willReturn(estimatedRangeDto);

        // when
        saleService.chooseBattery(A_TRANSACTION_ID, chooseBatteryDto);

        // then
        Mockito.verify(batteryFactory).create(chooseBatteryDto.type);
    }

    @Test
    public void whenChooseBattery_thenSaleChoosesBattery() {
        BDDMockito.given(transactionIdFactory.createFromInt(A_TRANSACTION_ID)).willReturn(transactionId);
        BDDMockito.given(saleRepository.getSale(transactionId)).willReturn(sale);
        BDDMockito.given(batteryFactory.create(chooseBatteryDto.type)).willReturn(battery);
        BDDMockito.given(sale.getBatteryAutonomy()).willReturn(AN_ESTIMATED_RANGE);
        BDDMockito.given(estimatedRangeAssembler.EstimatedRangeToDto(AN_ESTIMATED_RANGE)).willReturn(estimatedRangeDto);

        // when
        saleService.chooseBattery(A_TRANSACTION_ID, chooseBatteryDto);

        // then
        Mockito.verify(sale).chooseBattery(battery);
    }

    @Test
    public void whenChooseBattery_thenSaleGetsBatteryAutonomy() {
        BDDMockito.given(transactionIdFactory.createFromInt(A_TRANSACTION_ID)).willReturn(transactionId);
        BDDMockito.given(saleRepository.getSale(transactionId)).willReturn(sale);
        BDDMockito.given(batteryFactory.create(chooseBatteryDto.type)).willReturn(battery);
        BDDMockito.given(sale.getBatteryAutonomy()).willReturn(AN_ESTIMATED_RANGE);
        BDDMockito.given(estimatedRangeAssembler.EstimatedRangeToDto(AN_ESTIMATED_RANGE)).willReturn(estimatedRangeDto);

        // when
        saleService.chooseBattery(A_TRANSACTION_ID, chooseBatteryDto);

        // then
        Mockito.verify(sale).getBatteryAutonomy();
    }

    @Test
    public void whenChooseBattery_thenEstimatedRangeAssemblerCreatesDto() {
        BDDMockito.given(transactionIdFactory.createFromInt(A_TRANSACTION_ID)).willReturn(transactionId);
        BDDMockito.given(saleRepository.getSale(transactionId)).willReturn(sale);
        BDDMockito.given(batteryFactory.create(chooseBatteryDto.type)).willReturn(battery);
        BDDMockito.given(sale.getBatteryAutonomy()).willReturn(AN_ESTIMATED_RANGE);
        BDDMockito.given(estimatedRangeAssembler.EstimatedRangeToDto(AN_ESTIMATED_RANGE)).willReturn(estimatedRangeDto);

        // when
        saleService.chooseBattery(A_TRANSACTION_ID, chooseBatteryDto);

        // then
        Mockito.verify(estimatedRangeAssembler).EstimatedRangeToDto(AN_ESTIMATED_RANGE);
    }

    @Test
    public void whenCompleteSale_thenTransactionIdFactoryCreatesFromInt() {
        BDDMockito.given(transactionIdFactory.createFromInt(A_TRANSACTION_ID)).willReturn(transactionId);
        BDDMockito.given(saleRepository.getSale(transactionId)).willReturn(sale);
        BDDMockito.given(sale.getPrice()).willReturn(A_BALANCE);
        BDDMockito.given(invoiceFactory.create(A_BANK_NO_INT, A_ACCOUNT_NO_INT, A_FREQUENCY, A_BALANCE))
                .willReturn(invoice);

        // when
        saleService.completeSale(A_TRANSACTION_ID, invoiceDto);

        // then
        Mockito.verify(transactionIdFactory).createFromInt(A_TRANSACTION_ID);
    }

    @Test
    public void whenCompleteSale_thenSaleRepositoryGetsSale() {
        BDDMockito.given(transactionIdFactory.createFromInt(A_TRANSACTION_ID)).willReturn(transactionId);
        BDDMockito.given(saleRepository.getSale(transactionId)).willReturn(sale);
        BDDMockito.given(sale.getPrice()).willReturn(A_BALANCE);
        BDDMockito.given(invoiceFactory.create(A_BANK_NO_INT, A_ACCOUNT_NO_INT, A_FREQUENCY, A_BALANCE))
                .willReturn(invoice);

        // when
        saleService.completeSale(A_TRANSACTION_ID, invoiceDto);

        // then
        Mockito.verify(saleRepository).getSale(transactionId);
    }

    @Test
    public void whenCompleteSale_thenAddInvoiceToRepository() {
        BDDMockito.given(transactionIdFactory.createFromInt(A_TRANSACTION_ID)).willReturn(transactionId);
        BDDMockito.given(saleRepository.getSale(transactionId)).willReturn(sale);
        BDDMockito.given(sale.getPrice()).willReturn(A_BALANCE);
        BDDMockito.given(invoiceFactory.create(A_BANK_NO_INT, A_ACCOUNT_NO_INT, A_FREQUENCY, A_BALANCE))
                .willReturn(invoice);

        // when
        saleService.completeSale(A_TRANSACTION_ID, invoiceDto);

        // then
        Mockito.verify(invoiceRepository).addInvoice(sale.getTransactionId(), invoice);
    }

    @Test
    public void whenCompleteSale_thenInvoiceFactoryCreate() {
        BDDMockito.given(transactionIdFactory.createFromInt(A_TRANSACTION_ID)).willReturn(transactionId);
        BDDMockito.given(saleRepository.getSale(transactionId)).willReturn(sale);
        BDDMockito.given(sale.getPrice()).willReturn(A_BALANCE);
        BDDMockito.given(invoiceFactory.create(A_BANK_NO_INT, A_ACCOUNT_NO_INT, A_FREQUENCY, A_BALANCE))
                .willReturn(invoice);

        // when
        saleService.completeSale(A_TRANSACTION_ID, invoiceDto);

        // then
        Mockito.verify(invoiceFactory).create(A_BANK_NO_INT, A_ACCOUNT_NO_INT, invoiceDto.frequency, A_BALANCE);
    }
}
