package ca.ulaval.glo4003.evulution.domain.assemblyline.complete;

import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.email.ProductionLineEmailNotifier;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionRepository;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProductionRepository;
import ca.ulaval.glo4003.evulution.domain.production.complete.CompleteAssemblyProduction;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompleteAssemblyLineSequentialTest {

    private CompleteAssemblyLineSequential completeAssemblyLine;

    @Mock
    private CompleteAssemblyProduction completeAssemblyProduction;

    @Mock
    private CarProductionRepository carProductionRepository;

    @Mock
    private BatteryProductionRepository batteryProductionRepository;

    @Mock
    private ProductionLineEmailNotifier productionLineEmailNotifier;

    @Mock
    private AssemblyLineMediator assemblyLineMediator;

    @Mock
    private ProductionId productionId;

    @BeforeEach
    public void setUp() {
        completeAssemblyLine = new CompleteAssemblyLineSequential(carProductionRepository, batteryProductionRepository,
                productionLineEmailNotifier);
        completeAssemblyLine.setMediator(assemblyLineMediator);
    }

    @Test
    public void givenValidProduction_whenAdvance_thenMediatorNotifies()
            throws InvalidMappingKeyException, DeliveryIncompleteException {
        // then
        completeAssemblyLine.addProduction(completeAssemblyProduction);
        completeAssemblyLine.startNext();
        completeAssemblyLine.advance();
        completeAssemblyLine.advance();
        completeAssemblyLine.advance();

        BDDMockito.verify(assemblyLineMediator).notify(CompleteAssemblyLineSequential.class);
    }

    @Test
    public void givenValidProduction_whenAdvance_thenProductionShips()
            throws InvalidMappingKeyException, DeliveryIncompleteException {
        // then
        completeAssemblyLine.addProduction(completeAssemblyProduction);
        completeAssemblyLine.startNext();
        completeAssemblyLine.advance();
        completeAssemblyLine.advance();
        completeAssemblyLine.advance();

        BDDMockito.verify(completeAssemblyProduction).ship();
    }

    @Test
    public void givenValidProduction_whenAdvance_thenCarProductionRepoRemoves()
            throws InvalidMappingKeyException, DeliveryIncompleteException {

        // then
        when(completeAssemblyProduction.getProductionId()).thenReturn(productionId);
        completeAssemblyLine.addProduction(completeAssemblyProduction);
        completeAssemblyLine.startNext();
        completeAssemblyLine.advance();
        completeAssemblyLine.advance();
        completeAssemblyLine.advance();

        BDDMockito.verify(carProductionRepository).remove(productionId);
    }

    @Test
    public void givenValidProduction_whenAdvance_thenBatteryProductionRepoRemoves()
            throws InvalidMappingKeyException, DeliveryIncompleteException {
        // then
        when(completeAssemblyProduction.getProductionId()).thenReturn(productionId);
        completeAssemblyLine.addProduction(completeAssemblyProduction);
        completeAssemblyLine.startNext();
        completeAssemblyLine.advance();
        completeAssemblyLine.advance();
        completeAssemblyLine.advance();

        BDDMockito.verify(batteryProductionRepository).remove(productionId);
    }

}
