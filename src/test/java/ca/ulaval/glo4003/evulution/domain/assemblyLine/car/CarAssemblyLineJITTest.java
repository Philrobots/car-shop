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
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

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

    @Mock
    CarAssemblyLine otherAssemblyLine;

    @Mock
    CarProduction mockedCarProduction;

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

    @Test
    public void whenTransferAssemblyLine_thenCallOtherAssemblyLine() {
        // when
        this.carAssemblyLineJIT.transferAssemblyLine(otherAssemblyLine);

        //then
        verify(otherAssemblyLine).getIsBatteryInFire();
        verify(otherAssemblyLine).getWaitingList();
    }

    @Test
    public void whenCarFinished_CarProductionRepoAdds() {
        BDDMockito.given(mockedCarProduction.advance(carAssemblyAdapter)).willReturn(true);
        BDDMockito.given(carAssemblyLineJITTypeSelector.getNextCarProduction()).willReturn(mockedCarProduction);

        this.carAssemblyLineJIT.advance();

        verify(carProductionRepository).add(mockedCarProduction);
    }

    @Test
    public void givenWaitingListIsNotEmpty_whenCarFinished_CarProductionAddsCarCommand() throws CarNotAssociatedWithManufactureException {
        BDDMockito.given(mockedCarProduction.advance(carAssemblyAdapter)).willReturn(true);
        BDDMockito.given(carAssemblyLineJITTypeSelector.getNextCarProduction()).willReturn(mockedCarProduction);
        BDDMockito.given(carProductionRepository.replaceCarProductionWithoutManufactureIfItHasBeenMade(carProduction)).willReturn(false);

        this.carAssemblyLineJIT.addProduction(carProduction);
        this.carAssemblyLineJIT.advance();

        verify(mockedCarProduction).newCarCommand(carAssemblyAdapter);
    }
}
