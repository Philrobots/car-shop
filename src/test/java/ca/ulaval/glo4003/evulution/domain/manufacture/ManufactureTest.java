package ca.ulaval.glo4003.evulution.domain.manufacture;

import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProductionFactory;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionFactory;
import ca.ulaval.glo4003.evulution.domain.production.complete.CompleteAssemblyProductionFactory;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.CarNotChosenBeforeBatteryException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MissingElementsForSaleException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ManufactureTest {
    private static final Integer A_TIME_TO_PRODUCE = 1;
    private static final Integer AN_EQUIVALENCE_RATE = 200;
    private static final String A_BATTERY_TYPE = "STANDARD";
    private static final String A_VEHICLE_STYLE = "Pouliot";

    @Mock
    private ProductionId productionId;

    @Mock
    private Car car;

    @Mock
    private Battery battery;

    @Mock
    private Delivery delivery;

    @Mock
    private BatteryProductionFactory batteryProductionFactory;

    @Mock
    private CarProductionFactory carProductionFactory;

    @Mock
    private CompleteAssemblyProductionFactory completeAssemblyProductionFactory;

    private Manufacture manufacture;

    @BeforeEach
    void setup() {
        this.manufacture = new Manufacture(productionId, delivery);
    }

    @Test
    public void whenGetDeliveryId_thenDeliveryGetsId() {
        // when
        this.manufacture.getDeliveryId();

        // then
        Mockito.verify(delivery).getDeliveryId();
    }

    @Test
    public void whenSetCar_thenDeliverySetsCarTimeToProduce() {
        // given
        BDDMockito.given(car.getTimeToProduce()).willReturn(A_TIME_TO_PRODUCE);

        // when
        this.manufacture.setCar(car);

        // then
        Mockito.verify(delivery).setCarTimeToProduce(A_TIME_TO_PRODUCE);
    }

    @Test
    public void givenNullCar_whenSetBattery_thenCarNotChosenBeforeBatteryException() {
        // when & then
        Assertions.assertThrows(CarNotChosenBeforeBatteryException.class, () -> this.manufacture.setBattery(battery));

    }

    @Test
    public void whenSetBattery_thenDeliverySetBatteryTimeToProduce() throws CarNotChosenBeforeBatteryException {
        // given
        BDDMockito.given(battery.getTimeToProduce()).willReturn(A_TIME_TO_PRODUCE);
        this.manufacture.setCar(car);

        // when
        this.manufacture.setBattery(battery);

        // then
        Mockito.verify(delivery).setBatteryTimeToProduce(A_TIME_TO_PRODUCE);
    }

    @Test
    public void whenSetBattery_thenCarGetsEfficiencyEquivalenceRate() throws CarNotChosenBeforeBatteryException {
        // given
        BDDMockito.given(battery.getTimeToProduce()).willReturn(A_TIME_TO_PRODUCE);
        this.manufacture.setCar(car);

        // when
        this.manufacture.setBattery(battery);

        // then
        Mockito.verify(car).getEfficiencyEquivalenceRate();
    }

    @Test
    public void whenSetBattery_thenBatteryCalculatesEstimatedRange() throws CarNotChosenBeforeBatteryException {
        // given
        BDDMockito.given(battery.getTimeToProduce()).willReturn(A_TIME_TO_PRODUCE);
        BDDMockito.given(car.getEfficiencyEquivalenceRate()).willReturn(AN_EQUIVALENCE_RATE);
        this.manufacture.setCar(car);

        // when
        this.manufacture.setBattery(battery);

        // then
        Mockito.verify(battery).calculateEstimatedRange(AN_EQUIVALENCE_RATE);
    }

    @Test
    public void givenNullCar_whenSetReadyToProduce_thenMissingElementsForSaleException() {
        // when & then
        Assertions.assertThrows(MissingElementsForSaleException.class, () -> this.manufacture.setReadyToProduce());
    }

    @Test
    public void givenNullBattery_whenSetReadyToProduce_thenMissingElementsForSaleException() {
        // given
        this.manufacture.setCar(car);

        // when & then
        Assertions.assertThrows(MissingElementsForSaleException.class, () -> this.manufacture.setReadyToProduce());
    }

    @Test
    public void whenSetInProduction_thenReturnsProductionId() {
        // then
        Assertions.assertEquals(productionId, this.manufacture.setInProduction());
    }

    @Test
    public void whenGenerateBatteryInProduction_thenBatteryProductionFactoryCreates()
            throws CarNotChosenBeforeBatteryException {
        // given
        BDDMockito.given(battery.getTimeToProduce()).willReturn(A_TIME_TO_PRODUCE);
        BDDMockito.given(battery.getType()).willReturn(A_BATTERY_TYPE);
        this.manufacture.setCar(car);
        this.manufacture.setBattery(battery);

        // when
        this.manufacture.generateBatteryProduction(batteryProductionFactory);

        // then
        Mockito.verify(batteryProductionFactory).create(productionId, battery.getType(), battery.getTimeToProduce());
    }

    @Test
    void whenGenerateCarProduction_thenCarProductionFactoryCreates() {
        // given
        BDDMockito.given(car.getTimeToProduce()).willReturn(A_TIME_TO_PRODUCE);
        BDDMockito.given(car.getStyle()).willReturn(A_VEHICLE_STYLE);
        this.manufacture.setCar(car);

        // when
        this.manufacture.generateCarProduction(carProductionFactory);

        // then
        Mockito.verify(carProductionFactory).create(productionId, A_VEHICLE_STYLE, A_TIME_TO_PRODUCE);
    }

    @Test
    public void whenGenerateCompleteAssemblyProduction_thenCompleteAssemblyProductionFactoryCreates() {
        // when
        this.manufacture.generateCompleteAssemblyProduction(completeAssemblyProductionFactory);

        // then
        Mockito.verify(completeAssemblyProductionFactory).create(productionId, delivery);
    }
}
