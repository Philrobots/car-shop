package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryRepository;
import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteCarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompleteCarAssemblyLineTest {
    private static final Integer ASSEMBLY_DELAY_IN_WEEKS = 1;
    private static final String A_CAR_NAME = "Kia";
    private static final String A_BATTERY_NAME = "AAA";
    private final String AN_EMAIl = "expat.@tiray.com";
    private final LocalDate A_LOCAL_DATE = LocalDate.now();

    @Mock
    private EmailSender emailSender;

    @Mock
    private EmailFactory emailFactory;

    private CompleteCarAssemblyLine completeCarAssemblyLine;

    @Mock
    private Sale sale;

    @Mock
    private Email email;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private BatteryRepository batteryRepository;

    @BeforeEach
    public void setUp() {
        completeCarAssemblyLine = new CompleteCarAssemblyLine(emailFactory, emailSender, vehicleRepository,
                batteryRepository);
    }

    @Test
    public void whenAddCommand_thenWaitingListIsEmpty() {
        // then
        List<Sale> sales = completeCarAssemblyLine.getSalesWaitingList();
        assertTrue(sales.isEmpty());
    }

    @Test
    public void givenASale_whenAddCommand_thenWaitingListIsNotEmpty() {
        // when
        completeCarAssemblyLine.addCommand(sale);

        // then
        List<Sale> sales = completeCarAssemblyLine.getSalesWaitingList();
        assertEquals(sales.size(), 1);
    }

    @Test
    public void whenAddCommand_thenCarShouldNotBeInProductionMode() {
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
    public void givenASale_whenStartNext_thenWaitListShouldBeEmpty() {
        // given
        completeCarAssemblyLine.addCommand(sale);

        // when
        completeCarAssemblyLine.startNext();

        // then
        assertTrue(completeCarAssemblyLine.getSalesWaitingList().isEmpty());
    }

    @Test
    public void givenASale_whenStartNext_thenVehicleRepositoryRemovesCar() {
        // given
        BDDMockito.given(sale.getCarName()).willReturn(A_CAR_NAME);
        completeCarAssemblyLine.addCommand(sale);

        // when
        completeCarAssemblyLine.startNext();

        // then
        Mockito.verify(vehicleRepository).remove(sale.getCarName());
    }

    @Test
    public void givenASale_whenStartNext_thenVehicleRepositoryRemovesBattery() {
        // given
        BDDMockito.given(sale.getBatteryType()).willReturn(A_BATTERY_NAME);
        completeCarAssemblyLine.addCommand(sale);

        // when
        completeCarAssemblyLine.startNext();

        // then
        Mockito.verify(batteryRepository).remove(sale.getBatteryType());

    }

    @Test
    public void givenTwoWeeksRemaining_whenAdvance_thenCallsEmailFactory() {
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
    public void givenTwoWeeksRemaining_whenAdvance_thenSendsEmail() {
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
