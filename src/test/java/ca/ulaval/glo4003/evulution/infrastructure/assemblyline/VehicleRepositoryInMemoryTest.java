package ca.ulaval.glo4003.evulution.infrastructure.assemblyline;

import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class VehicleRepositoryInMemoryTest {

    private VehicleRepositoryInMemory vehicleRepositoryInMemory;

    @Mock
    private ProductionId productionId;

    @BeforeEach
    public void setUp() {
        this.vehicleRepositoryInMemory = new VehicleRepositoryInMemory();
    }

    @Test
    public void givenEmptyVehicleRepositoryInMemory_whenRemove_thenThrowsInvalidMappingKeyException() {
        // when
        Executable result = () -> vehicleRepositoryInMemory.remove(productionId);

        // then
        assertThrows(InvalidMappingKeyException.class, result);
    }
}
