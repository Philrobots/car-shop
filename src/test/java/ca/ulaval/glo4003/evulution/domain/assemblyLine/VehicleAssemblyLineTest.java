package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle.VehicleAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle.VehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle.VehicleRepository;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
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
    private static final VehicleProduction VEHICLE_PRODUCTION = new VehicleProduction(A_TRANSACTION_ID, A_CAR_NAME);

    private VehicleAssemblyLine vehicleAssemblyLine;

    @Mock
    private VehicleAssemblyAdapter vehicleAssemblyAdapter;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private AssemblyLineMediator assemblyLineMediator;

    @BeforeEach
    public void setup() {
        vehicleAssemblyLine = new VehicleAssemblyLine(vehicleAssemblyAdapter, vehicleRepository);
        vehicleAssemblyLine.setMediator(assemblyLineMediator);
    }

    @Test
    public void givenMultipleProductions_whenAddProduction_thenCreatesVehicleCommandOnce() {
        // when
        vehicleAssemblyLine.addProduction(VEHICLE_PRODUCTION);
        vehicleAssemblyLine.addProduction(VEHICLE_PRODUCTION);

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
        verify(vehicleAssemblyAdapter, times(0)).advance();
        verify(assemblyLineMediator, times(0)).notify(VehicleAssemblyLine.class);
    }

    @Test
    public void givenAProduction_whenAdvance_thenGetsStatusAndNotifies() {
        // given
        vehicleAssemblyLine.addProduction(VEHICLE_PRODUCTION);
        when(vehicleAssemblyAdapter.getStatus(A_TRANSACTION_ID)).thenReturn(AssemblyStatus.ASSEMBLED);

        // when
        vehicleAssemblyLine.advance();

        // then
        verify(vehicleAssemblyAdapter, times(1)).newVehicleCommand(A_TRANSACTION_ID, A_CAR_NAME);
        verify(vehicleAssemblyAdapter, times(1)).getStatus(A_TRANSACTION_ID);
        verify(vehicleAssemblyAdapter, times(1)).advance();
        verify(assemblyLineMediator, times(1)).notify(VehicleAssemblyLine.class);
    }

    @Test
    public void whenAdvance_thenAddsInVehicleRepository() {
        // given
        when(vehicleAssemblyAdapter.getStatus(A_TRANSACTION_ID)).thenReturn(AssemblyStatus.ASSEMBLED);
        vehicleAssemblyLine.addProduction(VEHICLE_PRODUCTION);

        // when
        vehicleAssemblyLine.advance();

        // then
        verify(vehicleRepository).add(A_CAR_NAME, VEHICLE_PRODUCTION);
    }

    @Test
    public void givenTwoProductions_whenAdvance_thenGetsStatusNotifiesAndAddsCommand() {
        // given
        vehicleAssemblyLine.addProduction(VEHICLE_PRODUCTION);
        vehicleAssemblyLine.addProduction(VEHICLE_PRODUCTION);
        when(vehicleAssemblyAdapter.getStatus(A_TRANSACTION_ID)).thenReturn(AssemblyStatus.ASSEMBLED);

        // when
        vehicleAssemblyLine.advance();

        // then
        verify(vehicleAssemblyAdapter, times(2)).newVehicleCommand(A_TRANSACTION_ID, A_CAR_NAME);
        verify(vehicleAssemblyAdapter, times(1)).getStatus(A_TRANSACTION_ID);
        verify(vehicleAssemblyAdapter, times(1)).advance();
        verify(assemblyLineMediator, times(1)).notify(VehicleAssemblyLine.class);
    }
}
