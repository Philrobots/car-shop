package ca.ulaval.glo4003.evulution.api.delivery;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryCompleteDto;
import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryLocationDto;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.domain.exceptions.GenericException;
import ca.ulaval.glo4003.evulution.service.delivery.DeliveryService;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class DeliveryResourceImpl implements DeliveryResource {
    private final DeliveryService deliveryService;
    private final ConstraintsValidator constraintsValidator;
    private final HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    public DeliveryResourceImpl(DeliveryService deliveryService, ConstraintsValidator constraintsValidator,
            HTTPExceptionResponseAssembler httpExceptionResponseAssembler) {
        this.deliveryService = deliveryService;
        this.constraintsValidator = constraintsValidator;
        this.httpExceptionResponseAssembler = httpExceptionResponseAssembler;
    }

    @Override
    public Response chooseDeliveryLocation(int deliveryId, DeliveryLocationDto deliveryLocationDto) {
        try {
            this.constraintsValidator.validate(deliveryLocationDto);
            this.deliveryService.chooseDeliveryLocation(deliveryId, deliveryLocationDto);

            return Response.ok().status(200, "Transaction complete").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }

    @Override
    public Response completeDelivery(int deliveryId) {
        try {
            DeliveryCompleteDto deliveryCompleteDto = this.deliveryService.completeDelivery(deliveryId);

            return Response.ok(deliveryCompleteDto, MediaType.APPLICATION_JSON).status(200, "Delivery completed")
                    .build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }
}
