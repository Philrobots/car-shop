package ca.ulaval.glo4003.evulution.api.Production;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.productions.ProductionRessource;
import ca.ulaval.glo4003.evulution.service.assemblyLine.AssemblyLineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductionResourceTest {

    private ProductionRessource productionRessource;

    @Mock
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    @Mock
    private AssemblyLineService assemblyLineService;

    @BeforeEach
    public void setUp() {
        productionRessource = new ProductionRessource(httpExceptionResponseAssembler, assemblyLineService);
    }

    @Test
    public void whenShutDownAssemblyLine_thenShouldCallTheServiceToShutDownAssemblyLine() {
        productionRessource.shutdownProductionLines();

        Mockito.verify(assemblyLineService).shutdown();
    }

    @Test
    public void whenReactivateAssemblyLine_thenShouldCallTheServiceToReactivateAssemblyLine() {
        productionRessource.reactivateProductionLines();

        Mockito.verify(assemblyLineService).reactivate();
    }
}
