package ca.ulaval.glo4003.evulution.service.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteCarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLine;
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
    private VehicleAssemblyLine vehicleAssemblyLine;

    @Mock
    private BatteryAssemblyLine batteryAssemblyLine;

    @Mock
    private CompleteCarAssemblyLine completeCarAssemblyLine;

    @Mock
    private ProductionAssembler productionAssembler;

    @Mock
    private Sale sale;

    private AssemblyLineService assemblyLineService;

    @BeforeEach
    public void setUp() {
        assemblyLineService = new AssemblyLineService(vehicleAssemblyLine, batteryAssemblyLine, completeCarAssemblyLine,
                productionAssembler);
    }

    @Test
    public void whenAddSaleToAssemblyLines_thenVehicleAssemblyLineAddsVehicle() {
        // given
        Mockito.when(productionAssembler.assembleVehicleProductionFromSale(sale)).thenReturn(vehicleProduction);

        // when
        assemblyLineService.addSaleToAssemblyLines(sale);

        // then
        Mockito.verify(vehicleAssemblyLine).addProduction(vehicleProduction);
    }

    @Test
    public void whenAddSaleToAssemblyLines_thenBatteryAssemblyLineAddsBattery() {
        // given
        Mockito.when(productionAssembler.assembleBatteryProductionFromSale(sale)).thenReturn(batteryProduction);

        // when
        assemblyLineService.addSaleToAssemblyLines(sale);

        // then
        Mockito.verify(batteryAssemblyLine).addProduction(batteryProduction);
    }

    @Test
    public void whenAddSaleToAssemblyLine_thenCompletesTheVehicleCommand() {
        // when
        assemblyLineService.addSaleToAssemblyLines(sale);

        // then
        Mockito.verify(completeCarAssemblyLine).addCommand(sale);
    }

    @Test
    public void whenAdvanceAssemblyLines_thenVehicleAssemblyLineAdvanceIsCalled() {
        // when
        this.assemblyLineService.advanceAssemblyLines();

        // then
        Mockito.verify(vehicleAssemblyLine).advance();
    }

    @Test
    public void whenAdvanceAssemblyLines_thenBatteryAssemblyLineAdvanceIsCalled() {
        // when
        this.assemblyLineService.advanceAssemblyLines();

        // then
        Mockito.verify(batteryAssemblyLine).advance();
    }

    @Test
    public void whenAdvanceAssemblyLines_thenCompleteCarAssemblyLineAdvanceIsCalled() {
        // when
        this.assemblyLineService.advanceAssemblyLines();

        // then
        Mockito.verify(completeCarAssemblyLine).advance();
    }
}
