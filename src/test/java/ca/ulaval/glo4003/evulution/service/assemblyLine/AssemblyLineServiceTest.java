package ca.ulaval.glo4003.evulution.service.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsNotShutdownException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsShutdownException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionSwitcher;
import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AssemblyLineServiceTest {
    @Mock
    private ProductionLine productionLine;

    @Mock
    private ProductionSwitcher switcher;

    private AssemblyLineService assemblyLineService;

    @BeforeEach
    public void setUp() {
        assemblyLineService = new AssemblyLineService(productionLine, switcher);
    }

    @Test
    public void whenShutdown_thenProductionLineCallsShutdowns() throws EmailException, AssemblyLineIsShutdownException {
        // when
        assemblyLineService.shutdown();

        Mockito.verify(productionLine).shutdown();
    }

    @Test
    public void whenReactivate_thenProductionLineReactivates()
            throws AssemblyLineIsNotShutdownException, EmailException {
        // when
        assemblyLineService.reactivate();

        Mockito.verify(productionLine).reactivate();
    }
}
