package ca.ulaval.glo4003.evulution.domain.assemblyLine.mediator;

import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteCarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AssemblyLineMediatorImplTest {

    @Mock
    private CompleteCarAssemblyLine completeCarAssemblyLine;

    @Mock
    private BatteryAssemblyLine batteryAssemblyLine;

    private AssemblyLineMediatorImpl assemblyLineMediator;

    @BeforeEach
    public void setUp() {
        assemblyLineMediator = new AssemblyLineMediatorImpl(batteryAssemblyLine, completeCarAssemblyLine);
    }

    @Test
    public void givenAnVehicleAssemblyLineClass_whenNotify_thenShouldCallTheBatteryAssemblyLineToStartNext() {
        // when
        assemblyLineMediator.notify(VehicleAssemblyLine.class);

        // then
        Mockito.verify(batteryAssemblyLine).startNext();
    }

    @Test
    public void givenAnBatteryAssemblyLineClass_whenNotify_thenShouldCallTheCompleteCarAssemblyLineToStartNext() {
        // when
        assemblyLineMediator.notify(BatteryAssemblyLine.class);

        // then
        Mockito.verify(completeCarAssemblyLine).startNext();
    }

}
