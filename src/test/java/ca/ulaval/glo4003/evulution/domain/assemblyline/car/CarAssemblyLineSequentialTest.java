package ca.ulaval.glo4003.evulution.domain.assemblyline.car;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.email.ProductionLineEmailNotifier;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionAssociatedWithManufacture;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionRepository;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.email.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarAssemblyLineSequentialTest {
    private static final int AN_INT = 1;
    private static final String AN_EMAIL = "email@email.com";
    private static final Integer A_PRODUCTION_TIME = 2;
    private final ProductionId A_PRODUCTION_ID = new ProductionId();
    private final ProductionId ANOTHER_PRODUCTION_ID = new ProductionId();
    private final String A_CAR_STYLE = "BLUE";

    private final CarProduction VEHICLE_PRODUCTION = new CarProductionAssociatedWithManufacture(A_PRODUCTION_ID, A_CAR_STYLE, A_PRODUCTION_TIME);
    private final CarProduction ANOTHER_VEHICLE_PRODUCTION = new CarProductionAssociatedWithManufacture(ANOTHER_PRODUCTION_ID, A_CAR_STYLE, A_PRODUCTION_TIME);


    private CarAssemblyLineSequential carAssemblyLine;

    @Mock
    private CarAssemblyAdapter carAssemblyAdapter;

    @Mock
    private CarProductionRepository carProductionRepository;

    @Mock
    private AssemblyLineMediator assemblyLineMediator;

    @Mock
    private Email email;

    @Mock
    private ProductionLineEmailNotifier productionLineEmailNotifier;

    @BeforeEach
    public void setup() {
        carAssemblyLine = new CarAssemblyLineSequential(carAssemblyAdapter, carProductionRepository,
                productionLineEmailNotifier);
        carAssemblyLine.setMediator(assemblyLineMediator);
    }

     @Test
     public void givenMultipleProductions_whenAddProduction_thenCreatesVehicleCommandOnce() {
         // when
         carAssemblyLine.addProduction(VEHICLE_PRODUCTION);
         carAssemblyLine.addProduction(VEHICLE_PRODUCTION);

         // then
         verify(carAssemblyAdapter, times(1)).newVehicleCommand(A_PRODUCTION_ID, A_CAR_STYLE);
     }

     @Test
     public void givenNoProduction_whenAdvance_thenDoesNothing() {
         // when
         carAssemblyLine.advance();

         // then
         verify(carAssemblyAdapter, times(0)).newVehicleCommand(A_PRODUCTION_ID, A_CAR_STYLE);
         verify(carAssemblyAdapter, times(0)).advance();
         verify(assemblyLineMediator, times(0)).notify(CarAssemblyLine.class);
     }

     @Test
     public void givenAProduction_whenAdvance_thenGetsStatusAndAdvance() {
         // given
         carAssemblyLine.addProduction(VEHICLE_PRODUCTION);

         // when
         carAssemblyLine.advance();

         // then
         verify(carAssemblyAdapter, times(1)).newVehicleCommand(A_PRODUCTION_ID, A_CAR_STYLE);
         verify(carAssemblyAdapter, times(1)).advance();
     }

     @Test
     public void whenAdvance_thenAddsInVehicleRepository() {
         // given
         carAssemblyLine.addProduction(VEHICLE_PRODUCTION);

         // when
         carAssemblyLine.advance();

         // then
         verify(carProductionRepository).add(VEHICLE_PRODUCTION);
     }

     @Test
     public void givenTwoProductions_whenAdvance_thenGetsStatusNotifiesAndAddsCommand() {
        // given
         carAssemblyLine.addProduction(VEHICLE_PRODUCTION);
         carAssemblyLine.addProduction(ANOTHER_VEHICLE_PRODUCTION);

         // when
         carAssemblyLine.advance();

         // then
         verify(carAssemblyAdapter, times(1)).newVehicleCommand(A_PRODUCTION_ID, A_CAR_STYLE);
         verify(carAssemblyAdapter, times(1)).advance();
     }
}
