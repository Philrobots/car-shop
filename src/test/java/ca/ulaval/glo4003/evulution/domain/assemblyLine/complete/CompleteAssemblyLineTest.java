package ca.ulaval.glo4003.evulution.domain.assemblyLine.complete;

import ca.ulaval.glo4003.evulution.domain.assemblyline.complete.CompleteAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.email.ProductionLineEmailNotifier;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionRepository;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProductionRepository;
import ca.ulaval.glo4003.evulution.domain.email.Email;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class CompleteAssemblyLineTest {
    private static final Integer ASSEMBLY_DELAY_IN_WEEKS = 1;
    private static final String A_CAR_NAME = "Kia";
    private static final String A_BATTERY_NAME = "AAA";
    private final String AN_EMAIl = "expat.@tiray.com";
    private final LocalDate A_LOCAL_DATE = LocalDate.now();

    private CompleteAssemblyLineSequential completeAssemblyLine;

    @Mock
    private Sale sale;

    @Mock
    private Email email;

    @Mock
    private CarProductionRepository carProductionRepository;

    @Mock
    private BatteryProductionRepository batteryProductionRepository;

    @Mock
    private ProductionLineEmailNotifier productionLineEmailNotifier;

    @BeforeEach
    public void setUp() {
        completeAssemblyLine = new CompleteAssemblyLineSequential(carProductionRepository, batteryProductionRepository,
                productionLineEmailNotifier);
    }
    //
    // @Test
    // public void whenAddCommand_thenWaitingListIsEmpty() {
    // // then
    // List<Sale> sales = completeAssemblyLine.getSalesWaitingList();
    // assertTrue(sales.isEmpty());
    // }
    //
    // @Test
    // public void givenASale_whenAddCommand_thenWaitingListIsNotEmpty() {
    // // when
    // completeAssemblyLine.addProduction(sale);
    //
    // // then
    // List<Sale> sales = completeAssemblyLine.getSalesWaitingList();
    // assertEquals(sales.size(), 1);
    // }
    //
    // @Test
    // public void whenAddCommand_thenCarShouldNotBeInProductionMode() {
    // // when
    // completeAssemblyLine.addProduction(sale);
    //
    // // then
    // boolean isCarInProduction = completeAssemblyLine.getIsCarInProduction();
    // assertFalse(isCarInProduction);
    // }
    //
    // @Test
    // public void whenStartNext_thenCarShouldBeInProductionMode() {
    // // when
    // BDDMockito.given(emailFactory.createAssemblyInProductionEmail(any(), any())).willReturn(email);
    // BDDMockito.given(sale.getEmail()).willReturn(AN_EMAIl);
    // completeAssemblyLine.addProduction(sale);
    // completeAssemblyLine.startNext();
    //
    // // then
    // boolean isCarInProduction = completeAssemblyLine.getIsCarInProduction();
    // assertTrue(isCarInProduction);
    // }
    //
    // @Test
    // public void givenASale_whenStartNext_thenWaitListShouldBeEmpty() {
    // // given
    // BDDMockito.given(emailFactory.createAssemblyInProductionEmail(any(), any())).willReturn(email);
    // BDDMockito.given(sale.getEmail()).willReturn(AN_EMAIl);
    // completeAssemblyLine.addProduction(sale);
    //
    // // when
    // completeAssemblyLine.startNext();
    //
    // // then
    // assertTrue(completeAssemblyLine.getSalesWaitingList().isEmpty());
    // }
    //
    // @Test
    // public void givenTwoWeeksRemaining_whenAdvance_thenCallsEmailFactory() {
    // // given
    // BDDMockito.given(sale.getEmail()).willReturn(AN_EMAIl);
    // BDDMockito.given(sale.addDelayInWeeks(ASSEMBLY_DELAY_IN_WEEKS)).willReturn(A_LOCAL_DATE);
    // BDDMockito.given(emailFactory.createAssemblyDelayEmail(any(), any())).willReturn(email);
    // completeAssemblyLine.setIsCarCompleteInProduction(true);
    // completeAssemblyLine.setWeeksRemaining(2);
    //
    // // when
    // completeAssemblyLine.setCurrentSale(sale);
    // completeAssemblyLine.advance();
    //
    // // then
    // Mockito.verify(emailFactory).createAssemblyDelayEmail(List.of(AN_EMAIl), A_LOCAL_DATE);
    // }
    //
    // @Test
    // public void givenTwoWeeksRemaining_whenAdvance_thenSendsEmail() {
    // // given
    // completeAssemblyLine.setIsCarCompleteInProduction(true);
    // completeAssemblyLine.setWeeksRemaining(2);
    // BDDMockito.given(sale.getEmail()).willReturn(AN_EMAIl);
    // BDDMockito.given(sale.addDelayInWeeks(ASSEMBLY_DELAY_IN_WEEKS)).willReturn(A_LOCAL_DATE);
    // BDDMockito.given(emailFactory.createAssemblyDelayEmail(List.of(AN_EMAIl), A_LOCAL_DATE)).willReturn(email);
    //
    // // when
    // completeAssemblyLine.setCurrentSale(sale);
    // completeAssemblyLine.advance();
    //
    // // then
    // Mockito.verify(email).send();
    // }
}
