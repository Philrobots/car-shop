package ca.ulaval.glo4003.evulution.api.Production;

import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.productions.ProductionResource;
import ca.ulaval.glo4003.evulution.api.productions.assembler.SwitchProductionsDtoAssembler;
import ca.ulaval.glo4003.evulution.service.assemblyLine.AssemblyLineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductionResourceTest {

    private ProductionResource productionResource;

    @Mock
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    @Mock
    private AssemblyLineService assemblyLineService;

    @Mock
    private SwitchProductionsDtoAssembler switchProductionsDtoAssembler;

    @BeforeEach
    public void setUp() {
        productionResource = new ProductionResource(httpExceptionResponseAssembler, assemblyLineService,
                switchProductionsDtoAssembler);
    }

    @Test
    public void whenShutDownAssemblyLine_thenShouldCallTheServiceToShutDownAssemblyLine() {
        productionResource.shutdownProductionLines();

        Mockito.verify(assemblyLineService).shutdown();
    }

    @Test
    public void whenReactivateAssemblyLine_thenShouldCallTheServiceToReactivateAssemblyLine() {
        productionResource.reactivateProductionLines();

        Mockito.verify(assemblyLineService).reactivate();
    }
}
