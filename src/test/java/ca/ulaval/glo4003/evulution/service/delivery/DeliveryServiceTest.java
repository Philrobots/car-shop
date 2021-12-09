package ca.ulaval.glo4003.evulution.service.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.*;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryModeException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoicePayment;
import ca.ulaval.glo4003.evulution.domain.sale.*;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.IncompleteSaleException;
import ca.ulaval.glo4003.evulution.infrastructure.delivery.exceptions.DeliveryNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;
import ca.ulaval.glo4003.evulution.service.delivery.dto.DeliveryLocationDto;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadOrderOfOperationsException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {
    private static final Integer A_DELIVERY_ID = 4;
    private static final Integer PAYMENTS_LEFT = 150;
    private static final BigDecimal PAYMENTS_TAKEN = BigDecimal.valueOf(200);

    @Mock
    private DeliveryIdFactory deliveryIdFactory;

    @Mock
    private DeliveryDetailsFactory deliveryDetailsFactory;

    @Mock
    private InvoicePayment invoicePayment;

    @Mock
    private DeliveryId deliveryId;

    @Mock
    private SaleId saleId;

    @Mock
    private Invoice invoice;

    @Mock
    private DeliveryCompleteAssembler deliveryCompleteAssembler;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private SaleValidator saleValidator;

    @Mock
    private DeliveryDomainService deliveryDomainService;

    @Mock
    private SaleDomainService saleDomainService;

    @Mock
    private DeliveryLocationDto deliveryLocationDto;

    private DeliveryService deliveryService;

    @BeforeEach
    public void setUp() {
        deliveryService = new DeliveryService(deliveryIdFactory, deliveryDetailsFactory, invoicePayment,
                deliveryCompleteAssembler, deliveryRepository, saleValidator, deliveryDomainService, saleDomainService);
    }

    @Test
    public void whenChooseDeliveryLocation_thenSaleValidatorIsCalled()
            throws DeliveryNotFoundException, IncompleteSaleException, SaleNotFoundException {
        // when
        this.deliveryService.chooseDeliveryLocation(A_DELIVERY_ID, deliveryLocationDto);

        // then
        Mockito.verify(saleValidator).validateCompleteStatusFromDeliveryId(A_DELIVERY_ID);
    }

    @Test
    public void whenCompleteDelivery_thenDeliveryFactoryCreatesFromInt()
            throws DeliveryNotFoundException, IncompleteSaleException, SaleNotFoundException {
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.given(deliveryRepository.getSaleId(deliveryId)).willReturn(saleId);
        BDDMockito.given(saleDomainService.activateInvoice(saleId)).willReturn(invoice);

        // when
        deliveryService.completeDelivery(A_DELIVERY_ID);

        // then
        Mockito.verify(deliveryIdFactory).createFromInt(A_DELIVERY_ID);
    }

    @Test
    public void whenCompleteDelivery_thenDeliveryRepositoryGetsSaleId()
            throws DeliveryNotFoundException, IncompleteSaleException, SaleNotFoundException {
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.given(deliveryRepository.getSaleId(deliveryId)).willReturn(saleId);
        BDDMockito.given(saleDomainService.activateInvoice(saleId)).willReturn(invoice);

        // when
        deliveryService.completeDelivery(A_DELIVERY_ID);

        // then
        Mockito.verify(deliveryRepository).getSaleId(deliveryId);
    }

    @Test
    public void whenCompleteDelivery_thenSaleDomainServiceActivatesInvoice()
            throws DeliveryNotFoundException, IncompleteSaleException, SaleNotFoundException {
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.given(deliveryRepository.getSaleId(deliveryId)).willReturn(saleId);
        BDDMockito.given(saleDomainService.activateInvoice(saleId)).willReturn(invoice);

        // when
        deliveryService.completeDelivery(A_DELIVERY_ID);

        // then
        Mockito.verify(saleDomainService).activateInvoice(saleId);
    }

    @Test
    public void whenCompleteDelivery_thenDeliveryDomainServiceCompleteDelivery() throws DeliveryNotFoundException,
            IncompleteSaleException, SaleNotFoundException, DeliveryIncompleteException {
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.given(deliveryRepository.getSaleId(deliveryId)).willReturn(saleId);
        BDDMockito.given(saleDomainService.activateInvoice(saleId)).willReturn(invoice);

        // when
        deliveryService.completeDelivery(A_DELIVERY_ID);

        // then
        Mockito.verify(deliveryDomainService).completeDelivery(deliveryId);
    }

    @Test
    public void whenCompleteDelivery_thenDeliveryCompleteAssemblerAssembles()
            throws DeliveryNotFoundException, IncompleteSaleException, SaleNotFoundException {
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.given(deliveryRepository.getSaleId(deliveryId)).willReturn(saleId);
        BDDMockito.given(saleDomainService.activateInvoice(saleId)).willReturn(invoice);
        BDDMockito.given(invoice.getPaymentsLeft()).willReturn(PAYMENTS_LEFT);
        BDDMockito.given(invoice.getPaymentsTaken()).willReturn(PAYMENTS_TAKEN);

        // when
        deliveryService.completeDelivery(A_DELIVERY_ID);

        // then
        Mockito.verify(deliveryCompleteAssembler).assemble(PAYMENTS_TAKEN, PAYMENTS_LEFT);
    }

    @Test
    public void givenSaleNotFoundException_whenChooseDeliveryLocation_thenThrowsServiceBadRequestException()
            throws DeliveryNotFoundException, IncompleteSaleException, SaleNotFoundException {
        // given
        BDDMockito.given(saleValidator.validateCompleteStatusFromDeliveryId(A_DELIVERY_ID))
                .willThrow(SaleNotFoundException.class);

        // when
        Executable chooseDeliveryLocation = () -> this.deliveryService.chooseDeliveryLocation(A_DELIVERY_ID,
                deliveryLocationDto);

        // then
        assertThrows(ServiceBadRequestException.class, chooseDeliveryLocation);
    }

    @Test
    public void givenDeliveryNotFoundException_whenChooseDeliveryLocation_thenThrowsServiceBadRequestException()
            throws DeliveryNotFoundException, IncompleteSaleException, SaleNotFoundException {
        // given
        BDDMockito.given(saleValidator.validateCompleteStatusFromDeliveryId(A_DELIVERY_ID))
                .willThrow(DeliveryNotFoundException.class);

        // when
        Executable chooseDeliveryLocation = () -> this.deliveryService.chooseDeliveryLocation(A_DELIVERY_ID,
                deliveryLocationDto);

        // then
        assertThrows(ServiceBadRequestException.class, chooseDeliveryLocation);
    }

    @Test
    public void givenIncompleteSaleException_whenChooseDeliveryLocation_thenThrowsServiceBadOrderOfOperationsException()
            throws DeliveryNotFoundException, IncompleteSaleException, SaleNotFoundException {
        // given
        BDDMockito.given(saleValidator.validateCompleteStatusFromDeliveryId(A_DELIVERY_ID))
                .willThrow(IncompleteSaleException.class);

        // when
        Executable chooseDeliveryLocation = () -> this.deliveryService.chooseDeliveryLocation(A_DELIVERY_ID,
                deliveryLocationDto);

        // then
        assertThrows(ServiceBadOrderOfOperationsException.class, chooseDeliveryLocation);
    }

    @Test
    public void givenDeliveryIncompleteException_whenChooseDeliveryLocation_thenThrowsServiceBadOrderOfOperationsException()
            throws DeliveryNotFoundException, IncompleteSaleException, SaleNotFoundException,
            DeliveryIncompleteException, BadDeliveryModeException, BadDeliveryLocationException {
        // given
        BDDMockito.given(saleValidator.validateCompleteStatusFromDeliveryId(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.doThrow(DeliveryIncompleteException.class).when(deliveryDomainService)
                .setDeliveryModeLocationAndConfirmDelivery(deliveryId, deliveryLocationDto.mode,
                        deliveryLocationDto.location);

        // when
        Executable chooseDeliveryLocation = () -> this.deliveryService.chooseDeliveryLocation(A_DELIVERY_ID,
                deliveryLocationDto);

        // then
        assertThrows(ServiceBadOrderOfOperationsException.class, chooseDeliveryLocation);
    }

    @Test
    public void givenBadDeliveryModeException_whenChooseDeliveryLocation_thenThrowsServiceBadInputParameterException()
            throws DeliveryNotFoundException, IncompleteSaleException, SaleNotFoundException,
            DeliveryIncompleteException, BadDeliveryModeException, BadDeliveryLocationException {
        // given
        BDDMockito.given(saleValidator.validateCompleteStatusFromDeliveryId(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.doThrow(BadDeliveryModeException.class).when(deliveryDomainService)
                .setDeliveryModeLocationAndConfirmDelivery(deliveryId, deliveryLocationDto.mode,
                        deliveryLocationDto.location);

        // when
        Executable chooseDeliveryLocation = () -> this.deliveryService.chooseDeliveryLocation(A_DELIVERY_ID,
                deliveryLocationDto);

        // then
        assertThrows(ServiceBadInputParameterException.class, chooseDeliveryLocation);
    }

    @Test
    public void givenBadDeliveryLocationException_whenChooseDeliveryLocation_thenThrowsServiceBadInputParameterException()
            throws DeliveryNotFoundException, IncompleteSaleException, SaleNotFoundException,
            DeliveryIncompleteException, BadDeliveryModeException, BadDeliveryLocationException {
        // given
        BDDMockito.given(saleValidator.validateCompleteStatusFromDeliveryId(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.doThrow(BadDeliveryLocationException.class).when(deliveryDomainService)
                .setDeliveryModeLocationAndConfirmDelivery(deliveryId, deliveryLocationDto.mode,
                        deliveryLocationDto.location);

        // when
        Executable chooseDeliveryLocation = () -> this.deliveryService.chooseDeliveryLocation(A_DELIVERY_ID,
                deliveryLocationDto);

        // then
        assertThrows(ServiceBadInputParameterException.class, chooseDeliveryLocation);
    }

    @Test
    public void givenSaleNotFoundException_whenCompleteDelivery_thenThrowsServiceBadRequestException()
            throws IncompleteSaleException, SaleNotFoundException {
        // given
        BDDMockito.given(saleDomainService.activateInvoice(any())).willThrow(SaleNotFoundException.class);

        // when
        Executable completeDelivery = () -> this.deliveryService.completeDelivery(A_DELIVERY_ID);

        // then
        assertThrows(ServiceBadRequestException.class, completeDelivery);
    }

    @Test
    public void givenDeliveryNotFoundException_whenCompleteDelivery_thenThrowsServiceBadRequestException()
            throws DeliveryNotFoundException {
        // given
        BDDMockito.given(deliveryRepository.getSaleId(any())).willThrow(DeliveryNotFoundException.class);

        // when
        Executable completeDelivery = () -> this.deliveryService.completeDelivery(A_DELIVERY_ID);

        // then
        assertThrows(ServiceBadRequestException.class, completeDelivery);
    }

    @Test
    public void givenIncompleteSaleException_whenCompleteDelivery_thenThrowsServiceBadOrderOfOperationsException()
            throws IncompleteSaleException, SaleNotFoundException {
        // given
        BDDMockito.given(saleDomainService.activateInvoice(any())).willThrow(IncompleteSaleException.class);

        // when
        Executable completeDelivery = () -> this.deliveryService.completeDelivery(A_DELIVERY_ID);

        // then
        assertThrows(ServiceBadOrderOfOperationsException.class, completeDelivery);
    }

    @Test
    public void givenDeliveryIncompleteException_whenCompleteDelivery_thenThrowsServiceBadOrderOfOperationsException()
            throws DeliveryNotFoundException, DeliveryIncompleteException {
        // given
        BDDMockito.doThrow(DeliveryIncompleteException.class).when(deliveryDomainService).completeDelivery(any());

        // when
        Executable completeDelivery = () -> this.deliveryService.completeDelivery(A_DELIVERY_ID);

        // then
        assertThrows(ServiceBadOrderOfOperationsException.class, completeDelivery);
    }
}
