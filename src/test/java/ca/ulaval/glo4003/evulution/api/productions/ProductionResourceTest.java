package ca.ulaval.glo4003.evulution.api.productions;

import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.productions.assembler.SwitchProductionsDtoAssembler;
import ca.ulaval.glo4003.evulution.api.productions.dto.SwitchProductionsRequest;
import ca.ulaval.glo4003.evulution.service.assemblyLine.AssemblyLineService;
import ca.ulaval.glo4003.evulution.service.assemblyLine.dto.SwitchProductionsDto;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductionResourceTest {
    private static final Integer VALID_STATUS = 200;

    private ProductionResource productionResource;

    @Mock
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    @Mock
    private AssemblyLineService assemblyLineService;

    @Mock
    private SwitchProductionsDtoAssembler switchProductionsDtoAssembler;

    @Mock
    private SwitchProductionsRequest switchProductionsRequest;

    @Mock
    private SwitchProductionsDto switchProductionsDto;

    @BeforeEach
    public void setUp() {
        productionResource = new ProductionResource(httpExceptionResponseAssembler, assemblyLineService,
                switchProductionsDtoAssembler);
    }

    @Test
    public void whenShutdownAssemblyLine_thenAssemblyLineServiceShutdowns() {
        // when
        productionResource.shutdownProductionLines();

        // then
        Mockito.verify(assemblyLineService).shutdown();
    }

    @Test
    public void whenShutdownProductionLines_thenReturns200() {
        // when
        Response response = this.productionResource.shutdownProductionLines();

        // then
        Assertions.assertEquals(VALID_STATUS, response.getStatus());
    }

    @Test
    public void whenReactivateProductionLines_thenAssemblyLineServiceReactivates() {
        // when
        productionResource.reactivateProductionLines();

        // then
        Mockito.verify(assemblyLineService).reactivate();
    }

    @Test
    public void whenReactivateProductionLines_thenReturns200() {
        // when
        Response response = productionResource.reactivateProductionLines();

        // then
        Assertions.assertEquals(VALID_STATUS, response.getStatus());
    }

    @Test
    public void whenSwitchProductionLines_thenSwitchProductionsDtoAssemblesFromRequest() {
        // when
        this.productionResource.switchProductionLines(switchProductionsRequest);

        // then
        Mockito.verify(switchProductionsDtoAssembler).fromRequest(switchProductionsRequest);
    }

    @Test
    public void whenSwitchProductionLines_thenAssemblyLineServiceSwitchProductions() {
        // given
        BDDMockito.given(switchProductionsDtoAssembler.fromRequest(switchProductionsRequest))
                .willReturn(switchProductionsDto);

        // when
        this.productionResource.switchProductionLines(switchProductionsRequest);

        // then
        Mockito.verify(assemblyLineService).switchProductions(switchProductionsDto);
    }

    @Test
    public void whenSwitchProductionLines_thenReturns200() {
        // when
        Response response = productionResource.reactivateProductionLines();

        // then
        Assertions.assertEquals(VALID_STATUS, response.getStatus());
    }
}
