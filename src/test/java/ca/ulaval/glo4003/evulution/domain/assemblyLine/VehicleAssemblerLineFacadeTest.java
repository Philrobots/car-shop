package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.car_manufacture.BasicVehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.car_manufacture.BuildStatus;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLineFacade;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.car.ModelInformationDto;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;
import ca.ulaval.glo4003.evulution.infrastructure.mappers.JsonFileMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
public class VehicleAssemblerLineFacadeTest {

    private VehicleAssemblyLineFacade vehicleAssemblyLineFacade;
    private TransactionId A_TRANSACTION_ID = new TransactionId(123);
    private TransactionId ANOTHER_TRANSACTION_ID = new TransactionId(456);
    private String A_VEHICLE_TYPE = "Vandry";
    private Map<String, Integer> PRODUCTION_TIME_BY_VEHICLE_TYPE = new HashMap<>();
    private int A_TIME = 1;
    private BuildStatus A_BUILD_STATUS = BuildStatus.ASSEMBLED;
    private List<ModelInformationDto> modelInformationDtoList = JsonFileMapper.parseModels();

    @Mock
    private BasicVehicleAssemblyLine basicVehicleAssemblyLine;

    @BeforeEach
    public void setUp() {
        vehicleAssemblyLineFacade = new VehicleAssemblyLineFacade(basicVehicleAssemblyLine, modelInformationDtoList);
    }

    @Test
    public void whenAdvance_thenAdvanceIsCalled() {
        // when
        vehicleAssemblyLineFacade.advance();

        // then
        verify(basicVehicleAssemblyLine, times(1)).advance();
    }

    @Test
    public void whenGetStatus_thenGetBuildStatusIsCalled() {
        // given
        when(basicVehicleAssemblyLine.getBuildStatus(any())).thenReturn(A_BUILD_STATUS);

        // when
        vehicleAssemblyLineFacade.getStatus(A_TRANSACTION_ID);

        // then
        verify(basicVehicleAssemblyLine).getBuildStatus(any());
    }

    @Test
    public void whenNewVehicleCommand_thenNewCarCommandIsCalledWithGoodParameters() {
        // when
        vehicleAssemblyLineFacade.newVehicleCommand(A_TRANSACTION_ID, A_VEHICLE_TYPE);

        // then
        verify(basicVehicleAssemblyLine).newCarCommand(any(), any());

    }
}
