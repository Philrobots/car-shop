package ca.ulaval.glo4003.evulution.service.delivery;

import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryCompleteDto;
import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryLocationDto;
import ca.ulaval.glo4003.evulution.domain.delivery.*;
import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;
import ca.ulaval.glo4003.evulution.service.invoice.InvoiceService;
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

    @Mock
    private DeliveryIdFactory deliveryIdFactory;

    @Mock
    private DeliveryDetailsFactory deliveryDetailsFactory;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private InvoiceService invoiceService;

    @Mock
    private DeliveryId deliveryId;

    @Mock
    private TransactionId transactionId;

    @Mock
    private Sale sale;

    @Mock
    private Invoice invoice;

    @Mock
    private DeliveryLocationDto deliveryLocationDto;

    @Mock
    private DeliveryCompleteDto deliveryCompleteDto;

    @Mock
    private DeliveryDetails deliveryDetails;

    @Mock
    private DeliveryCompleteAssembler deliveryCompleteAssembler;

    private DeliveryService deliveryService;

    @BeforeEach
    public void setUp() {
        deliveryService = new DeliveryService(deliveryIdFactory, deliveryDetailsFactory, saleRepository, invoiceService,
                deliveryCompleteAssembler);
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

    @Test
    public void whenCompleteDelivery_thenDeliveryFactoryCreatesFromInt() {
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.given(saleRepository.getSaleFromDeliveryId(deliveryId)).willReturn(sale);
        BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
        BDDMockito.given(invoiceService.makeInvoiceActive(transactionId)).willReturn(invoice);

        // when
        deliveryService.completeDelivery(A_DELIVERY_ID);

        // then
        Mockito.verify(deliveryIdFactory).createFromInt(A_DELIVERY_ID);
    }

    @Test
    public void whenCompleteDelivery_thenSaleRepositoryGetsSaleFromDeliveryID() {
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.given(saleRepository.getSaleFromDeliveryId(deliveryId)).willReturn(sale);
        BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
        BDDMockito.given(invoiceService.makeInvoiceActive(transactionId)).willReturn(invoice);

        // when
        deliveryService.completeDelivery(A_DELIVERY_ID);

        // then
        Mockito.verify(saleRepository).getSaleFromDeliveryId(deliveryId);
    }

    @Test
    public void whenCompleteDelivery_thenSaleSetsDeliveryStatus() {
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.given(saleRepository.getSaleFromDeliveryId(deliveryId)).willReturn(sale);
        BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
        BDDMockito.given(invoiceService.makeInvoiceActive(transactionId)).willReturn(invoice);

        // when
        deliveryService.completeDelivery(A_DELIVERY_ID);

        // then
        Mockito.verify(sale).setDeliveryStatus(DeliveryStatus.COMPLETED);
    }

    @Test
    public void whenCompleteDelivery_thenInvoiceServiceMakesInvoiceActive() {
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.given(saleRepository.getSaleFromDeliveryId(deliveryId)).willReturn(sale);
        BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
        BDDMockito.given(invoiceService.makeInvoiceActive(transactionId)).willReturn(invoice);

        // when
        deliveryService.completeDelivery(A_DELIVERY_ID);

        // then
        Mockito.verify(invoiceService).makeInvoiceActive(transactionId);
    }

    @Test
    public void whenCompleteDelivery_thenAssemblesDeliveryAssembles() {
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.given(saleRepository.getSaleFromDeliveryId(deliveryId)).willReturn(sale);
        BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
        BDDMockito.given(invoiceService.makeInvoiceActive(transactionId)).willReturn(invoice);
        BDDMockito.given(invoice.getPaymentsLeft()).willReturn(PAYMENTS_LEFT);
        BDDMockito.given(invoice.getPaymentsTaken()).willReturn(PAYMENTS_TAKEN);
        BDDMockito.given(deliveryCompleteAssembler.assemble(PAYMENTS_TAKEN, PAYMENTS_LEFT))
                .willReturn(deliveryCompleteDto);

        // when
        deliveryService.completeDelivery(A_DELIVERY_ID);

        // then
        Mockito.verify(deliveryCompleteAssembler).assemble(PAYMENTS_TAKEN, PAYMENTS_LEFT);
    }
}
