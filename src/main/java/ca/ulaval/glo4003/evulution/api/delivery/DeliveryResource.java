package ca.ulaval.glo4003.evulution.api.delivery;

import ca.ulaval.glo4003.evulution.api.authorization.SecuredWithDeliveryId;
import ca.ulaval.glo4003.evulution.api.delivery.assemblers.DeliveryCompletedResponseAssembler;
import ca.ulaval.glo4003.evulution.api.delivery.assemblers.DeliveryLocationDtoAssembler;
import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryCompletedResponse;
import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryLocationRequest;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.service.delivery.DeliveryService;
import ca.ulaval.glo4003.evulution.service.delivery.dto.DeliveryCompleteDto;
import ca.ulaval.glo4003.evulution.service.delivery.dto.DeliveryLocationDto;
import ca.ulaval.glo4003.evulution.service.exceptions.GenericException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/deliveries")
public class DeliveryResource {
    private final DeliveryService deliveryService;
    private final ConstraintsValidator constraintsValidator;
    private final HTTPExceptionResponseAssembler httpExceptionResponseAssembler;
    private final DeliveryCompletedResponseAssembler deliveryCompletedResponseAssembler;
    private final DeliveryLocationDtoAssembler deliveryLocationDtoAssembler;

    public DeliveryResource(DeliveryService deliveryService, ConstraintsValidator constraintsValidator,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler,
            DeliveryCompletedResponseAssembler deliveryCompletedResponseAssembler,
            DeliveryLocationDtoAssembler deliveryLocationDtoAssembler) {
        this.deliveryService = deliveryService;
        this.constraintsValidator = constraintsValidator;
        this.httpExceptionResponseAssembler = httpExceptionResponseAssembler;
        this.deliveryCompletedResponseAssembler = deliveryCompletedResponseAssembler;
        this.deliveryLocationDtoAssembler = deliveryLocationDtoAssembler;
    }

    @POST
    @SecuredWithDeliveryId
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{delivery_id}/location")
    public Response chooseDeliveryLocation(@PathParam("delivery_id") int deliveryId,
            DeliveryLocationRequest deliveryLocationRequest) {
        try {
            this.constraintsValidator.validate(deliveryLocationRequest);
            DeliveryLocationDto deliveryLocationDto = deliveryLocationDtoAssembler.fromRequest(deliveryLocationRequest);
            this.deliveryService.chooseDeliveryLocation(deliveryId, deliveryLocationDto);

            return Response.ok().status(200, "Transaction complete").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }

    @POST
    @SecuredWithDeliveryId
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{delivery_id}/complete")
    public Response completeDelivery(@PathParam("delivery_id") int deliveryId) {
        try {
            DeliveryCompleteDto deliveryCompleteDto = this.deliveryService.completeDelivery(deliveryId);
            DeliveryCompletedResponse deliveryCompletedResponse = this.deliveryCompletedResponseAssembler
                    .toResponse(deliveryCompleteDto);

            return Response.ok(deliveryCompletedResponse, MediaType.APPLICATION_JSON).status(200, "Delivery completed")
                    .build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }
}
