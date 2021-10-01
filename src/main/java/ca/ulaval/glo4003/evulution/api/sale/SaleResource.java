package ca.ulaval.glo4003.evulution.api.sale;

import ca.ulaval.glo4003.evulution.api.authorization.Secured;
import ca.ulaval.glo4003.evulution.api.authorization.SecuredWithTransactionId;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseBatteryDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseVehicleDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.InvoiceDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/sales")
public interface SaleResource {

    @POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    Response initSale(ContainerRequestContext containerRequestContext);

    @POST
    @Secured
    @SecuredWithTransactionId
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{transaction_id}/vehicle")
    Response chooseVehicle(@PathParam("transaction_id") int transactionId, ChooseVehicleDto chooseVehicleDto);

    @POST
    @Secured
    @SecuredWithTransactionId
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{transaction_id}/battery")
    Response chooseBattery(@PathParam("transaction_id") int transactionId, ChooseBatteryDto chooseBatteryDto);

    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{transaction_id}/complete")
    Response completeSale(@PathParam("transaction_id") int transactionId, InvoiceDto invoiceDto);
}
