package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryRepository;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.email.Email;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.production.BatteryProduction;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatteryAssemblyLineTest {

    private static final int AN_INT = 1;
    private static final TransactionId A_TRANSACTION_ID = new TransactionId(AN_INT);
    private static final String A_BATTERY_TYPE = "type";
    private static final String AN_EMAIL = "email@email.com";
    private static final BatteryProduction A_PRODUCTION_BATTERY = new BatteryProduction(A_TRANSACTION_ID,
            A_BATTERY_TYPE, AN_EMAIL);

    private BatteryAssemblyLine batteryAssemblyLine;

    @Mock
    private BatteryAssemblyAdapter batteryAssemblyAdapter;

    @Mock
    private AssemblyLineMediator assemblyLineMediator;

    @Mock
    private BatteryRepository batteryRepository;

    @Mock
    private EmailFactory emailFactory;

    @Mock
    private Email email;

    @BeforeEach
    public void setup() {
        batteryAssemblyLine = new BatteryAssemblyLine(batteryAssemblyAdapter, batteryRepository, emailFactory);
        batteryAssemblyLine.setMediator(assemblyLineMediator);
    }

    @Test
    public void givenBatteryInProduction_whenAdvanceAssemblyLine_thenAdvanceIsCalledInAdapter() {
        // given
        when(batteryAssemblyAdapter.getStatus(A_TRANSACTION_ID)).thenReturn(AssemblyStatus.ASSEMBLED);
        when(emailFactory.createBatteryBuiltEmail(any())).thenReturn(email);
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
        when(emailFactory.createBatteryBuiltEmail(any())).thenReturn(email);
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
        when(emailFactory.createBatteryBuiltEmail(any())).thenReturn(email);
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
