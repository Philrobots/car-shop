package ca.ulaval.glo4003.evulution.api.delivery;

import ca.ulaval.glo4003.evulution.api.authorization.Secured;
import ca.ulaval.glo4003.evulution.api.authorization.SecuredWithDeliveryId;
import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryLocationDto;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/deliveries")
public interface DeliveryResource {

    @POST
    @SecuredWithDeliveryId
    @Path("/{delivery_id}")
    Response chooseDeliveryLocation(@PathParam("delivery_id") int deliveryId, DeliveryLocationDto deliveryLocationDto);
}
