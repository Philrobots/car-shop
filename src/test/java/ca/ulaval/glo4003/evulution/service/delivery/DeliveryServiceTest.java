package ca.ulaval.glo4003.evulution.service.delivery;

import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryLocationDto;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryDetails;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryDetailsFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {
    private final static int A_DELIVERY_ID = 4;

    @Mock
    private DeliveryIdFactory deliveryIdFactory;

    @Mock
    private DeliveryDetailsFactory deliveryDetailsFactory;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private DeliveryId deliveryId;

    @Mock
    private Sale sale;

    @Mock
    private DeliveryLocationDto deliveryLocationDto;

    @Mock
    private DeliveryDetails deliveryDetails;

    private DeliveryService deliveryService;

    @BeforeEach
    public void setUp() {
        deliveryService = new DeliveryService(deliveryIdFactory, deliveryDetailsFactory, saleRepository);
    }

    @Test
    public void whenChooseDeliveryLocation_thenDeliveryIdFactoryCreatesFromInt() {
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.given(saleRepository.getSaleFromDeliveryId(deliveryId)).willReturn(sale);
        BDDMockito.given(deliveryDetailsFactory.create(deliveryLocationDto.mode, deliveryLocationDto.location))
                .willReturn(deliveryDetails);

        // when
        deliveryService.chooseDeliveryLocation(A_DELIVERY_ID, deliveryLocationDto);

        // then
        Mockito.verify(deliveryIdFactory).createFromInt(A_DELIVERY_ID);
    }

    @Test
    public void whenChooseDeliveryLocation_thenSaleRepositoryGetsDelivery() {
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.given(saleRepository.getSaleFromDeliveryId(deliveryId)).willReturn(sale);
        BDDMockito.given(deliveryDetailsFactory.create(deliveryLocationDto.mode, deliveryLocationDto.location))
                .willReturn(deliveryDetails);

        // when
        deliveryService.chooseDeliveryLocation(A_DELIVERY_ID, deliveryLocationDto);

        // then
        Mockito.verify(saleRepository).getSaleFromDeliveryId(deliveryId);
    }

    @Test
    public void whenChooseDeliveryLocation_thenDeliveryDetailsIsCreated() {
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.given(saleRepository.getSaleFromDeliveryId(deliveryId)).willReturn(sale);
        BDDMockito.given(deliveryDetailsFactory.create(deliveryLocationDto.mode, deliveryLocationDto.location))
                .willReturn(deliveryDetails);

        // when
        deliveryService.chooseDeliveryLocation(A_DELIVERY_ID, deliveryLocationDto);

        // then
        Mockito.verify(deliveryDetailsFactory).create(deliveryLocationDto.mode, deliveryLocationDto.location);
    }

    @Test
    public void whenChooseDeliveryLocation_thenSaleSetDeliveryDetails() {
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.given(saleRepository.getSaleFromDeliveryId(deliveryId)).willReturn(sale);
        BDDMockito.given(deliveryDetailsFactory.create(deliveryLocationDto.mode, deliveryLocationDto.location))
                .willReturn(deliveryDetails);

        // when
        deliveryService.chooseDeliveryLocation(A_DELIVERY_ID, deliveryLocationDto);

        // then
        Mockito.verify(sale).setDeliveryDetails(deliveryDetails);
    }
}
