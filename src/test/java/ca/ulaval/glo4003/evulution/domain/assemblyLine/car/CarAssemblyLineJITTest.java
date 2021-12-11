package ca.ulaval.glo4003.evulution.domain.assemblyline.car;

import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionAssociatedWithManufacture;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionRepository;
import ca.ulaval.glo4003.evulution.domain.production.exceptions.CarNotAssociatedWithManufactureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarAssemblyLineJITTest {

    CarAssemblyLineJIT carAssemblyLineJIT;

    @Mock
    AssemblyLineMediator assemblyLineMediator;

    @Mock
    CarProductionRepository carProductionRepository;

    @Mock
    CarAssemblyAdapter carAssemblyAdapter;

    @Mock
    CarAssemblyLineJITTypeSelector carAssemblyLineJITTypeSelector;

    ProductionId productionId = new ProductionId();
    String carStyle = "jaime larchitecture";
    int carProductionTime = 1;

    CarProduction carProduction = new CarProductionAssociatedWithManufacture(productionId, carStyle, carProductionTime);

    @BeforeEach
    void setup() {
        carAssemblyLineJIT = new CarAssemblyLineJIT(assemblyLineMediator, carAssemblyAdapter, carProductionRepository,
                carAssemblyLineJITTypeSelector);
    }

    @Test
    public void whenAddProduction_thenCarProductionRepositoryIsCalled() throws CarNotAssociatedWithManufactureException {
        // when
        this.carAssemblyLineJIT.addProduction(carProduction);

        // then
        verify(this.carProductionRepository, times(1)).replaceCarProductionWithoutManufactureIfItHasBeenMade(carProduction);
    }
}
