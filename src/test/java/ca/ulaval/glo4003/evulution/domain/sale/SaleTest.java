package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.sale.exception.CarNotChosenBeforeBatteryException;
import ca.ulaval.glo4003.evulution.domain.sale.exception.MissingElementsForSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exception.SaleCompleteException;
import ca.ulaval.glo4003.evulution.domain.sale.exception.SaleNotCompletedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class SaleTest {
    private static final String AN_EMAIL = "email@email.com";
    private static final String DELIVERY_MODE = "At campus";
    private static final String DELIVERY_LOCATION = "Vachon";
    private static final Integer EFFICIENCY_EQUIVALENCE_RATE = 100;

    @Mock
    private TransactionId transactionId;

    @Mock
    private Delivery delivery;

    @Mock
    private Car car;

    @Mock
    private Battery battery;

    private Sale sale;

    @BeforeEach
    public void setUp() {
        this.sale = new Sale(AN_EMAIL, transactionId, delivery);
    }

    @Test
    public void givenCompleteSale_whenChooseCar_thenThrowSaleCompleteException() {
        // given
        sale.chooseCar(car);
        sale.chooseBattery(battery);
        sale.completeSale();

        // when
        Executable chooseCar = () -> sale.chooseCar(car);

        // then
        assertThrows(SaleCompleteException.class, chooseCar);
    }

    @Test
    public void givenCompleteSale_whenChooseBattery_thenThrowSaleCompleteException() {
        // given
        sale.chooseCar(car);
        sale.chooseBattery(battery);
        sale.completeSale();

        // when
        Executable chooseBattery = () -> sale.chooseBattery(battery);

        // then
        assertThrows(SaleCompleteException.class, chooseBattery);
    }

    @Test
    public void givenSaleWithoutCar_whenChooseBattery_thenThrowCarNotChosenBeforeBatteryException() {
        assertThrows(CarNotChosenBeforeBatteryException.class, () -> this.sale.chooseBattery(battery));
    }

    @Test
    public void givenNoChosenCar_whenCompleteSale_thenThrowMissingElementsForSaleException() {
        // when
        Executable completeSale = () -> sale.completeSale();

        // then
        assertThrows(MissingElementsForSaleException.class, completeSale);
    }

    @Test
    public void givenNoChosenBattery_whenCompleteSale_thenThrowMissingElementsForSaleException() {
        // given
        sale.chooseCar(car);

        // when
        Executable completeSale = () -> sale.completeSale();

        // then
        assertThrows(MissingElementsForSaleException.class, completeSale);
    }

    @Test
    public void givenSaleNotComplete_whenchooseDelivery_thenThrowSaleNotCompletedException() {
        assertThrows(SaleNotCompletedException.class, () -> this.sale.chooseDelivery(DELIVERY_MODE, DELIVERY_LOCATION));
    }

    @Test
    public void givenEfficiencyEquivalenceRate_whenGetBatteryAutonomy_thenReturnEstimatedRange() {
        // given
        sale.chooseCar(car);
        sale.chooseBattery(battery);
        BDDMockito.given(car.getEfficiencyEquivalenceRate()).willReturn(EFFICIENCY_EQUIVALENCE_RATE);

        // when
        sale.getBatteryAutonomy();

        // then
        Mockito.verify(battery).calculateEstimatedRange(EFFICIENCY_EQUIVALENCE_RATE);
    }
}
