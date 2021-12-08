package ca.ulaval.glo4003.evulution.service.delivery;

import ca.ulaval.glo4003.evulution.service.delivery.dto.DeliveryCompleteDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class DeliveryCompleteAssemblerTest {
    private static final BigDecimal PAYMENTS_TAKEN = BigDecimal.TEN;
    private static final Integer PAYMENTS_LEFT = 5;

    private DeliveryCompleteAssembler deliveryCompleteAssembler;

    @BeforeEach
    void setUp() {
        deliveryCompleteAssembler = new DeliveryCompleteAssembler();
    }

    @Test
    public void whenAssemble_thenCreatesDeliveryCompleteDto() {
        // when
        DeliveryCompleteDto deliveryCompleteDto = deliveryCompleteAssembler.assemble(PAYMENTS_TAKEN, PAYMENTS_LEFT);

        // then
        Assertions.assertNotNull(deliveryCompleteDto);
    }
}
