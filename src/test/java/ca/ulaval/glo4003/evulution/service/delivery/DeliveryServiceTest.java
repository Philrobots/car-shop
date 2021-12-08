package ca.ulaval.glo4003.evulution.service.delivery;

import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryLocationRequest;
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
import ca.ulaval.glo4003.evulution.service.delivery.dto.DeliveryCompleteDto;
import ca.ulaval.glo4003.evulution.service.delivery.dto.DeliveryLocationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {
    private static final Integer A_DELIVERY_ID = 4;
    private static final Integer PAYMENTS_LEFT = 150;
    private static final BigDecimal PAYMENTS_TAKEN = BigDecimal.valueOf(200);
    private static final String A_DELIVERY_MODE = "At campus";
    private static final String A_DELIVERY_LOCATION = "Pouliot";

    @Mock
    private DeliveryIdFactory deliveryIdFactory;

    @Mock
    private DeliveryDetailsFactory deliveryDetailsFactory;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private InvoicePayment invoicePayment;

    @Mock
    private DeliveryId deliveryId;

    @Mock
    private SaleId saleId;

    @Mock
    private Sale sale;

    @Mock
    private Invoice invoice;

    @Mock
    private DeliveryLocationRequest deliveryLocationRequest;

    @Mock
    private DeliveryCompleteDto deliveryCompleteDto;

    @Mock
    private DeliveryDetails deliveryDetails;

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

}
