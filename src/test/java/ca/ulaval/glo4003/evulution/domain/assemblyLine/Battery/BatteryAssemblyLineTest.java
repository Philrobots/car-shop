package ca.ulaval.glo4003.evulution.domain.assemblyLine.Battery;

import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.adapter.BatteryAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.email.ProductionLineEmailNotifier;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProductionRepository;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.email.Email;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BatteryAssemblyLineTest {

    private static final int AN_INT = 1;
    private static final String A_BATTERY_TYPE = "type";
    private static final String AN_EMAIL = "email@email.com";
    private static final Integer A_PRODUCTION_TIME = 2;

    private BatteryAssemblyLineSequential batteryAssemblyLine;

    @Mock
    private BatteryAssemblyAdapter batteryAssemblyAdapter;

    @Mock
    private AssemblyLineMediator assemblyLineMediator;

    @Mock
    private BatteryProductionRepository batteryProductionRepository;

    @Mock
    private EmailFactory emailFactory;

    @Mock
    private Email email;

    @Mock
    private ProductionLineEmailNotifier productionLineEmailNotifier;

    @BeforeEach
    public void setup() {
        batteryAssemblyLine = new BatteryAssemblyLineSequential(batteryAssemblyAdapter, batteryProductionRepository,
                productionLineEmailNotifier);
        batteryAssemblyLine.setMediator(assemblyLineMediator);
    }

    // @Test
    // public void givenBatteryInProduction_whenAdvanceAssemblyLine_thenAdvanceIsCalledInAdapter() {
    // // given
    // when(batteryAssemblyAdapter.getStatus(A_SALE_ID)).thenReturn(AssemblyStatus.ASSEMBLED);
    // when(emailFactory.createBatteryBatteryInProductionEmail(List.of(AN_EMAIL), A_PRODUCTION_TIME)).thenReturn(email);
    // batteryAssemblyLine.addProduction(A_PRODUCTION_BATTERY);
    // batteryAssemblyLine.startNext();
    //
    // // when
    // batteryAssemblyLine.advance();
    //
    // // then
    // verify(batteryAssemblyAdapter).advance();
    // }
    //
    // @Test
    // public void givenBatteryReadyToBeBeAssembled_whenAdvanceAssemblyLine_thenNotifiesMediator() {
    // // given
    // when(batteryAssemblyAdapter.getStatus(A_SALE_ID)).thenReturn(AssemblyStatus.ASSEMBLED);
    // when(emailFactory.createBatteryBatteryInProductionEmail(List.of(AN_EMAIL), A_PRODUCTION_TIME)).thenReturn(email);
    // batteryAssemblyLine.addProduction(A_PRODUCTION_BATTERY);
    // batteryAssemblyLine.startNext();
    //
    // // when
    // batteryAssemblyLine.advance();
    //
    // // then
    // verify(assemblyLineMediator).notify(batteryAssemblyLine.getClass());
    // }
    //
    // @Test
    // public void whenAdvance_thenAddsInBatteryRepository() {
    // // given
    // when(batteryAssemblyAdapter.getStatus(A_SALE_ID)).thenReturn(AssemblyStatus.ASSEMBLED);
    // when(emailFactory.createBatteryBatteryInProductionEmail(List.of(AN_EMAIL), A_PRODUCTION_TIME)).thenReturn(email);
    // batteryAssemblyLine.addProduction(A_PRODUCTION_BATTERY);
    // batteryAssemblyLine.startNext();
    //
    // // when
    // batteryAssemblyLine.advance();
    //
    // // then
    // verify(batteryRepository).add(A_BATTERY_TYPE, A_PRODUCTION_BATTERY);
    // }
    //
    // @Test
    // public void givenBatteryToProduce_whenStartNext_thenNewBatteryCommandIsCalledInAdapter() {
    // // given
    // when(emailFactory.createBatteryBatteryInProductionEmail(List.of(AN_EMAIL), A_PRODUCTION_TIME)).thenReturn(email);
    // batteryAssemblyLine.addProduction(A_PRODUCTION_BATTERY);
    //
    // // when
    // batteryAssemblyLine.startNext();
    //
    // // then
    // verify(batteryAssemblyAdapter).newBatteryCommand(A_PRODUCTION_BATTERY.getTransactionId(),
    // A_PRODUCTION_BATTERY.getBatteryType());
    // }
}
