package ca.ulaval.glo4003.evulution.infrastructure.assemblyline.battery;

import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProduction;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.BatteryProductionRepositoryInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.BDDMockito;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BatteryProductionRepositoryInMemoryTest {

    private BatteryProductionRepositoryInMemory batteryRepositoryInMemory;

    @Mock
    private BatteryProduction batteryProduction;

    @Mock
    private ProductionId productionId;

    @BeforeEach
    public void setUp() {
        this.batteryRepositoryInMemory = new BatteryProductionRepositoryInMemory();
    }

    @Test
    public void givenABatteryProduction_whenAdd_thenShouldReturnTheBatteryProduction() {
        // given
        BDDMockito.given(this.batteryProduction.getProductionId()).willReturn(productionId);

        // when
        this.batteryRepositoryInMemory.add(batteryProduction);

        // then
        assertEquals(this.batteryRepositoryInMemory.getAndSendToProduction().get(0), this.batteryProduction);
    }

    @Test
    public void givenABatteryProduction_whenAddThenRemove_thenShouldNotContainsANyBatteryProduction() {
        // given
        BDDMockito.given(this.batteryProduction.getProductionId()).willReturn(productionId);
        this.batteryRepositoryInMemory.add(this.batteryProduction);

        // when
        this.batteryRepositoryInMemory.remove(productionId);

        // then
        assertTrue(this.batteryRepositoryInMemory.getAndSendToProduction().isEmpty());
    }
}
