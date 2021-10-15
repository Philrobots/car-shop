package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyFacade;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleAssemblyLineTest {

    private static final int ZERO_SECONDS = 0;
    private static final int AN_INT = 1;
    private static final TransactionId A_TRANSACTION_ID = new TransactionId(AN_INT);
    private static final String A_CAR_NAME = "name";

    private VehicleAssemblyLine vehicleAssemblyLine;

    @Mock
    private VehicleAssemblyFacade vehicleAssemblyFacade;

    @Mock
    private Car car;

    @BeforeEach
    public void setup() {
        vehicleAssemblyLine = new VehicleAssemblyLine(vehicleAssemblyFacade, ZERO_SECONDS);
        when(vehicleAssemblyFacade.getStatus(A_TRANSACTION_ID)).thenReturn(AssemblyStatus.ASSEMBLED);
        when(car.getName()).thenReturn(A_CAR_NAME);
    }

    @Test
    public void whenCompleteVehicleCommand_thenShouldSetCarAsAssembled() {
        // when
        vehicleAssemblyLine.completeVehicleCommand(A_TRANSACTION_ID, car);

        // then
        verify(car).setCarAsAssembled();
    }

    @Test
    public void whenCompleteVehicleCommand_thenCallsNewBatteryCommand() {
        // when
        vehicleAssemblyLine.completeVehicleCommand(A_TRANSACTION_ID, car);

        // then
        verify(vehicleAssemblyFacade).newVehicleCommand(A_TRANSACTION_ID, A_CAR_NAME);
    }

}