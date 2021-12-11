package ca.ulaval.glo4003.evulution.infrastructure.assemblyline.car;

import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.CarProductionRepositoryInMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CarProductionRepositoryInMemoryTest {

    @Mock
    private ProductionId productionId;

    @Mock
    private CarProduction carProduction;

    private CarProductionRepositoryInMemory vehicleRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        this.vehicleRepositoryInMemory = new CarProductionRepositoryInMemory();
    }

    @Test
    public void whenAddCarProduction_thenShouldNotThrow() {
        Assertions.assertDoesNotThrow(() -> this.vehicleRepositoryInMemory.add(carProduction));
    }

    @Test
    public void givenACarProduction_whenRemoveCarProduction_thenShouldNotThrow() {
        // given
        BDDMockito.given(this.carProduction.getProductionId()).willReturn(productionId);
        this.vehicleRepositoryInMemory.add(carProduction);

        // then
        Assertions.assertDoesNotThrow(() -> this.vehicleRepositoryInMemory.remove(productionId));
    }

}
