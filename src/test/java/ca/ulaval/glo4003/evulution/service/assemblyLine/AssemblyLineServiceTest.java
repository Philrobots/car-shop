package ca.ulaval.glo4003.evulution.service.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteCarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.production.ProductionAssembler;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AssemblyLineServiceTest {

    @Mock
    private VehicleAssemblyLine vehicleAssemblyLine;

    @Mock
    private BatteryAssemblyLine batteryAssemblyLine;

    @Mock
    private CompleteCarAssemblyLine completeCarAssemblyLine;

    @Mock
    private Sale sale;

    @Mock
    private TransactionId transactionId;

    @Mock
    private Car car;

    @Mock
    private Battery battery;

    @Mock
    private Delivery delivery;

    private AssemblyLineService assemblyLineService;

    @BeforeEach
    public void setUp() {
        assemblyLineService = new AssemblyLineService(vehicleAssemblyLine, batteryAssemblyLine, completeCarAssemblyLine,
                new ProductionAssembler());
    }

    // @Test
    // public void givenASale_whenCompleteVehicleCommand_thenShouldCallTheVehicleAssemblyLineToAddVehicle() {
    // // given
    // BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
    // BDDMockito.given(sale.getCar()).willReturn(car);
    //
    // // when
    // assemblyLineService.completeVehicleCommand(sale);
    //
    // // then
    // Mockito.verify(vehicleAssemblyLine).completeVehicleCommand(transactionId, car);
    // }
    //
    // @Test
    // public void givenAnSale_whenCompleteVehicleCommand_thenShouldCallTheBatteryAssemblyLine() {
    // // given
    // BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
    // BDDMockito.given(sale.getBattery()).willReturn(battery);
    //
    // // when
    // assemblyLineService.completeVehicleCommand(sale);
    //
    // // then
    // Mockito.verify(batteryAssemblyLine).completeBatteryCommand(transactionId, battery);
    // }
    //
    // @Test
    // public void givenAnSale_whenCompleteVehicleCommand_thenShouldCompleteTheVehicleCommand() {
    // // when
    // assemblyLineService.completeVehicleCommand(sale);
    //
    // // then
    // Mockito.verify(completeCarAssemblyLine).completeCarCommand(sale);
    // }

}
