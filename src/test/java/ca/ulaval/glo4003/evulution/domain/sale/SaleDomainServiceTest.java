package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoicePayment;
import ca.ulaval.glo4003.evulution.domain.invoice.exceptions.InvalidInvoiceException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleAlreadyCompleteException;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class SaleDomainServiceTest {
    private static final Integer A_BANK_NUMBER = 123;
    private static final Integer AN_ACCOUNT_NUMBER = 456;
    private static final String A_FREQUENCY = "Weekly";
    private static final String AN_EMAIL = "yolo@ulaval.ca";

    private static final BigDecimal A_PRICE = BigDecimal.valueOf(10);

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private Invoice invoice;

    @Mock
    private SaleId saleId;

    @Mock
    private Sale sale;

    @Mock
    private AccountId accountId;

    @Mock
    private Account account;

    @Mock
    private InvoicePayment invoicePayment;

    private SaleDomainService saleDomainService;

    @BeforeEach
    void setUp() {
        saleDomainService = new SaleDomainService(saleRepository, accountRepository, invoicePayment);
    }

    @Test
    public void whenSetVehiclePrice_thenSaleRepositoryGetsSale() throws SaleNotFoundException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);

        // when
        saleDomainService.setVehiclePrice(saleId, A_PRICE);

        // then
        Mockito.verify(saleRepository).getSale(saleId);
    }

    @Test
    public void whenSetVehiclePrice_thenSaleSetsVehiclePrice() throws SaleNotFoundException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);

        // when
        saleDomainService.setVehiclePrice(saleId, A_PRICE);

        // then
        Mockito.verify(sale).setVehiclePrice(A_PRICE);
    }

    @Test
    public void whenSetVehiclePrice_thenSaleRepositoryRegistersSale() throws SaleNotFoundException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);

        // when
        saleDomainService.setVehiclePrice(saleId, A_PRICE);

        // then
        Mockito.verify(saleRepository).registerSale(sale);
    }

    @Test
    public void whenSetBatteryPrice_thenSaleRepositoryGetsSale() throws SaleNotFoundException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);

        // when
        saleDomainService.setBatteryPrice(saleId, A_PRICE);

        // then
        Mockito.verify(saleRepository).getSale(saleId);
    }

    @Test
    public void whenSetBatteryPrice_thenSaleSetsBatteryPrice() throws SaleNotFoundException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);

        // when
        saleDomainService.setBatteryPrice(saleId, A_PRICE);

        // then
        Mockito.verify(sale).setBatteryPrice(A_PRICE);
    }

    @Test
    public void whenSetBatteryPrice_thenSaleRepositoryRegistersSale() throws SaleNotFoundException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);

        // when
        saleDomainService.setBatteryPrice(saleId, A_PRICE);

        // then
        Mockito.verify(saleRepository).registerSale(sale);
    }

    @Test
    public void whenCompleteSale_thenSaleRepositoryGetsSale()
            throws SaleNotFoundException, SaleAlreadyCompleteException, InvalidInvoiceException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);

        // when
        saleDomainService.completeSale(saleId, A_BANK_NUMBER, AN_ACCOUNT_NUMBER, A_FREQUENCY);

        // then
        Mockito.verify(saleRepository).getSale(saleId);
    }

    @Test
    public void whenCompleteSale_thenSaleCompletesSale()
            throws SaleNotFoundException, SaleAlreadyCompleteException, InvalidInvoiceException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);

        // when
        saleDomainService.completeSale(saleId, A_BANK_NUMBER, AN_ACCOUNT_NUMBER, A_FREQUENCY);

        Mockito.verify(sale).completeSale(A_BANK_NUMBER, AN_ACCOUNT_NUMBER, A_FREQUENCY);
    }

    @Test
    public void whenCompleteSale_thenSaleRepositoryRegisters()
            throws SaleNotFoundException, SaleAlreadyCompleteException, InvalidInvoiceException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);

        // when
        saleDomainService.completeSale(saleId, A_BANK_NUMBER, AN_ACCOUNT_NUMBER, A_FREQUENCY);

        // then
        Mockito.verify(saleRepository).registerSale(sale);
    }

    @Test
    public void whenStartPayments_thenSaleRepositoryGetsSale() throws SaleNotFoundException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);
        BDDMockito.given(sale.startPayments(invoicePayment)).willReturn(invoice);

        // when
        saleDomainService.startPayments(saleId);

        // then
        Mockito.verify(saleRepository).getSale(saleId);
    }

    @Test
    public void whenStartPayments_thenSaleStartsPayments() throws SaleNotFoundException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);
        BDDMockito.given(sale.startPayments(invoicePayment)).willReturn(invoice);

        // when
        saleDomainService.startPayments(saleId);

        // then
        Mockito.verify(sale).startPayments(invoicePayment);
    }

    @Test
    public void whenStartPayments_thenSaleRegistersSale() throws SaleNotFoundException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);
        BDDMockito.given(sale.startPayments(invoicePayment)).willReturn(invoice);

        // when
        saleDomainService.startPayments(saleId);

        // then
        Mockito.verify(saleRepository).registerSale(sale);
    }

    @Test
    public void whenStartPayments_thenReturnsInvoice() throws SaleNotFoundException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);
        BDDMockito.given(sale.startPayments(invoicePayment)).willReturn(invoice);

        // when
        Invoice currentInvoice = saleDomainService.startPayments(saleId);

        Assertions.assertEquals(invoice, currentInvoice);
    }

    @Test
    public void whenGetEmailFromSaleId_thenSaleRepositoryGetsAccountIdFromSaleId()
            throws SaleNotFoundException, AccountNotFoundException {
        // given
        BDDMockito.given(saleRepository.getAccountIdFromSaleId(saleId)).willReturn(accountId);
        BDDMockito.given(accountRepository.getAccount(accountId)).willReturn(account);
        BDDMockito.given(account.getEmail()).willReturn(AN_EMAIL);

        // when
        saleDomainService.getEmailFromSaleId(saleId);

        // then
        Mockito.verify(saleRepository).getAccountIdFromSaleId(saleId);
    }

    @Test
    public void whenGetEmailFromSaleId_thenReturnsAnEmail() throws SaleNotFoundException, AccountNotFoundException {
        // given
        BDDMockito.given(saleRepository.getAccountIdFromSaleId(saleId)).willReturn(accountId);
        BDDMockito.given(accountRepository.getAccount(accountId)).willReturn(account);
        BDDMockito.given(account.getEmail()).willReturn(AN_EMAIL);

        // when
        String email = saleDomainService.getEmailFromSaleId(saleId);

        // then
        Assertions.assertEquals(AN_EMAIL, email);
    }

}
