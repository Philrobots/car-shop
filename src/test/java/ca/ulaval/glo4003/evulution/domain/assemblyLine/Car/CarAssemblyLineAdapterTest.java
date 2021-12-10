package ca.ulaval.glo4003.evulution.domain.assemblyLine.Car;

import ca.ulaval.glo4003.evulution.car_manufacture.BasicVehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyLineAdapter;
import ca.ulaval.glo4003.evulution.domain.car.ModelInformationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CarAssemblyLineAdapterTest {
    private static final String A_TIME_TO_PRODUCE = "2";
    private static final String A_VEHICLE_TYPE = "Vandry";

    private CarAssemblyLineAdapter vehicleAssemblyLineAdapter;

    @Mock
    private BasicVehicleAssemblyLine basicVehicleAssemblyLine;

    @Mock
    private ModelInformationDto modelInformationDto;

    @BeforeEach
    public void setUp() {
        BDDMockito.given(modelInformationDto.time_to_produce).willReturn(A_TIME_TO_PRODUCE);
        BDDMockito.given(modelInformationDto.name).willReturn(A_VEHICLE_TYPE);
        List<ModelInformationDto> modelInformationDtos = List.of(modelInformationDto);
        vehicleAssemblyLineAdapter = new CarAssemblyLineAdapter(basicVehicleAssemblyLine, modelInformationDtos);
    }
    /*
     * @Test public void whenAdvance_thenVehicleAssemblyLineAdvances() { // when vehicleAssemblyLineAdapter.advance();
     *
     * // then Mockito.verify(basicVehicleAssemblyLine).advance(); }
     */

    // @Test
    // public void whenGetStatus_thenGetBuildStatusIsCalled() {
    // // given
    // when(basicVehicleAssemblyLine.getBuildStatus(any())).thenReturn(A_BUILD_STATUS);
    //
    // // when
    // vehicleAssemblyLineAdapter.getStatus(A_SALE_ID);
    //
    // // then
    // verify(basicVehicleAssemblyLine).getBuildStatus(any());
    // }
    //
    // @Test
    // public void whenNewVehicleCommand_thenNewCarCommandIsCalledWithGoodParameters() {
    // // when
    // vehicleAssemblyLineAdapter.newVehicleCommand(A_SALE_ID, A_VEHICLE_TYPE);
    //
    // // then
    // verify(basicVehicleAssemblyLine).newCarCommand(any(), any());
    //
    // }
}
