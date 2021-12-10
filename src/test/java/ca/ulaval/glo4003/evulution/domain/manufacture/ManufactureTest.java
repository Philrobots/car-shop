package ca.ulaval.glo4003.evulution.domain.manufacture;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.CarNotChosenBeforeBatteryException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MissingElementsForSaleException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManufactureTest {

    Manufacture manufacture;

    AccountId AN_ACCOUNT_ID = new AccountId();
    DeliveryId A_DELIVERY_ID = new DeliveryId(123);
    int AN_ASSEMBLY_TIME_IN_WEEKS = 1;
    Car A_CAR = new Car("binou", "binou2", 1, BigDecimal.ONE, "1", "red");
    Battery A_BATTERY = new Battery("type", "123", 1, BigDecimal.ONE, 1);


    Delivery A_DELIVERY = new Delivery(AN_ACCOUNT_ID, A_DELIVERY_ID, AN_ASSEMBLY_TIME_IN_WEEKS);

    @BeforeEach
    void setup() {
        this.manufacture = new Manufacture(A_DELIVERY);
    }

    @Test
    void givenNoCar_whenAddBattery_thenThrowCarNotChosenBatteryException() throws CarNotChosenBeforeBatteryException {
        when(this.manufacture.addBattery(A_BATTERY)).thenThrow(CarNotChosenBeforeBatteryException.class);
    }

    @Test
    void givenCar_whenAddBattery_thenReturnEstimatedRange() throws CarNotChosenBeforeBatteryException {
        this.manufacture.setCar(A_CAR);
        int actual_range = this.manufacture.addBattery(A_BATTERY);
        Assertions.assertEquals(A_BATTERY.calculateEstimatedRange(A_CAR.getEfficiencyEquivalenceRate()), actual_range);
    }

    @Test
    void givenNoCar_whenSetReadyToProduce_thenThrowMissingElementsForSaleException() {
        Assertions.assertThrows(MissingElementsForSaleException.class, () -> this.manufacture.setReadyToProduce());
    }

    @Test
    void givenNoBattery_whenSetReadyToProduce_thenThrowMissingElementsforSaleException() {
        this.manufacture.setCar(A_CAR);
        Assertions.assertThrows(MissingElementsForSaleException.class, () -> this.manufacture.setReadyToProduce());
    }

    @Test
    void givenCarAndBattery_whenSetReadyToProduce_thenSetStatusToReady() throws CarNotChosenBeforeBatteryException, MissingElementsForSaleException {
        this.manufacture.setCar(A_CAR);
        this.manufacture.addBattery(A_BATTERY);
        this.manufacture.setReadyToProduce();
        Assertions.assertTrue(this.manufacture.isReadyToProduce());
    }
}
