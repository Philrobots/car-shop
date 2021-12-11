package ca.ulaval.glo4003.evulution.domain.assemblyline.battery;

import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.adapter.BatteryAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.email.ProductionLineEmailNotifier;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProduction;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProductionRepository;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.mockito.BDDMockito;

@ExtendWith(MockitoExtension.class)
class BatteryAssemblyLineTest {

    private static final int TIME_IN_WEEK = 1;

    @Mock
    private ProductionId productionId;

    @Mock
    private BatteryAssemblyAdapter batteryAssemblyAdapter;

    @Mock
    private AssemblyLineMediator assemblyLineMediator;

    @Mock
    private BatteryProductionRepository batteryProductionRepository;

    @Mock
    private ProductionLineEmailNotifier productionLineEmailNotifier;

    @Mock
    private BatteryProduction currentBatteryProduction;

    private BatteryAssemblyLineSequential batteryAssemblyLine;

    @BeforeEach
    public void setup() {
        this.batteryAssemblyLine = new BatteryAssemblyLineSequential(batteryAssemblyAdapter, batteryProductionRepository,
                productionLineEmailNotifier);
        this.batteryAssemblyLine.setMediator(assemblyLineMediator);
    }


    @Test
    public void givenABatteryProduction_whenStartNext_thenCurrentBatteryProduction_thenShouldAddNewCommand() {
        // given
        this.batteryAssemblyLine.addProduction(currentBatteryProduction);

        // when
        this.batteryAssemblyLine.startNext();

        // when
        Mockito.verify(currentBatteryProduction).newBatteryCommand(batteryAssemblyAdapter);
    }

    @Test
    public void givenABatteryProduction_whenStartNext_thenShouldSendEmail() {
        // given
        BDDMockito.given(currentBatteryProduction.getProductionId()).willReturn(productionId);
        BDDMockito.given(currentBatteryProduction.getProductionTimeInWeeks()).willReturn(TIME_IN_WEEK);
        this.batteryAssemblyLine.addProduction(currentBatteryProduction);

        // when
        this.batteryAssemblyLine.startNext();

        // when
        Mockito.verify(this.productionLineEmailNotifier).sendBatteryStartedEmail(productionId, TIME_IN_WEEK);
    }


    @Test
    public void whenReactivate_thenCallTheRepositoryToGetAndSendToProduction() {
        // when
        this.batteryAssemblyLine.reactivate();

        // then
        Mockito.verify(batteryProductionRepository).getAndSendToProduction();
    }

}
