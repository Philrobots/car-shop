package ca.ulaval.glo4003.evulution.service.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteCarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle.VehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.production.BatteryProduction;
import ca.ulaval.glo4003.evulution.domain.production.ProductionAssembler;
import ca.ulaval.glo4003.evulution.domain.production.VehicleProduction;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AssemblyLineServiceTest {
    @Mock
    private VehicleProduction vehicleProduction;

    @Mock
    private BatteryProduction batteryProduction;

    @Mock
    private ProductionLine productionLine;

    @Mock
    private ProductionAssembler productionAssembler;

    @Mock
    private Sale sale;

    private AssemblyLineService assemblyLineService;

    @BeforeEach
    public void setUp() {
        assemblyLineService = new AssemblyLineService(productionAssembler, productionLine);
    }

    @Test
    public void whenAddSaleToAssemblyLines_thenVehicleAssemblerAssemblesVehicle() {
        // given
        Mockito.when(productionAssembler.assembleVehicleProductionFromSale(sale)).thenReturn(vehicleProduction);
        Mockito.when(productionAssembler.assembleBatteryProductionFromSale(sale)).thenReturn(batteryProduction);

        // when
        assemblyLineService.addSaleToAssemblyLines(sale);

        // then
        Mockito.verify(productionLine).addSaleToAssemblyLines(vehicleProduction, batteryProduction, sale);
    }

    @Test
    public void whenAdvanceAssemblyLines_thenProductionLineIsCalled() {
        // when
        assemblyLineService.advanceAssemblyLines();

        Mockito.verify(productionLine).advanceAssemblyLines();
    }

    @Test
    public void whenShutdown_thenProductionLineIsCalled() {
        // when
        assemblyLineService.shutdown();

        Mockito.verify(productionLine).shutdown();
    }

    @Test
    public void whenReactivate_thenProductionLineIsCalled() {
        // when
        assemblyLineService.reactivate();

        Mockito.verify(productionLine).reactivate();
    }
}