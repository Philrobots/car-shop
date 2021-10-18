package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteCarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompleteCarAssemblyLineTest {
    private static final int ZERO_SECONDS = 0;

    private CompleteCarAssemblyLine completeCarAssemblyLine;

    @Mock
    private Delivery delivery;

    @BeforeEach
    public void setUp() {
        completeCarAssemblyLine = new CompleteCarAssemblyLine(ZERO_SECONDS);

    }

    @Test
    public void whenCompleteCarCommand_thenDeliveryIsDeliveredToCampus() {
        // when
        completeCarAssemblyLine.completeCarCommand(delivery);

        // verify
        verify(delivery).deliverToCampus();
    }

}