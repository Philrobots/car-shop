package ca.ulaval.glo4003.evulution.api.sale;

import ca.ulaval.glo4003.evulution.api.authorization.assemblers.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.sale.assemblers.*;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseBatteryRequest;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseCarRequest;
import ca.ulaval.glo4003.evulution.api.sale.dto.InvoiceRequest;
import ca.ulaval.glo4003.evulution.api.sale.dto.SaleResponse;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
import ca.ulaval.glo4003.evulution.service.manufacture.ManufactureService;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.ChooseBatteryDto;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.ChooseCarDto;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.EstimatedRangeDto;
import ca.ulaval.glo4003.evulution.service.sale.SaleService;
import ca.ulaval.glo4003.evulution.service.sale.dto.InvoiceDto;
import ca.ulaval.glo4003.evulution.service.sale.dto.SaleCreatedDto;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
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
public class SaleResourceTest {
    private static final int A_SALE_ID = 2;
    private static final Integer STATUS_200 = 200;
    private static final Integer STATUS_201 = 201;
    private static final Integer STATUS_202 = 202;
    private static final String AN_AUTHORIZATION_TOKEN = "Auth Token";

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
    private ConstraintsValidator constraintsValidator;

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
    private SaleCreatedDto saleCreatedDto;

    @Mock
    private EstimatedRangeDto estimatedRangeDto;

    @Mock
    private SaleResponse saleResponse;

    @BeforeEach
    public void setUp() {
        saleResource = new SaleResource(saleService, tokenDtoAssembler, httpExceptionResponseAssembler,
                chooseCarDtoAssembler, chooseBatteryDtoAssembler, estimatedRangeResponseAssembler,
                saleResponseAssembler, invoiceDtoAssembler, constraintsValidator, manufactureService);
    }

