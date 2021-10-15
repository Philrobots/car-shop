package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLineFacade;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.car.ModelInformationDto;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;
import ca.ulaval.glo4003.evulution.infrastructure.mappers.JsonFileMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
public class VehicleAssemblerLineFacadeTest {

    private VehicleAssemblyLineFacade vehicleAssemblyLineFacade;
    private TransactionId A_TRANSACTION_ID = new TransactionId(123);
    private TransactionId ANOTHER_TRANSACTION_ID = new TransactionId(456);
    private String A_VEHICLE_TYPE = "Mercedes G wagon";
    private Map<String, Integer> PRODUCTION_TIME_BY_VEHICLE_TYPE = new HashMap<>();
    private int A_TIME = 12;
    private List<ModelInformationDto> modelInformationDtoList = JsonFileMapper.parseModels();

    @BeforeEach
    public void setUp() {
        vehicleAssemblyLineFacade = new VehicleAssemblyLineFacade(modelInformationDtoList);
    }

    @Test
    public void givenNewVehicleCommand_whenGetStatus_thenAssemblyStatusIsReceived() {
        // given
        givenNewVehicleCommand();

        // when
        AssemblyStatus status = vehicleAssemblyLineFacade.getStatus(A_TRANSACTION_ID);

        // then
        assertEquals(status, AssemblyStatus.RECEIVED);
    }

     @Test public void givenNewVehicleCommandThatIsAdvanced_whenGetStatus_thenAssemblyStatusIsReceived() {
        // given
        givenNewVehicleCommand(); vehicleAssemblyLineFacade.advance();

        // when
        AssemblyStatus status = vehicleAssemblyLineFacade.getStatus(A_TRANSACTION_ID);
     
       // then
          assertEquals(status, AssemblyStatus.IN_PROGRESS);
    }
     

    private void givenNewVehicleCommand() {
        PRODUCTION_TIME_BY_VEHICLE_TYPE.put(A_VEHICLE_TYPE, A_TIME);
        vehicleAssemblyLineFacade.newVehicleCommand(A_TRANSACTION_ID, A_VEHICLE_TYPE);
    }

    private void givenTwoNewVehicleCommand() {
        PRODUCTION_TIME_BY_VEHICLE_TYPE.put(A_VEHICLE_TYPE, A_TIME);
        vehicleAssemblyLineFacade.newVehicleCommand(A_TRANSACTION_ID, A_VEHICLE_TYPE);
        vehicleAssemblyLineFacade.newVehicleCommand(ANOTHER_TRANSACTION_ID, A_VEHICLE_TYPE);
    }

}
