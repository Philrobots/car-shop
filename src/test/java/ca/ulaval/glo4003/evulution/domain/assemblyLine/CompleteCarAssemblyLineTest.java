package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteCarAssemblyLine;
import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.evulution.domain.email.Email;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.email.EmailSender;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CompleteCarAssemblyLineTest {

    private String AN_EMAIl = "expat.@tiray.com";
    private static final Integer ASSEMBLY_DELAY_IN_WEEKS = 1;
    private LocalDate A_LOCAL_DATE = LocalDate.now();

    @Mock
    private EmailSender emailSender;

    @Mock
    private EmailFactory emailFactory;

    private CompleteCarAssemblyLine completeCarAssemblyLine;

    @Mock
    private Sale sale;

    @Mock
    private Email email;

    @BeforeEach
    public void setUp() {
        completeCarAssemblyLine = new CompleteCarAssemblyLine(emailFactory, emailSender);
    }

    @Test
    public void givenNothing_thenWaitingListShouldBeEmpty() {
        // then
        List<Sale> sales = completeCarAssemblyLine.getSalesWaitingList();
        assertTrue(sales.isEmpty());
    }

    @Test
    public void givenAnSale_whenAddCommand_thenWaitListShouldNotBeEmpty() {
        // when
        completeCarAssemblyLine.addCommand(sale);

        // then
        List<Sale> sales = completeCarAssemblyLine.getSalesWaitingList();
        assertEquals(sales.size(), 1);
    }

    @Test
    public void givenNothing_thenCarShouldNotBeInProductionMode() {
        // when
        completeCarAssemblyLine.addCommand(sale);

        // then
        boolean isCarInProduction = completeCarAssemblyLine.getIsCarInProduction();
        assertFalse(isCarInProduction);
    }

    @Test
    public void whenStartNext_thenCarShouldBeInProductionMode() {
        // when
        completeCarAssemblyLine.addCommand(sale);
        completeCarAssemblyLine.startNext();

        // then
        boolean isCarInProduction = completeCarAssemblyLine.getIsCarInProduction();
        assertTrue(isCarInProduction);
    }

    @Test
    public void givenAnCar_whenStartNext_thenWaitListShouldBeEmpty() {
        // when
        completeCarAssemblyLine.addCommand(sale);
        completeCarAssemblyLine.startNext();

        // then
        assertTrue(completeCarAssemblyLine.getSalesWaitingList().isEmpty());
    }

    @Test
    public void givenAnWeekRemainingOf2_whenAdvance_thenShouldCallEmailFactory() {
        // given
        completeCarAssemblyLine.setIsCarCompleteInProduction(true);
        completeCarAssemblyLine.setWeeksRemaining(2);
        BDDMockito.given(sale.getEmail()).willReturn(AN_EMAIl);
        BDDMockito.given(sale.addDelayInWeeks(ASSEMBLY_DELAY_IN_WEEKS)).willReturn(A_LOCAL_DATE);

        // when
        completeCarAssemblyLine.setCurrentSale(sale);
        completeCarAssemblyLine.advance();

        // then
        Mockito.verify(emailFactory).createAssemblyDelayEmail(List.of(AN_EMAIl), A_LOCAL_DATE);
    }

    @Test
    public void givenAnWeekRemainingOf2_whenAdvance_thenSendEmail() {
        // given
        completeCarAssemblyLine.setIsCarCompleteInProduction(true);
        completeCarAssemblyLine.setWeeksRemaining(2);
        BDDMockito.given(sale.getEmail()).willReturn(AN_EMAIl);
        BDDMockito.given(sale.addDelayInWeeks(ASSEMBLY_DELAY_IN_WEEKS)).willReturn(A_LOCAL_DATE);
        BDDMockito.given(emailFactory.createAssemblyDelayEmail(List.of(AN_EMAIl), A_LOCAL_DATE)).willReturn(email);

        // when
        completeCarAssemblyLine.setCurrentSale(sale);
        completeCarAssemblyLine.advance();

        // then
        Mockito.verify(emailSender).sendEmail(email);
    }
}
