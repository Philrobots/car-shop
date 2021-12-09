package ca.ulaval.glo4003.evulution.api.sale;

import ca.ulaval.glo4003.evulution.api.authorization.assemblers.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.sale.assemblers.*;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseBatteryRequest;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseCarRequest;
import ca.ulaval.glo4003.evulution.api.sale.dto.InvoiceRequest;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.service.manufacture.ManufactureService;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.ChooseBatteryDto;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.ChooseCarDto;
import ca.ulaval.glo4003.evulution.service.sale.SaleService;
import ca.ulaval.glo4003.evulution.service.sale.dto.InvoiceDto;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SaleResourceTest {
    private static final Integer BAD_INPUT_PARAMETERS_ERROR_CODE = 400;
    private static final String BAD_INPUT_PARAMETERS_ERROR_MESSAGE = "bad input parameter";
    private final int A_SALE_ID = 2;

    private SaleResource saleResource;

    @Mock
    private SaleService saleService;

    @Mock
    private TokenDtoAssembler tokenDtoAssembler;

    @Mock
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    @Mock
    private ChooseCarDtoAssembler chooseCarDtoAssembler;

    @Mock
    private ChooseBatteryDtoAssembler chooseBatteryDtoAssembler;

    @Mock
    private EstimatedRangeResponseAssembler estimatedRangeResponseAssembler;

    @Mock
    private SaleResponseAssembler saleResponseAssembler;

    @Mock
    private InvoiceDtoAssembler invoiceDtoAssembler;

    @Mock
    private ManufactureService manufactureService;

    @Mock
    private TokenDto tokenDto;

    @Mock
    private ChooseCarRequest chooseCarRequest;

    @Mock
    private ChooseCarDto chooseCarDto;

    @Mock
    private ChooseBatteryRequest chooseBatteryRequest;

    @Mock
    private ChooseBatteryDto chooseBatteryDto;

    @Mock
    private InvoiceRequest invoiceRequest;

    @Mock
    private InvoiceDto invoiceDto;

    @Mock
    private ContainerRequestContext containerRequestContext;

    @Mock
    private ConstraintsValidator constraintsValidator;

    @BeforeEach
    private void setUp() {
        saleResource = new SaleResource(saleService, tokenDtoAssembler, httpExceptionResponseAssembler,
                chooseCarDtoAssembler, chooseBatteryDtoAssembler, estimatedRangeResponseAssembler,
                saleResponseAssembler, invoiceDtoAssembler, constraintsValidator, manufactureService);
    }

    /*
     * @Test public void givenInvalidConstraints_whenChooseVehicle_thenReturnsAccordingErrorResponse() { // given
     * Mockito.doThrow(BadInputParameterException.class).when(constraintsValidator).validate(chooseCarRequest);
     *
     * // when Response response = saleResource.chooseVehicle(A_SALE_ID, chooseCarRequest);
     *
     * // then assertEquals(response.getStatus(), BAD_INPUT_PARAMETERS_ERROR_CODE); assertEquals(response.getEntity(),
     * BAD_INPUT_PARAMETERS_ERROR_MESSAGE); }
     *
     * @Test public void givenInvalidConstraints_whenChooseBattery_thenReturnsAccordingErrorResponse() { // given
     * Mockito.doThrow(BadInputParameterException.class).when(constraintsValidator).validate(chooseBatteryRequest);
     *
     * // when Response response = saleResource.chooseBattery(A_SALE_ID, chooseBatteryRequest);
     *
     * // then assertEquals(response.getStatus(), BAD_INPUT_PARAMETERS_ERROR_CODE); assertEquals(response.getEntity(),
     * BAD_INPUT_PARAMETERS_ERROR_MESSAGE); }
     */

    @Test
    public void whenInitSale_thenSaleServiceIsCalled() {
        // given
        String emptyRequestContextAuthorization = null;
        BDDMockito.given(tokenDtoAssembler.assembleFromString(emptyRequestContextAuthorization)).willReturn(tokenDto);

        // when
        saleResource.initSale(containerRequestContext);

        // then
        Mockito.verify(saleService).initSale(tokenDto);
    }
    /*
     * @Test public void whenChooseVehicle_thenManufactureServiceChooseVehicle() { // when
     * saleResource.chooseVehicle(A_SALE_ID, chooseCarRequest);
     *
     * // then Mockito.verify(manufactureService).chooseCar(A_SALE_ID, chooseCarDto); }
     *
     * @Test public void whenChooseBattery_thenManufactureServiceChooseBattery() { // when
     * saleResource.chooseBattery(A_SALE_ID, chooseBatteryRequest);
     *
     * // then Mockito.verify(manufactureService).chooseBattery(A_SALE_ID, chooseBatteryDto); }
     *
     * @Test public void givenInvalidConstraints_whenCompleteSale_thenReturnsAccordingErrorResponse() { // given
     * Mockito.doThrow(BadInputParameterException.class).when(constraintsValidator).validate(invoiceRequest);
     *
     * // when Response response = saleResource.completeSale(A_SALE_ID, invoiceRequest);
     *
     * // then assertEquals(response.getStatus(), BAD_INPUT_PARAMETERS_ERROR_CODE); assertEquals(response.getEntity(),
     * BAD_INPUT_PARAMETERS_ERROR_MESSAGE); }
     *
     * @Test public void whenCompleteSale_thenSaleServiceCompleteSale() { // when saleResource.completeSale(A_SALE_ID,
     * invoiceRequest);
     *
     * // then Mockito.verify(saleService).completeSale(A_SALE_ID, invoiceDto); }
     */
}
