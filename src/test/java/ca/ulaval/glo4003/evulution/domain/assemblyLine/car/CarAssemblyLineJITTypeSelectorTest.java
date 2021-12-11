package ca.ulaval.glo4003.evulution.domain.assemblyline.car;

import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarAssemblyLineJITTypeSelectorTest {

    @Mock
    CarProductionFactory carProductionFactory;

    CarAssemblyLineJITTypeSelector carAssemblyLineJITTypeSelector;

    @BeforeEach
    void setup() {
        carAssemblyLineJITTypeSelector = new CarAssemblyLineJITTypeSelector(carProductionFactory);
    }

    @Test
    public void givenInitialState_whenGetNextCarProduction_thenReturnSubcompact() {
        this.carAssemblyLineJITTypeSelector.getNextCarProduction();
        verify(this.carProductionFactory).createCompact();
    }

    @Test
    public void givenSecondState_whenGetNextCarProduction_thenReturnLuxury() {
        this.carAssemblyLineJITTypeSelector.getNextCarProduction();
        this.carAssemblyLineJITTypeSelector.getNextCarProduction();
        verify(this.carProductionFactory).createLuxury();
    }

    @Test
    public void givenThirdState_whenGetNextCarProduction_thenReturnSubCompact() {
        this.carAssemblyLineJITTypeSelector.getNextCarProduction();
        this.carAssemblyLineJITTypeSelector.getNextCarProduction();
        this.carAssemblyLineJITTypeSelector.getNextCarProduction();
        verify(this.carProductionFactory).createSubCompact();
    }
}
