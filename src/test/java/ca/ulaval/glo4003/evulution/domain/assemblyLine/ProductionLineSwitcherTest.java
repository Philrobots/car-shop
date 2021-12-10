package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyLineType;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionSwitcher;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionType;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.InvalidAssemblyLineException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.InvalidAssemblyLineOrderException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediatorSwitcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductionLineSwitcherTest {

    private static final AssemblyLineType CAR_ASSEMBLY_LINE_TYPE = AssemblyLineType.CAR;
    private static final AssemblyLineType BATTERY_ASSEMBLY_LINE_TYPE = AssemblyLineType.BATTERY;
    private static final ProductionType JIT_PRODUCTION_TYPE = ProductionType.JIT;
    private static final ProductionType SEQUENTIAL_PRODUCTION_TYPE = ProductionType.SEQUENTIAL;

    private ProductionSwitcher productionSwitcher;

    @Mock
    private CarAssemblyLine carAssemblyLineJustInTime;

    @Mock
    private CarAssemblyLine carAssemblyLineSequential;

    @Mock
    private ProductionLine productionLine;

    @Mock
    private AssemblyLineMediatorSwitcher assemblyLineMediatorSwitcher;

    @BeforeEach
    public void setup() {
        this.productionSwitcher = new ProductionSwitcher(carAssemblyLineJustInTime, carAssemblyLineSequential,
                productionLine, assemblyLineMediatorSwitcher);
    }

    @Test
    public void givenBatteryAssemblyLineType_whenSwitchProduction_thenShouldThrow() {
        // when
        Executable switcher = () -> this.productionSwitcher.switchProduction(BATTERY_ASSEMBLY_LINE_TYPE,
                JIT_PRODUCTION_TYPE);

        // then
        Assertions.assertThrows(InvalidAssemblyLineException.class, switcher);
    }

    @Test
    public void givenCarAssemblyTypeWithProductionTypeToJITAndCurrentTypeToJIT_whenSwitchProduction_thenShouldThrow() {
        // given
        this.productionSwitcher.setCurrentProductionType(JIT_PRODUCTION_TYPE);

        // when
        Executable switcher = () -> this.productionSwitcher.switchProduction(CAR_ASSEMBLY_LINE_TYPE,
                JIT_PRODUCTION_TYPE);

        // then
        Assertions.assertThrows(InvalidAssemblyLineOrderException.class, switcher);
    }

    @Test
    public void givenCarAssemblyTypeWithProductionTypeToSequentialAndCurrentTypeToSequential_whenSwitchProduction_thenShouldThrow() {
        // given
        this.productionSwitcher.setCurrentProductionType(SEQUENTIAL_PRODUCTION_TYPE);

        // when
        Executable switcher = () -> this.productionSwitcher.switchProduction(CAR_ASSEMBLY_LINE_TYPE,
                SEQUENTIAL_PRODUCTION_TYPE);

        // then
        Assertions.assertThrows(InvalidAssemblyLineOrderException.class, switcher);
    }

    @Test
    public void givenCarAssemblyTypeWithProductionTypeToSequentialAndCurrentTypeToJIT_whenSwitchProduction_thenShouldChangeCurrentProductionType()
            throws InvalidAssemblyLineException, InvalidAssemblyLineOrderException {
        // given
        this.productionSwitcher.setCurrentProductionType(JIT_PRODUCTION_TYPE);

        // when
        this.productionSwitcher.switchProduction(CAR_ASSEMBLY_LINE_TYPE, SEQUENTIAL_PRODUCTION_TYPE);

        // then
        ProductionType currentProductionType = this.productionSwitcher.getCurrentProductionType();
        Assertions.assertEquals(currentProductionType, SEQUENTIAL_PRODUCTION_TYPE);
    }

    @Test
    public void givenCarAssemblyTypeWithProductionTypeToSequentialAndCurrentTypeToJIT_whenSwitchProduction_thenShouldSetSequentialAssemblyLine()
            throws InvalidAssemblyLineException, InvalidAssemblyLineOrderException {
        // given
        this.productionSwitcher.setCurrentProductionType(JIT_PRODUCTION_TYPE);

        // when
        this.productionSwitcher.switchProduction(CAR_ASSEMBLY_LINE_TYPE, SEQUENTIAL_PRODUCTION_TYPE);

        // then
        verify(this.productionLine).setCarAssemblyLine(carAssemblyLineSequential);
        verify(this.assemblyLineMediatorSwitcher).setCarAssemblyLine(carAssemblyLineSequential);
    }

    @Test
    public void givenCarAssemblyTypeWithProductionTypeToJITAndCurrentTypeToSequential_whenSwitchProduction_thenShouldHaveTheRightProductionType()
            throws InvalidAssemblyLineException, InvalidAssemblyLineOrderException {
        // given
        this.productionSwitcher.setCurrentProductionType(SEQUENTIAL_PRODUCTION_TYPE);

        // when
        this.productionSwitcher.switchProduction(CAR_ASSEMBLY_LINE_TYPE, JIT_PRODUCTION_TYPE);

        // then
        ProductionType currentProductionType = this.productionSwitcher.getCurrentProductionType();
        Assertions.assertEquals(currentProductionType, JIT_PRODUCTION_TYPE);
    }

    @Test
    public void givenCarAssemblyTypeWithProductionTypeToJITAndCurrentTypeToSequential_whenSwitchProduction_thenShouldSetSequentialAssemblyLine()
            throws InvalidAssemblyLineException, InvalidAssemblyLineOrderException {
        // given
        this.productionSwitcher.setCurrentProductionType(SEQUENTIAL_PRODUCTION_TYPE);

        // when
        this.productionSwitcher.switchProduction(CAR_ASSEMBLY_LINE_TYPE, JIT_PRODUCTION_TYPE);

        // then
        verify(this.productionLine).setCarAssemblyLine(carAssemblyLineJustInTime);
        verify(this.assemblyLineMediatorSwitcher).setCarAssemblyLine(carAssemblyLineJustInTime);
    }
}
