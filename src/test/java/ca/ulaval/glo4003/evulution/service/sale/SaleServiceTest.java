package ca.ulaval.glo4003.evulution.service.sale;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseBatteryDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseVehicleDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.SaleCreatedDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.EstimatedRangeDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.TransactionIdDto;
import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.car.CarFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.sale.*;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {

    private static final String AN_EMAIL = "jo@live.com";

    private static final int A_TRANSACTION_ID = 1;

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
    private Delivery delivery;

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

    private SaleService saleService;

    @BeforeEach
    public void setUp() {
        saleService = new SaleService(saleFactory, saleRepository, tokenRepository, tokenAssembler,
                transactionIdAssembler, transactionIdFactory, carFactory, batteryFactory, estimatedRangeAssembler);
    }

    @Test
    public void whenInitSale_thenTokenAssemblerCallsDtoToToken() {
        BDDMockito.given(tokenAssembler.dtoToToken(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getEmail(token)).willReturn(AN_EMAIL);
        BDDMockito.given(saleFactory.create(AN_EMAIL)).willReturn(sale);
        BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
        BDDMockito.given(sale.getDelivery()).willReturn(delivery);
        BDDMockito.given(delivery.getDeliveryId()).willReturn(deliveryId);
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
        BDDMockito.given(sale.getDelivery()).willReturn(delivery);
        BDDMockito.given(delivery.getDeliveryId()).willReturn(deliveryId);
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
        BDDMockito.given(sale.getDelivery()).willReturn(delivery);
        BDDMockito.given(delivery.getDeliveryId()).willReturn(deliveryId);
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
        BDDMockito.given(sale.getDelivery()).willReturn(delivery);
        BDDMockito.given(delivery.getDeliveryId()).willReturn(deliveryId);
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
        BDDMockito.given(sale.getDelivery()).willReturn(delivery);
        BDDMockito.given(delivery.getDeliveryId()).willReturn(deliveryId);
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
        BDDMockito.given(sale.getDelivery()).willReturn(delivery);
        BDDMockito.given(delivery.getDeliveryId()).willReturn(deliveryId);
        BDDMockito.given(transactionIdAssembler.transactionIdToDto(transactionId, deliveryId))
                .willReturn(this.saleCreatedDto);

        // when
        SaleCreatedDto saleCreatedDto = saleService.initSale(tokenDto);

        // then
        assertTrue(saleCreatedDto != null);
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
}
