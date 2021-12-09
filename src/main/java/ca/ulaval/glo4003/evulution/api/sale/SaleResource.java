package ca.ulaval.glo4003.evulution.api.sale;

import ca.ulaval.glo4003.evulution.api.authorization.Secured;
import ca.ulaval.glo4003.evulution.api.authorization.SecuredWithSaleId;
import ca.ulaval.glo4003.evulution.api.authorization.assemblers.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.sale.assemblers.*;
import ca.ulaval.glo4003.evulution.api.sale.dto.*;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.service.exceptions.GenericException;
import ca.ulaval.glo4003.evulution.service.manufacture.ManufactureService;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.ChooseBatteryDto;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.ChooseCarDto;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.EstimatedRangeDto;
import ca.ulaval.glo4003.evulution.service.sale.SaleService;
import ca.ulaval.glo4003.evulution.service.sale.dto.InvoiceDto;
import ca.ulaval.glo4003.evulution.service.sale.dto.SaleCreatedDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/sales")
public class SaleResource {
    private final SaleService saleService;
    private final TokenDtoAssembler tokenDtoAssembler;
    private final HTTPExceptionResponseAssembler httpExceptionResponseAssembler;
    private final ChooseCarDtoAssembler chooseCarDtoAssembler;
    private final ChooseBatteryDtoAssembler chooseBatteryDtoAssembler;
    private final EstimatedRangeResponseAssembler estimatedRangeResponseAssembler;
    private final SaleResponseAssembler saleResponseAssembler;
    private final InvoiceDtoAssembler invoiceDtoAssembler;
    private final ConstraintsValidator constraintsValidator;
    private final ManufactureService manufactureService;

    public SaleResource(SaleService saleService, TokenDtoAssembler tokenDtoAssembler,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler, ChooseCarDtoAssembler chooseCarDtoAssembler,
            ChooseBatteryDtoAssembler chooseBatteryDtoAssembler,
            EstimatedRangeResponseAssembler estimatedRangeResponseAssembler,
            SaleResponseAssembler saleResponseAssembler, InvoiceDtoAssembler invoiceDtoAssembler,
            ConstraintsValidator constraintsValidator, ManufactureService manufactureService) {
        this.saleService = saleService;
        this.tokenDtoAssembler = tokenDtoAssembler;
        this.httpExceptionResponseAssembler = httpExceptionResponseAssembler;
        this.chooseCarDtoAssembler = chooseCarDtoAssembler;
        this.chooseBatteryDtoAssembler = chooseBatteryDtoAssembler;
        this.estimatedRangeResponseAssembler = estimatedRangeResponseAssembler;
        this.saleResponseAssembler = saleResponseAssembler;
        this.invoiceDtoAssembler = invoiceDtoAssembler;
        this.constraintsValidator = constraintsValidator;
        this.manufactureService = manufactureService;
    }

    @POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response initSale(ContainerRequestContext containerRequestContext) {
        try {
            String authorizationToken = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            TokenDto tokenDto = tokenDtoAssembler.assembleFromString(authorizationToken);
            SaleCreatedDto saleCreatedDto = this.saleService.initSale(tokenDto);
            SaleResponse saleResponse = this.saleResponseAssembler.toResponse(saleCreatedDto);

            return Response.ok(saleResponse, MediaType.APPLICATION_JSON).status(201, "search results matching criteria")
                    .build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }

    }

    @POST
    @SecuredWithSaleId
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{sale_id}/vehicle")
    public Response chooseVehicle(@PathParam("sale_id") int saleId, ChooseCarRequest chooseCarRequest) {
        try {
            this.constraintsValidator.validate(chooseCarRequest);
            ChooseCarDto chooseCarDto = this.chooseCarDtoAssembler.fromRequest(chooseCarRequest);
            this.manufactureService.chooseCar(saleId, chooseCarDto);

            return Response.ok().status(202, "Added car model").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }

    @POST
    @SecuredWithSaleId
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{sale_id}/battery")
    public Response chooseBattery(@PathParam("sale_id") int saleId, ChooseBatteryRequest chooseBatteryRequest) {
        try {
            this.constraintsValidator.validate(chooseBatteryRequest);
            ChooseBatteryDto chooseBatteryDto = this.chooseBatteryDtoAssembler.fromRequest(chooseBatteryRequest);
            EstimatedRangeDto estimatedRangeDto = this.manufactureService.chooseBattery(saleId, chooseBatteryDto);
            EstimatedRangeResponse estimatedRangeResponse = this.estimatedRangeResponseAssembler
                    .toResponse(estimatedRangeDto);

            return Response.ok(estimatedRangeResponse, MediaType.APPLICATION_JSON)
                    .status(202, "Added selected battery capacity").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }

    @POST
    @SecuredWithSaleId
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{sale_id}/complete")
    public Response completeSale(@PathParam("sale_id") int saleId, InvoiceRequest invoiceRequest) {
        try {
            this.constraintsValidator.validate(invoiceRequest);
            InvoiceDto invoiceDto = this.invoiceDtoAssembler.fromRequest(invoiceRequest);
            this.saleService.completeSale(saleId, invoiceDto);

            return Response.ok().status(200, "Transaction complete").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }
}
