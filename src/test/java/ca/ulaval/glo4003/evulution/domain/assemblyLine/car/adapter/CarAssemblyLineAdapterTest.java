package ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter;

import ca.ulaval.glo4003.evulution.car_manufacture.BasicVehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.car_manufacture.BuildStatus;
import ca.ulaval.glo4003.evulution.car_manufacture.VehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyLineAdapter;
import ca.ulaval.glo4003.evulution.domain.car.ModelInformationDto;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarAssemblyLineAdapterTest {
    private static final ProductionId A_PRODUCTION_ID = new ProductionId();
    private static final String A_TIME_TO_PRODUCE = "2";
    private static final String A_VEHICLE_TYPE = "Vandry";

    private CarAssemblyLineAdapter vehicleAssemblyLineAdapter;
    private ModelInformationDto modelInformationDto = new ModelInformationDto();

    @Mock
    private BasicVehicleAssemblyLine basicVehicleAssemblyLine;

    @BeforeEach
    public void setUp() {
        modelInformationDto.time_to_produce = A_TIME_TO_PRODUCE;
        modelInformationDto.style = A_VEHICLE_TYPE;
        List<ModelInformationDto> modelInformationDtos = List.of(modelInformationDto);
        vehicleAssemblyLineAdapter = new CarAssemblyLineAdapter(basicVehicleAssemblyLine, modelInformationDtos);
    }

    @Test
    public void whenAdvance_thenVehicleAssemblyLineAdvances() {
        vehicleAssemblyLineAdapter.advance();

        Mockito.verify(basicVehicleAssemblyLine).advance();
    }

    @Test
    public void whenGetStatus_thenGetBuildStatusIsCalled() {
        // given
        when(basicVehicleAssemblyLine.getBuildStatus(any())).thenReturn(BuildStatus.RECEIVED);

        // when
        vehicleAssemblyLineAdapter.isAssembled(A_PRODUCTION_ID);

        // then
        verify(basicVehicleAssemblyLine).getBuildStatus(any());
    }

    @Test
    public void whenNewVehicleCommand_thenNewCarCommandIsCalledWithGoodParameters() {
        // when
        vehicleAssemblyLineAdapter.newVehicleCommand(A_PRODUCTION_ID, A_VEHICLE_TYPE);

        // then
        verify(basicVehicleAssemblyLine).newCarCommand(any(), any());
    }

}
