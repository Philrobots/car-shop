package ca.ulaval.glo4003.evulution.api.sale;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.mappers.HTTPExceptionMapper;
import ca.ulaval.glo4003.evulution.api.mappers.mapping.HTTPExceptionMapping;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseBatteryDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseVehicleDto;
import ca.ulaval.glo4003.evulution.domain.exception.GenericException;
import ca.ulaval.glo4003.evulution.service.sale.SaleService;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class SaleResourceImpl implements SaleResource {

    private SaleService saleService;
    private TokenDtoAssembler tokenDtoAssembler;
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    public SaleResourceImpl(SaleService saleService, TokenDtoAssembler tokenDtoAssembler, HTTPExceptionResponseAssembler httpExceptionResponseAssembler) {
        this.saleService = saleService;
        this.tokenDtoAssembler = tokenDtoAssembler;
        this.httpExceptionResponseAssembler = httpExceptionResponseAssembler;
    }

    @Override
    public Response initSale(ContainerRequestContext containerRequestContext) {
        try {
            String authorizationToken = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            TokenDto tokenDto = tokenDtoAssembler.assembleFromString(authorizationToken);

            return Response.ok(this.saleService.initSale(tokenDto), MediaType.APPLICATION_JSON)
                    .status(201, "search results matching criteria").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }

    }

    @Override
    public Response chooseVehicle(int transactionId, ChooseVehicleDto chooseVehicleDto) {
        try {
            this.saleService.chooseVehicle(transactionId, chooseVehicleDto);
            return Response.ok().status(202, "Added car model").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }

    @Override
    public Response chooseBattery(int transactionId, ChooseBatteryDto chooseBatteryDto) {
        try {
            this.saleService.chooseBattery(transactionId, chooseBatteryDto);
            return Response.ok().status(202, "Added selected battery capacity").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }
}
