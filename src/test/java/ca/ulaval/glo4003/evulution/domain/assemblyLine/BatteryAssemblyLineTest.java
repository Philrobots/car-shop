package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryAssemblyFacade;
import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatteryAssemblyLineTest {

    private static final int ZERO_SECONDS = 0;
    private static final int AN_INT = 1;
    private static final TransactionId A_TRANSACTION_ID = new TransactionId(AN_INT);
    private static final String A_BATTERY_TYPE = "type";

    private BatteryAssemblyLine batteryAssemblyLine;

    @Mock
    private BatteryAssemblyFacade batteryAssemblyFacade;

    @Mock
    private Battery battery;

    @BeforeEach
    public void setup(){
        batteryAssemblyLine = new BatteryAssemblyLine(batteryAssemblyFacade, ZERO_SECONDS);
        when(batteryAssemblyFacade.getStatus(A_TRANSACTION_ID)).thenReturn(AssemblyStatus.ASSEMBLED);
        when(battery.getType()).thenReturn(A_BATTERY_TYPE);
    }

    @Test
    public void  whenCompleteBatteryCommand_thenShouldSetBatteryAsAssembled(){
        // when
        batteryAssemblyLine.completeBatteryCommand(A_TRANSACTION_ID, battery);

        // then
        verify(battery).setBatteryAsAssembled();
    }

    @Test
    public void  whenCompleteBatteryCommand_thenCallsNewBatteryCommand(){
        // when
        batteryAssemblyLine.completeBatteryCommand(A_TRANSACTION_ID, battery);

        // then
        verify(batteryAssemblyFacade).newBatteryCommand(A_TRANSACTION_ID, A_BATTERY_TYPE);
    }
}