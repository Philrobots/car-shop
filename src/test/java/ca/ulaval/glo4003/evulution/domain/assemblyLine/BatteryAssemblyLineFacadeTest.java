package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.car_manufacture.BasicBatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryAssemblyLineFacade;
import ca.ulaval.glo4003.evulution.domain.car.BatteryInformationDto;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;
import ca.ulaval.glo4003.evulution.infrastructure.mappers.JsonFileMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BatteryAssemblyLineFacadeTest {

    @Mock
    private BasicBatteryAssemblyLine basicBatteryAssemblyLine;

    private List<BatteryInformationDto> batteries = JsonFileMapper.parseBatteries();

    private BatteryAssemblyLineFacade batteryAssemblyLineFacade;

    @BeforeEach
    public void setUp() {
        batteryAssemblyLineFacade = new BatteryAssemblyLineFacade(basicBatteryAssemblyLine, batteries);
    }

    @Test
    public void whenAdvance_thenShouldCallTheBasicBatteryAssemblyLineToAdvance() {
        // when
        batteryAssemblyLineFacade.advance();

        // then
        Mockito.verify(basicBatteryAssemblyLine).advance();
    }

}
