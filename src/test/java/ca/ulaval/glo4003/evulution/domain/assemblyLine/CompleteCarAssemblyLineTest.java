package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteCarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.email.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CompleteCarAssemblyLineTest {
    private static final Integer AN_ASSEMBLY_TIME_IN_WEEKS = 1;
    private static final Integer ZERO_SECONDS = 0;

    @Mock
    private EmailSender emailSender;

    @Mock
    private EmailFactory emailFactory;

    private CompleteCarAssemblyLine completeCarAssemblyLine;

    @Mock
    private Delivery delivery;

    @BeforeEach
    public void setUp() {
        // completeCarAssemblyLine = new CompleteCarAssemblyLine(ZERO_SECONDS, AN_ASSEMBLY_TIME_IN_WEEKS, emailFactory,
        // emailSender);

    }

}
