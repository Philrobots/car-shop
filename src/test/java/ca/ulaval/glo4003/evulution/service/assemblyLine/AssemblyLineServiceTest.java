package ca.ulaval.glo4003.evulution.service.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyLineType;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionSwitcher;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionType;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsNotShutdownException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsShutdownException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.InvalidAssemblyLineException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.InvalidAssemblyLineOrderException;
import ca.ulaval.glo4003.evulution.service.assemblyLine.dto.SwitchProductionsDto;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadOrderOfOperationsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AssemblyLineServiceTest {
    private static final String A_VALID_PRODUCTION_MODE = "JIT";
    private static final String A_VALID_LINE_TYPE = "COMPLETE";
    private static final String AN_INVALID_PRODUCTION_MODE = "abcd";
    private static final String AN_INVALID_LINE_TYPE = "abcd";

    private static final AssemblyLineType ASSEMBLY_LINE_TYPE = AssemblyLineType.COMPLETE;
    private static final ProductionType PRODUCTION_TYPE = ProductionType.JIT;

    @Mock
    private ProductionLine productionLine;

    @Mock
    private ProductionSwitcher productionSwitcher;

    @Mock
    private SwitchProductionsDto switchProductionsDto;

    private AssemblyLineService assemblyLineService;

    @BeforeEach
    public void setUp() {
        assemblyLineService = new AssemblyLineService(productionLine, productionSwitcher);
    }

    @Test
    public void whenShutdown_thenProductionLineShutsDown() throws AssemblyLineIsShutdownException {
        // when
        this.assemblyLineService.shutdown();

        // then
        Mockito.verify(productionLine).shutdown();
    }

    @Test
    public void givenAssemblyLineIsShutdownException_whenShutdown_thenThrowServiceBadOrderOfOperationsException()
            throws AssemblyLineIsShutdownException {
        // given
        BDDMockito.doThrow(AssemblyLineIsShutdownException.class).when(productionLine).shutdown();

        // when
        Executable shutdown = () -> this.assemblyLineService.shutdown();

        // then
        Assertions.assertThrows(ServiceBadOrderOfOperationsException.class, shutdown);
    }

    @Test
    public void whenReactivate_thenProductionLineReactivates() throws AssemblyLineIsNotShutdownException {
        // when
        assemblyLineService.reactivate();

        // then
        Mockito.verify(productionLine).reactivate();
    }

    @Test
    public void givenAssemblyLineIsNotShutdownException_whenReactivate_thenThrowServiceBadOrderOfOperationsException()
            throws AssemblyLineIsNotShutdownException {
        // given
        BDDMockito.doThrow(AssemblyLineIsNotShutdownException.class).when(productionLine).reactivate();

        // when
        Executable reactivate = () -> this.assemblyLineService.reactivate();

        // then
        Assertions.assertThrows(ServiceBadOrderOfOperationsException.class, reactivate);
    }

    @Test
    public void whenSwitchProductions_thenProductionSwitcherSwitchProduction()
            throws InvalidAssemblyLineException, InvalidAssemblyLineOrderException {
        // given
        switchProductionsDto.productionMode = A_VALID_PRODUCTION_MODE;
        switchProductionsDto.lineType = A_VALID_LINE_TYPE;

        // when
        this.assemblyLineService.switchProductions(switchProductionsDto);

        // then
        Mockito.verify(productionSwitcher).switchProduction(ASSEMBLY_LINE_TYPE, PRODUCTION_TYPE);
    }

    @Test
    public void givenIllegalArgumentException_whenSwitchProductions_thenThrowsServiceBadInputParameterException() {
        switchProductionsDto.productionMode = AN_INVALID_PRODUCTION_MODE;
        switchProductionsDto.lineType = AN_INVALID_LINE_TYPE;

        // when & then
        Assertions.assertThrows(ServiceBadInputParameterException.class,
                () -> this.assemblyLineService.switchProductions(switchProductionsDto));
    }

    @Test
    public void givenInvalidAssemblyLineException_whenSwitchProductions_thenThrowsServiceBadInputParameterException()
            throws InvalidAssemblyLineException, InvalidAssemblyLineOrderException {
        switchProductionsDto.productionMode = A_VALID_PRODUCTION_MODE;
        switchProductionsDto.lineType = A_VALID_LINE_TYPE;
        BDDMockito.doThrow(InvalidAssemblyLineException.class).when(productionSwitcher)
                .switchProduction(ASSEMBLY_LINE_TYPE, PRODUCTION_TYPE);

        // when & then
        Assertions.assertThrows(ServiceBadInputParameterException.class,
                () -> this.assemblyLineService.switchProductions(switchProductionsDto));
    }

    @Test
    public void givenInvalidAssemblyLineOrderException_whenSwitchProductions_thenThrowsServiceBadOrderOfOperationsException()
            throws InvalidAssemblyLineException, InvalidAssemblyLineOrderException {
        switchProductionsDto.productionMode = A_VALID_PRODUCTION_MODE;
        switchProductionsDto.lineType = A_VALID_LINE_TYPE;
        BDDMockito.doThrow(InvalidAssemblyLineOrderException.class).when(productionSwitcher)
                .switchProduction(ASSEMBLY_LINE_TYPE, PRODUCTION_TYPE);

        // when & then
        Assertions.assertThrows(ServiceBadOrderOfOperationsException.class,
                () -> this.assemblyLineService.switchProductions(switchProductionsDto));
    }
}
