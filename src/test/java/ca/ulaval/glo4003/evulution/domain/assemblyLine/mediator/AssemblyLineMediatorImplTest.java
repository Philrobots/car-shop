package ca.ulaval.glo4003.evulution.domain.assemblyLine.mediator;

import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.complete.CompleteAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class AssemblyLineMediatorImplTest {

    @Mock
    private CompleteAssemblyLineSequential completeAssemblyLine;

    @Mock
    private BatteryAssemblyLineSequential batteryAssemblyLine;

    @Mock
    private CarAssemblyLineSequential carAssemblyLine;

    private AssemblyLineMediatorImpl assemblyLineMediator;

    @BeforeEach
    public void setUp() {
        assemblyLineMediator = new AssemblyLineMediatorImpl(batteryAssemblyLine, completeAssemblyLine, carAssemblyLine);
    }

     @Test
     public void givenAnVehicleAssemblyLineClass_whenNotify_thenShouldCallTheBatteryAssemblyLineToStartNext() {
         // when
         assemblyLineMediator.notify(CarAssemblyLine.class);

         // then
         Mockito.verify(batteryAssemblyLine).startNext();
     }

     @Test
     public void givenAnBatteryAssemblyLineClass_whenNotify_thenShouldCallTheCompleteCarAssemblyLineToStartNext() {
         // when
         assemblyLineMediator.notify(BatteryAssemblyLine.class);

         // then
         Mockito.verify(completeAssemblyLine).startNext();
     }

    @Test
    public void givenAnCompleteAssemblyLineClass_whenNotify_thenShouldCallTheCompleteCarAssemblyLineToStartNext() {
        // when
        assemblyLineMediator.notify(CompleteAssemblyLineSequential.class);

        // then
        Mockito.verify(carAssemblyLine).startNext();
    }

    @Test
    public void givenCompleteCar_whenIsCarState_thenShouldBeFalse(){
        assemblyLineMediator.notify(CarAssemblyLine.class);

        assertFalse(assemblyLineMediator.isCarState());
    }

    @Test
    public void givenCompleteCar_whenIsBatteryOrCompleteState_thenShouldBeTrue(){
        assemblyLineMediator.notify(CarAssemblyLine.class);

        assertTrue(assemblyLineMediator.isCompleteOrBatteryState());
    }

}
