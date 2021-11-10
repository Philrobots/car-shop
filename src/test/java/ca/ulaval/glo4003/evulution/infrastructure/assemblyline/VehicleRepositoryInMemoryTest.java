package ca.ulaval.glo4003.evulution.infrastructure.assemblyline;

import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class VehicleRepositoryInMemoryTest {

    private static final String A_NAME = "A_NAME";

    private VehicleRepositoryInMemory vehicleRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        this.vehicleRepositoryInMemory = new VehicleRepositoryInMemory();
    }

    @Test
    public void givenEmptyVehicleRepositoryInMemory_whenRemove_thenThrowsInvalidMappingKeyException() {
        // when
        Executable result = () -> vehicleRepositoryInMemory.remove(A_NAME);

        // then
        assertThrows(InvalidMappingKeyException.class, result);
    }
}
