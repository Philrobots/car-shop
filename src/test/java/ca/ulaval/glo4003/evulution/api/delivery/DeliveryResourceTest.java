package ca.ulaval.glo4003.evulution.api.delivery;

import ca.ulaval.glo4003.evulution.api.delivery.assemblers.DeliveryCompletedResponseAssembler;
import ca.ulaval.glo4003.evulution.api.delivery.assemblers.DeliveryLocationDtoAssembler;
import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryLocationRequest;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.service.delivery.DeliveryService;
import ca.ulaval.glo4003.evulution.service.delivery.dto.DeliveryLocationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeliveryResourceTest {

    private final static int A_DELIVERY_ID = 4;

    @Mock
    private DeliveryService deliveryService;

    @Mock
    private DeliveryLocationRequest deliveryLocationRequest;

    @Mock
    private ConstraintsValidator constraintsValidator;

    @Mock
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    @Mock
    private DeliveryCompletedResponseAssembler deliveryCompletedResponseAssembler;

    @Mock
    private DeliveryLocationDtoAssembler deliveryLocationDtoAssembler;

    @Mock
    private DeliveryLocationDto deliveryLocationDto;

    private DeliveryResource deliveryResource;

    @BeforeEach
    public void setUp() {
        deliveryResource = new DeliveryResource(deliveryService, constraintsValidator, httpExceptionResponseAssembler,
                deliveryCompletedResponseAssembler, deliveryLocationDtoAssembler);
    }

    @Test
    public void whenChooseDeliveryLocation_thenDeliveryServiceIsCalled() {
        // given
        BDDMockito.given(deliveryLocationDtoAssembler.fromRequest(deliveryLocationRequest))
                .willReturn(deliveryLocationDto);

        // when
        deliveryResource.chooseDeliveryLocation(A_DELIVERY_ID, deliveryLocationRequest);

        // then
        Mockito.verify(deliveryService).chooseDeliveryLocation(A_DELIVERY_ID, deliveryLocationDto);
    }

    @Test
    public void whenChooseDeliveryLocation_thenConstraintValidatorValidates() {
        // when
        deliveryResource.chooseDeliveryLocation(A_DELIVERY_ID, deliveryLocationRequest);

        // then
        Mockito.verify(constraintsValidator).validate(deliveryLocationRequest);

    }

    @Test
    public void whenCompleteDelivery_thenDeliveryServiceIsCalled() {
        // when
        deliveryResource.completeDelivery(A_DELIVERY_ID);

        // then
        Mockito.verify(deliveryService).completeDelivery(A_DELIVERY_ID);
    }
}
