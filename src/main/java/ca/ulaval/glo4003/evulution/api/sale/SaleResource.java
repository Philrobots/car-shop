package ca.ulaval.glo4003.evulution.api.sale;

import ca.ulaval.glo4003.evulution.api.authorization.Secured;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseVehicleDto;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static org.eclipse.jetty.http.HttpHeader.AUTHORIZATION;

@Path("/sales")
public interface SaleResource {

    @POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    Response initSale(ContainerRequestContext containerRequestContext);

    @POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{transaction_id}/vehicle")
    Response chooseVehicle(@PathParam("transaction_id") int transactionId, ChooseVehicleDto chooseVehicleDto);
}
