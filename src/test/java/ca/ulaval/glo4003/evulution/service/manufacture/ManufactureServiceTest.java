package ca.ulaval.glo4003.evulution.service.manufacture;

import ca.ulaval.glo4003.evulution.domain.car.exceptions.BadCarSpecsException;
import ca.ulaval.glo4003.evulution.domain.manufacture.ManufactureDomainService;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.domain.sale.SaleValidator;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.CarNotChosenBeforeBatteryException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleAlreadyCompleteException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadOrderOfOperationsException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadRequestException;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.ChooseBatteryDto;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.ChooseCarDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManufactureServiceTest {
    private static final Integer A_SALE_ID_REQUEST = 2;
    private static final Integer AN_ESTIMATED_RANGE = 100;

    private static final String A_NAME = "name";
    private static final String A_COLOR = "color";

    @Mock
    private SaleValidator saleValidator;

    @Mock
    private ManufactureDomainService manufactureDomainService;

    @Mock
    private ChooseBatteryDto chooseBatteryDto;

    @Mock
    private ChooseCarDto chooseCarDto;

    @Mock
    private SaleId saleId;

    @Mock
    private EstimatedRangeAssembler estimatedRangeAssembler;

    private ManufactureService manufactureService;

    @BeforeEach
    void setUp() {
        this.manufactureService = new ManufactureService(saleValidator, manufactureDomainService,
                estimatedRangeAssembler);
    }

    @Test
    public void whenChooseCar_thenSaleValidatorValidatesNotCompleteStatus()
            throws SaleNotFoundException, SaleAlreadyCompleteException {
        // when
        this.manufactureService.chooseCar(A_SALE_ID_REQUEST, chooseCarDto);

        // then
        Mockito.verify(saleValidator).validateNotCompleteStatus(A_SALE_ID_REQUEST);
    }

    @Test
    public void whenChooseCar_thenManufactureDomainServiceAddsCar()
            throws SaleNotFoundException, SaleAlreadyCompleteException, BadCarSpecsException {
        // given
        BDDMockito.given(saleValidator.validateNotCompleteStatus(A_SALE_ID_REQUEST)).willReturn(saleId);
        chooseCarDto.name = A_NAME;
        chooseCarDto.color = A_COLOR;

        // when
        this.manufactureService.chooseCar(A_SALE_ID_REQUEST, chooseCarDto);

        // then
        Mockito.verify(manufactureDomainService).addCar(saleId, chooseCarDto.name, chooseCarDto.color);
    }

    @Test
    public void givenSaleNotFoundException_whenChooseCar_thenThrowsServiceBadRequestException()
            throws SaleNotFoundException, SaleAlreadyCompleteException {
        // given
        BDDMockito.doThrow(SaleNotFoundException.class).when(saleValidator)
                .validateNotCompleteStatus(A_SALE_ID_REQUEST);

        // when & then
        Assertions.assertThrows(ServiceBadRequestException.class,
                () -> this.manufactureService.chooseCar(A_SALE_ID_REQUEST, chooseCarDto));
    }

    @Test
    public void givenSaleAlreadyCompleteException_whenChooseCar_thenThrowsServiceBadOrderOfOperationsException()
            throws SaleNotFoundException, SaleAlreadyCompleteException {
        // given
        BDDMockito.doThrow(SaleAlreadyCompleteException.class).when(saleValidator)
                .validateNotCompleteStatus(A_SALE_ID_REQUEST);

        // when & then
        Assertions.assertThrows(ServiceBadOrderOfOperationsException.class,
                () -> this.manufactureService.chooseCar(A_SALE_ID_REQUEST, chooseCarDto));
    }

    @Test
    public void givenBadCarSpecsException_whenChooseCar_thenThrowsServiceBadInputParameterException()
            throws SaleNotFoundException, SaleAlreadyCompleteException, BadCarSpecsException {
        // given
        BDDMockito.given(saleValidator.validateNotCompleteStatus(A_SALE_ID_REQUEST)).willReturn(saleId);
        BDDMockito.doThrow(BadCarSpecsException.class).when(manufactureDomainService).addCar(saleId, chooseCarDto.name,
                chooseCarDto.color);

        // when & then
        Assertions.assertThrows(ServiceBadInputParameterException.class,
                () -> this.manufactureService.chooseCar(A_SALE_ID_REQUEST, chooseCarDto));
    }

    @Test
    public void whenChooseBattery_thenSaleValidatorValidatesNotCompleteStatus()
            throws SaleNotFoundException, SaleAlreadyCompleteException {
        // when
        this.manufactureService.chooseBattery(A_SALE_ID_REQUEST, chooseBatteryDto);

        // then
        Mockito.verify(saleValidator).validateNotCompleteStatus(A_SALE_ID_REQUEST);
    }

    @Test
    public void whenChooseBattery_thenManufactureDomainServiceAddsBattery() throws BadCarSpecsException,
            SaleNotFoundException, CarNotChosenBeforeBatteryException, SaleAlreadyCompleteException {
        // given
        BDDMockito.given(saleValidator.validateNotCompleteStatus(A_SALE_ID_REQUEST)).willReturn(saleId);

        // when
        this.manufactureService.chooseBattery(A_SALE_ID_REQUEST, chooseBatteryDto);

        // then
        Mockito.verify(manufactureDomainService).addBattery(saleId, chooseBatteryDto.type);
    }

    @Test
    public void whenChooseBattery_thenEstimatedRangeAssemblerAssemblesEstimatedRangeToDto() throws BadCarSpecsException,
            SaleNotFoundException, CarNotChosenBeforeBatteryException, SaleAlreadyCompleteException {
        // given
        BDDMockito.given(saleValidator.validateNotCompleteStatus(A_SALE_ID_REQUEST)).willReturn(saleId);
        BDDMockito.given(manufactureDomainService.addBattery(saleId, chooseBatteryDto.type))
                .willReturn(AN_ESTIMATED_RANGE);

        // when
        this.manufactureService.chooseBattery(A_SALE_ID_REQUEST, chooseBatteryDto);

        // then
        Mockito.verify(estimatedRangeAssembler).estimatedRangeToDto(AN_ESTIMATED_RANGE);
    }

    @Test
    public void givenSaleNotFoundException_whenChooseBattery_thenServiceBadRequestException()
            throws SaleNotFoundException, SaleAlreadyCompleteException {
        // given
        BDDMockito.doThrow(SaleNotFoundException.class).when(saleValidator)
                .validateNotCompleteStatus(A_SALE_ID_REQUEST);

        // when & then
        Assertions.assertThrows(ServiceBadRequestException.class,
                () -> this.manufactureService.chooseBattery(A_SALE_ID_REQUEST, chooseBatteryDto));
    }

    @Test
    public void givenSaleAlreadyCompleteException_whenChooseBattery_thenServiceBadOrderOfOperationsException()
            throws SaleNotFoundException, SaleAlreadyCompleteException {
        // given
        BDDMockito.doThrow(SaleAlreadyCompleteException.class).when(saleValidator)
                .validateNotCompleteStatus(A_SALE_ID_REQUEST);

        // when & then
        Assertions.assertThrows(ServiceBadOrderOfOperationsException.class,
                () -> this.manufactureService.chooseBattery(A_SALE_ID_REQUEST, chooseBatteryDto));
    }

    @Test
    public void givenCarNotChosenBeforeBatteryException_whenChooseBattery_thenServiceBadOrderOfOperationsException()
            throws BadCarSpecsException, SaleNotFoundException, CarNotChosenBeforeBatteryException,
            SaleAlreadyCompleteException {
        // given
        BDDMockito.given(saleValidator.validateNotCompleteStatus(A_SALE_ID_REQUEST)).willReturn(saleId);
        BDDMockito.doThrow(CarNotChosenBeforeBatteryException.class).when(manufactureDomainService).addBattery(saleId,
                chooseBatteryDto.type);

        // when & then
        Assertions.assertThrows(ServiceBadOrderOfOperationsException.class,
                () -> this.manufactureService.chooseBattery(A_SALE_ID_REQUEST, chooseBatteryDto));
    }

    @Test
    public void givenBadCarSpecsException_whenChooseBattery_thenServiceBadInputParameterException()
            throws BadCarSpecsException, SaleNotFoundException, CarNotChosenBeforeBatteryException,
            SaleAlreadyCompleteException {
        // given
        BDDMockito.given(saleValidator.validateNotCompleteStatus(A_SALE_ID_REQUEST)).willReturn(saleId);
        BDDMockito.doThrow(BadCarSpecsException.class).when(manufactureDomainService).addBattery(saleId,
                chooseBatteryDto.type);

        // when & then
        Assertions.assertThrows(ServiceBadInputParameterException.class,
                () -> this.manufactureService.chooseBattery(A_SALE_ID_REQUEST, chooseBatteryDto));
    }

}
