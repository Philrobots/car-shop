package ca.ulaval.glo4003.evulution.api.sale;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParameterException;
import ca.ulaval.glo4003.evulution.api.mappers.HTTPExceptionMapper;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseBatteryDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseVehicleDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.InvoiceDto;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.service.sale.SaleService;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SaleResourceImplTest {
    private final int A_TRANSACTION_ID = 2;
    private static final Integer BAD_INPUT_PARAMETERS_ERROR_CODE = 400;
    private static final String BAD_INPUT_PARAMETERS_ERROR_MESSAGE = "bad input parameter";

    private SaleResourceImpl saleResourceImpl;
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    @Mock
    private SaleService saleService;

    @Mock
    private TokenDtoAssembler tokenDtoAssembler;

    @Mock
    private TokenDto tokenDto;

    @Mock
    private ChooseVehicleDto chooseVehicleDto;

    @Mock
    private ChooseBatteryDto chooseBatteryDto;

    @Mock
    private InvoiceDto invoiceDto;

    @Mock
    private ContainerRequestContext containerRequestContext;

    @Mock
    private ConstraintsValidator constraintsValidator;

    @BeforeEach
    private void setUp() {
        httpExceptionResponseAssembler = new HTTPExceptionResponseAssembler(new HTTPExceptionMapper());
        saleResourceImpl = new SaleResourceImpl(saleService, tokenDtoAssembler, httpExceptionResponseAssembler,
                constraintsValidator);
    }

    @Test
    public void givenInvalidConstraints_whenChooseVehicle_thenReturnsAccordingErrorResponse() {
        // given
        Mockito.doThrow(BadInputParameterException.class).when(constraintsValidator).validate(chooseVehicleDto);

        // when
        Response response = saleResourceImpl.chooseVehicle(A_TRANSACTION_ID, chooseVehicleDto);

        // then
        assertEquals(response.getStatus(), BAD_INPUT_PARAMETERS_ERROR_CODE);
        assertEquals(response.getEntity(), BAD_INPUT_PARAMETERS_ERROR_MESSAGE);
    }

    @Test
    public void givenInvalidConstraints_whenChooseBattery_thenReturnsAccordingErrorResponse() {
        // given
        Mockito.doThrow(BadInputParameterException.class).when(constraintsValidator).validate(chooseBatteryDto);

        // when
        Response response = saleResourceImpl.chooseBattery(A_TRANSACTION_ID, chooseBatteryDto);

        // then
        assertEquals(response.getStatus(), BAD_INPUT_PARAMETERS_ERROR_CODE);
        assertEquals(response.getEntity(), BAD_INPUT_PARAMETERS_ERROR_MESSAGE);
    }

    @Test
    public void whenInitSale_thenSaleServiceIsCalled() {
        // given
        String emptyRequestContextAuthorization = null;
        BDDMockito.given(tokenDtoAssembler.assembleFromString(emptyRequestContextAuthorization)).willReturn(tokenDto);

        // when
        saleResourceImpl.initSale(containerRequestContext);

        // then
        Mockito.verify(saleService).initSale(tokenDto);
    }

    @Test
    public void whenChooseVehicle_thenSaleServiceIsCalled() {
        // when
        saleResourceImpl.chooseVehicle(A_TRANSACTION_ID, chooseVehicleDto);

        // then
        Mockito.verify(saleService).chooseVehicle(A_TRANSACTION_ID, chooseVehicleDto);
    }

    @Test
    public void whenChooseBattery_thenSaleServiceIsCalled() {
        // when
        saleResourceImpl.chooseBattery(A_TRANSACTION_ID, chooseBatteryDto);

        // then
        Mockito.verify(saleService).chooseBattery(A_TRANSACTION_ID, chooseBatteryDto);
    }

    @Test
    public void givenInvalidConstraints_whenCompleteSale_thenReturnsAccordingErrorResponse() {
        // given
        Mockito.doThrow(BadInputParameterException.class).when(constraintsValidator).validate(invoiceDto);

        // when
        Response response = saleResourceImpl.completeSale(A_TRANSACTION_ID, invoiceDto);

        // then
        assertEquals(response.getStatus(), BAD_INPUT_PARAMETERS_ERROR_CODE);
        assertEquals(response.getEntity(), BAD_INPUT_PARAMETERS_ERROR_MESSAGE);
    }

    @Test
    public void whenCompleteSale_thenSaleServiceIsCalled() {
        // when
        saleResourceImpl.completeSale(A_TRANSACTION_ID, invoiceDto);

        // then
        Mockito.verify(saleService).completeSale(A_TRANSACTION_ID, invoiceDto);
    }
}
