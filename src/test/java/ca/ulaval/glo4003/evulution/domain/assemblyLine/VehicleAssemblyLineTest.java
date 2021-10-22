package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.production.VehicleProduction;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleAssemblyLineTest {

    private static final int AN_INT = 1;
    private static final TransactionId A_TRANSACTION_ID = new TransactionId(AN_INT);
    private static final String A_CAR_NAME = "name";
    private static final VehicleProduction vehicleProduction = new VehicleProduction(A_TRANSACTION_ID, A_CAR_NAME);

    private VehicleAssemblyLine vehicleAssemblyLine;

    @Mock
    private VehicleAssemblyAdapter vehicleAssemblyAdapter;

    @Mock
    private AssemblyLineMediator assemblyLineMediator;

    @BeforeEach
    public void setup() {
        vehicleAssemblyLine = new VehicleAssemblyLine(vehicleAssemblyAdapter);
        vehicleAssemblyLine.setMediator(assemblyLineMediator);
    }

    @Test
    public void givenMultipleProductions_whenAddProduction_thenShouldCreateVehicleCommandOnce() {
        // when
        vehicleAssemblyLine.addProduction(vehicleProduction);
        vehicleAssemblyLine.addProduction(vehicleProduction);

        // then
        verify(vehicleAssemblyAdapter, times(1)).newVehicleCommand(A_TRANSACTION_ID, A_CAR_NAME);
    }

    @Test
    public void givenNoProduction_whenAdvance_thenDoesNothing() {
        // when
        vehicleAssemblyLine.advance();

        // then
        verify(vehicleAssemblyAdapter, times(0)).newVehicleCommand(A_TRANSACTION_ID, A_CAR_NAME);
        verify(vehicleAssemblyAdapter, times(0)).getStatus(A_TRANSACTION_ID);
        verify(assemblyLineMediator, times(0)).notify(VehicleAssemblyLine.class);
    }

    @Test
    public void givenOneProduction_whenAdvance_thenGetsStatusAndNotifies() {
        // given
        vehicleAssemblyLine.addProduction(vehicleProduction);
        when(vehicleAssemblyAdapter.getStatus(A_TRANSACTION_ID)).thenReturn(AssemblyStatus.ASSEMBLED);

        // when
        vehicleAssemblyLine.advance();

        // then
        verify(vehicleAssemblyAdapter, times(1)).newVehicleCommand(A_TRANSACTION_ID, A_CAR_NAME);
        verify(vehicleAssemblyAdapter, times(1)).getStatus(A_TRANSACTION_ID);
        verify(assemblyLineMediator, times(1)).notify(VehicleAssemblyLine.class);
    }

    @Test
    public void givenTwoProductions_whenAdvance_thenGetsStatusNotifiesAndAddsCommand() {
        // given
        vehicleAssemblyLine.addProduction(vehicleProduction);
        vehicleAssemblyLine.addProduction(vehicleProduction);
        when(vehicleAssemblyAdapter.getStatus(A_TRANSACTION_ID)).thenReturn(AssemblyStatus.ASSEMBLED);

        // when
        vehicleAssemblyLine.advance();

        // then
        verify(vehicleAssemblyAdapter, times(2)).newVehicleCommand(A_TRANSACTION_ID, A_CAR_NAME);
        verify(vehicleAssemblyAdapter, times(1)).getStatus(A_TRANSACTION_ID);
        verify(assemblyLineMediator, times(1)).notify(VehicleAssemblyLine.class);
    }
}