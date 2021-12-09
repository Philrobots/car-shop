package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.MismatchAccountIdWithDeliveryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeliveryTest {
    private static final Integer AN_ASSEMBLY_TIME_IN_WEEKS = 1;
    private static final Integer A_CAR_ASSEMBLY_TIME_IN_WEEKS = 3;
    private static final Integer A_BATTERY_ASSEMBLY_TIME_IN_WEEKS = 2;
    private static final DeliveryStatus A_CONFIRMED_DELIVERY_STATUS = DeliveryStatus.CONFIRMED;
    private static final DeliveryStatus A_COMPLETED_DELIVERY_STATUS = DeliveryStatus.COMPLETED;
    private static final AccountId A_CORRECT_ACCOUNT_ID = new AccountId();
    private static final AccountId AN_INCORRECT_ACCOUNT_ID = new AccountId();

    private Delivery delivery;

    @Mock
    private DeliveryId deliveryId;

    @BeforeEach
    void setUp() {
        this.delivery = new Delivery(A_CORRECT_ACCOUNT_ID, deliveryId, AN_ASSEMBLY_TIME_IN_WEEKS);
    }

    @Test
    public void givenDeliveryDate_whenAddDelayInWeeks_thenAddDelayToDeliveryDate() {
        // given
        delivery.setCarTimeToProduce(A_CAR_ASSEMBLY_TIME_IN_WEEKS);
        delivery.setBatteryTimeToProduce(A_BATTERY_ASSEMBLY_TIME_IN_WEEKS);

        // when
        LocalDate deliveryDate = delivery.addDelayInWeeks(1);

        // then
        assertEquals(deliveryDate, LocalDate.now().plusWeeks(7));
    }

    @Test
    public void givenNonConfirmedStatus_whenSetStatusToCompleted_thenThrowsDeliveryIncompleteException() {
        assertThrows(DeliveryIncompleteException.class, () -> delivery.setStatus(A_COMPLETED_DELIVERY_STATUS));
    }

    @Test
    public void givenNonDeliveredStatus_whenSetStatusToConfirmed_thenThrowsDeliveryIncompleteException()
            throws DeliveryIncompleteException {
        // given
        delivery.setStatus(A_CONFIRMED_DELIVERY_STATUS);

        assertThrows(DeliveryIncompleteException.class, () -> delivery.setStatus(A_COMPLETED_DELIVERY_STATUS));
    }

    @Test
    public void givenCorrectAccountId_whenVerifyAccountId_thenDoesntThrowException() {
        assertDoesNotThrow(() -> delivery.verifyAccountId(A_CORRECT_ACCOUNT_ID));
    }

    @Test
    public void givenWrongAccountId_whenVerifyAccountId_thenThrowsMismatchAccountIdWithDeliveryException() {
        assertThrows(MismatchAccountIdWithDeliveryException.class,
                () -> delivery.verifyAccountId(AN_INCORRECT_ACCOUNT_ID));
    }
}
