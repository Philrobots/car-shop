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
public class BatteryRepositoryInMemoryTest {

    private BatteryRepositoryInMemory batteryRepositoryInMemory;

    @Mock
    private ProductionId productionId;

    @BeforeEach
    public void setUp() {
        this.batteryRepositoryInMemory = new BatteryRepositoryInMemory();
    }

    @Test
    public void givenEmptyBatteryRepositoryInMemory_whenRemove_thenThrowsInvalidMappingKeyException() {
        // when
        Executable result = () -> batteryRepositoryInMemory.remove(productionId);

        // then
        assertThrows(InvalidMappingKeyException.class, result);
    }
}