    @Test
    public void whenInitSale_thenContainerRequestContextGetsHeaderString() {

        // given
        BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION))
                .willReturn(AN_AUTHORIZATION_TOKEN);

        // when
        saleResource.initSale(containerRequestContext);

        // then
        Mockito.verify(containerRequestContext).getHeaderString(HttpHeaders.AUTHORIZATION);
    }

    @Test
    public void whenInitSale_thenTokenDtoAssemblerAssemblesFromString() {

        // given
        BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION))
                .willReturn(AN_AUTHORIZATION_TOKEN);
        BDDMockito.given(tokenDtoAssembler.assembleFromString(AN_AUTHORIZATION_TOKEN)).willReturn(tokenDto);

        // when
        saleResource.initSale(containerRequestContext);

        // then
        Mockito.verify(tokenDtoAssembler).assembleFromString(AN_AUTHORIZATION_TOKEN);

    }

    @Test
    public void whenInitSale_thenSaleServiceInitsSale() {
        // given
        BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION))
                .willReturn(AN_AUTHORIZATION_TOKEN);
        BDDMockito.given(tokenDtoAssembler.assembleFromString(AN_AUTHORIZATION_TOKEN)).willReturn(tokenDto);
        BDDMockito.given(saleService.initSale(tokenDto)).willReturn(saleCreatedDto);

        // when
        saleResource.initSale(containerRequestContext);

        // then
        Mockito.verify(saleService).initSale(tokenDto);
    }

    @Test
    public void whenInitSale_thenSaleResponseAssemblerAssemblesToResponse() {
        // given
        BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION))
                .willReturn(AN_AUTHORIZATION_TOKEN);
        BDDMockito.given(tokenDtoAssembler.assembleFromString(AN_AUTHORIZATION_TOKEN)).willReturn(tokenDto);
        BDDMockito.given(saleService.initSale(tokenDto)).willReturn(saleCreatedDto);
        BDDMockito.given(saleResponseAssembler.toResponse(saleCreatedDto)).willReturn(saleResponse);

        // when
        saleResource.initSale(containerRequestContext);

        // then
        Mockito.verify(saleResponseAssembler).toResponse(saleCreatedDto);
    }

    @Test
    public void whenInitSale_thenReturnResponseStatus201() {
        // given
        BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION))
                .willReturn(AN_AUTHORIZATION_TOKEN);
        BDDMockito.given(tokenDtoAssembler.assembleFromString(AN_AUTHORIZATION_TOKEN)).willReturn(tokenDto);
        BDDMockito.given(saleService.initSale(tokenDto)).willReturn(saleCreatedDto);
        BDDMockito.given(saleResponseAssembler.toResponse(saleCreatedDto)).willReturn(saleResponse);

        // when
        Response response = saleResource.initSale(containerRequestContext);

        // then
        Assertions.assertEquals(response.getStatus(), STATUS_201);
    }

    @Test
    public void whenChooseVehicle_thenContraitValidatorValidates() {
        // when
        this.saleResource.chooseVehicle(A_SALE_ID, chooseCarRequest);

        // then
        Mockito.verify(constraintsValidator).validate(chooseCarRequest);
    }

    @Test
    public void whenChooseVehicle_thenChooseCarDtoAssemblerFromRequest() {
        // when
        this.saleResource.chooseVehicle(A_SALE_ID, chooseCarRequest);

        // then
        Mockito.verify(chooseCarDtoAssembler).fromRequest(chooseCarRequest);
    }

    @Test
    public void givenChooseCarDto_whenChooseVehicle_thenManufactureServiceChooseCar() {
        // given
        BDDMockito.given(chooseCarDtoAssembler.fromRequest(chooseCarRequest)).willReturn(chooseCarDto);

        // when
        this.saleResource.chooseVehicle(A_SALE_ID, chooseCarRequest);

        // then
        Mockito.verify(manufactureService).chooseCar(A_SALE_ID, chooseCarDto);
    }

    @Test
    public void givenValidConstraint_whenChooseVehicle_thenReturnsResponseStatus202() {
        // given
        BDDMockito.given(chooseCarDtoAssembler.fromRequest(chooseCarRequest)).willReturn(chooseCarDto);

        // when
        Response response = this.saleResource.chooseVehicle(A_SALE_ID, chooseCarRequest);

        System.out.println(response);

        // then
        Assertions.assertEquals(response.getStatus(), STATUS_202);
    }

    @Test
    public void givenInvalidConstraints_whenChooseVehicle_thenReturnsAccordingErrorResponse() {
        // given
        Mockito.doThrow(ServiceBadInputParameterException.class).when(constraintsValidator).validate(chooseCarRequest);

        // when
        saleResource.chooseVehicle(A_SALE_ID, chooseCarRequest);

        // then
        Mockito.verify(httpExceptionResponseAssembler)
                .assembleResponseFromExceptionClass(ServiceBadInputParameterException.class);
    }

    @Test
    public void whenChooseBattery_thenConstraintValidatorValidates() {
        // when
        this.saleResource.chooseBattery(A_SALE_ID, chooseBatteryRequest);

        // then
        Mockito.verify(constraintsValidator).validate(chooseBatteryRequest);
    }

    @Test
    public void whenChooseBattery_thenChooseBatteryDtoAssemblesFromRequest() {
        // when
        this.saleResource.chooseBattery(A_SALE_ID, chooseBatteryRequest);

        // then
        Mockito.verify(chooseBatteryDtoAssembler).fromRequest(chooseBatteryRequest);
    }

    @Test
    public void whenChooseBattery_thenManufactureServiceChoosesBattery() {
        // given
        BDDMockito.given(chooseBatteryDtoAssembler.fromRequest(chooseBatteryRequest)).willReturn(chooseBatteryDto);

        // when
        this.saleResource.chooseBattery(A_SALE_ID, chooseBatteryRequest);

        // then
        Mockito.verify(manufactureService).chooseBattery(A_SALE_ID, chooseBatteryDto);
    }

    @Test
    public void whenChooseBattery_thenEstimatedRangeResponseAssemblesToResponse() {
        // given
        BDDMockito.given(chooseBatteryDtoAssembler.fromRequest(chooseBatteryRequest)).willReturn(chooseBatteryDto);
        BDDMockito.given(manufactureService.chooseBattery(A_SALE_ID, chooseBatteryDto)).willReturn(estimatedRangeDto);

        // when
        this.saleResource.chooseBattery(A_SALE_ID, chooseBatteryRequest);

        // then
        Mockito.verify(estimatedRangeResponseAssembler).toResponse(estimatedRangeDto);
    }

    @Test
    public void givenValidConstraint_whenChooseBattery_thenReturnsResponseStatus202() {
        // given
        BDDMockito.given(chooseBatteryDtoAssembler.fromRequest(chooseBatteryRequest)).willReturn(chooseBatteryDto);

        // when
        Response response = this.saleResource.chooseBattery(A_SALE_ID, chooseBatteryRequest);

        // then
        Assertions.assertEquals(response.getStatus(), STATUS_202);
    }

    @Test
    public void givenInvalidConstraints_whenChooseBattery_thenHttpExceptionResponseAssemblesResponseFromExceptionClass() {
        // given
        Mockito.doThrow(ServiceBadInputParameterException.class).when(constraintsValidator)
                .validate(chooseBatteryRequest);

        // when
        saleResource.chooseBattery(A_SALE_ID, chooseBatteryRequest);

        // then
        Mockito.verify(httpExceptionResponseAssembler)
                .assembleResponseFromExceptionClass(ServiceBadInputParameterException.class);
    }

    @Test
    public void whenCompleteSale_thenConstraintValidatorValidates() {
        // when
        this.saleResource.completeSale(A_SALE_ID, invoiceRequest);

        // then
        Mockito.verify(constraintsValidator).validate(invoiceRequest);
    }

    @Test
    public void whenCompleteSale_thenInvoiceDtoAssemblerAssemblesFromRequest() {
        // given
        BDDMockito.given(invoiceDtoAssembler.fromRequest(invoiceRequest)).willReturn(invoiceDto);

        // when
        this.saleResource.completeSale(A_SALE_ID, invoiceRequest);

        // then
        Mockito.verify(invoiceDtoAssembler).fromRequest(invoiceRequest);
    }

    @Test
    public void whenCompleteSale_thenSaleServiceCompletesSale() {
        // given
        BDDMockito.given(invoiceDtoAssembler.fromRequest(invoiceRequest)).willReturn(invoiceDto);

        // when
        saleResource.completeSale(A_SALE_ID, invoiceRequest);

        // then
        Mockito.verify(saleService).completeSale(A_SALE_ID, invoiceDto);
    }

    @Test
    public void whenCompleteSale_thenReturnsResponseStatus200() {
        // given
        BDDMockito.given(invoiceDtoAssembler.fromRequest(invoiceRequest)).willReturn(invoiceDto);

        // when
        Response response = saleResource.completeSale(A_SALE_ID, invoiceRequest);

        // then
        Assertions.assertEquals(response.getStatus(), STATUS_200);

    }

    @Test
    public void givenInvalidConstraints_whenCompleteSale_thenHttpExceptionResponseAssemblesResponseFromExceptionClass() {
        // given
        Mockito.doThrow(ServiceBadInputParameterException.class).when(constraintsValidator).validate(invoiceRequest);

        // when
        saleResource.completeSale(A_SALE_ID, invoiceRequest);

        // then
        Mockito.verify(httpExceptionResponseAssembler)
                .assembleResponseFromExceptionClass(ServiceBadInputParameterException.class);
    }

}
