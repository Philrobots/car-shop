package ca.ulaval.glo4003.evulution.api.delivery;

import ca.ulaval.glo4003.evulution.api.authorization.SecuredWithDeliveryId;
import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryLocationDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/deliveries")
public interface DeliveryResource {

    @POST
    @SecuredWithDeliveryId
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{delivery_id}/location")
    Response chooseDeliveryLocation(@PathParam("delivery_id") int deliveryId, DeliveryLocationDto deliveryLocationDto);

    @POST
    @SecuredWithDeliveryId
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{delivery_id}/complete")
    Response completeDelivery(@PathParam("delivery_id") int deliveryId);
}
