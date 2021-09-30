package ca.ulaval.glo4003.evulution.api.delivery;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryLocationDto;
import ca.ulaval.glo4003.evulution.api.mappers.HTTPExceptionMapper;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.service.delivery.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeliveryResourceImplTest {

    private final static int A_DELIVERY_ID = 4;

    @Mock
    private DeliveryService deliveryService;

    @Mock
    private DeliveryLocationDto deliveryLocationDto;

    @Mock
    private ConstraintsValidator constraintsValidator;

    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    private DeliveryResourceImpl deliveryResourceImpl;

    @BeforeEach
    private void setUp() {
        httpExceptionResponseAssembler = new HTTPExceptionResponseAssembler(new HTTPExceptionMapper());
        deliveryResourceImpl = new DeliveryResourceImpl(deliveryService, constraintsValidator,
                httpExceptionResponseAssembler);
    }

    @Test
    public void whenChooseDeliveryLocation_thenDeliveryServiceIsCalled() {
        // when
        deliveryResourceImpl.chooseDeliveryLocation(A_DELIVERY_ID, deliveryLocationDto);

        // then
        Mockito.verify(deliveryService).chooseDeliveryLocation(A_DELIVERY_ID, deliveryLocationDto);
    }
}