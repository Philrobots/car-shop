package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryRepository;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.production.BatteryProduction;
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

    private static final int AN_INT = 1;
    private static final TransactionId A_TRANSACTION_ID = new TransactionId(AN_INT);
    private static final String A_BATTERY_TYPE = "type";
    private static final BatteryProduction A_PRODUCTION_BATTERY = new BatteryProduction(A_TRANSACTION_ID,
            A_BATTERY_TYPE);

    private BatteryAssemblyLine batteryAssemblyLine;

    @Mock
    private BatteryAssemblyAdapter batteryAssemblyAdapter;

    @Mock
    private AssemblyLineMediator assemblyLineMediator;

    @Mock
    private BatteryRepository batteryRepository;

    @BeforeEach
    public void setup() {
        batteryAssemblyLine = new BatteryAssemblyLine(batteryAssemblyAdapter, batteryRepository);
        batteryAssemblyLine.setMediator(assemblyLineMediator);
    }

    @Test
    public void givenBatteryInProduction_whenAdvanceAssemblyLine_thenAdvanceIsCalledInAdapter() {
        // given
        when(batteryAssemblyAdapter.getStatus(A_TRANSACTION_ID)).thenReturn(AssemblyStatus.ASSEMBLED);
        batteryAssemblyLine.addProduction(A_PRODUCTION_BATTERY);
        batteryAssemblyLine.startNext();
        // when
        batteryAssemblyLine.advance();

        // then
        verify(batteryAssemblyAdapter).advance();
    }

    @Test
    public void givenBatteryReadyToBeBeAssembled_whenAdvanceAssemblyLine_thenNotifiesMediator() {
        // given
        when(batteryAssemblyAdapter.getStatus(A_TRANSACTION_ID)).thenReturn(AssemblyStatus.ASSEMBLED);
        batteryAssemblyLine.addProduction(A_PRODUCTION_BATTERY);
        batteryAssemblyLine.startNext();

        // when
        batteryAssemblyLine.advance();

        // then
        verify(assemblyLineMediator).notify(batteryAssemblyLine.getClass());
    }

    @Test
    public void whenAdvance_thenAddsInBatteryRepository() {
        // given
        when(batteryAssemblyAdapter.getStatus(A_TRANSACTION_ID)).thenReturn(AssemblyStatus.ASSEMBLED);
        batteryAssemblyLine.addProduction(A_PRODUCTION_BATTERY);
        batteryAssemblyLine.startNext();

        // when
        batteryAssemblyLine.advance();

        // then
        verify(batteryRepository).add(A_BATTERY_TYPE, A_PRODUCTION_BATTERY);
    }

    @Test
    public void givenBatteryToProduce_whenStartNext_thenNewBatteryCommandIsCalledInAdapter() {
        // given
        batteryAssemblyLine.addProduction(A_PRODUCTION_BATTERY);

        // when
        batteryAssemblyLine.startNext();

        // then
        verify(batteryAssemblyAdapter).newBatteryCommand(A_PRODUCTION_BATTERY.getTransactionId(),
                A_PRODUCTION_BATTERY.getBatteryType());
    }
}
