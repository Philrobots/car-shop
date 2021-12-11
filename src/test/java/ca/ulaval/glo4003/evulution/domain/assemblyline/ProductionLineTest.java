package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.complete.CompleteAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsNotShutdownException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsShutdownException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.email.ProductionLineEmailNotifier;
import ca.ulaval.glo4003.evulution.domain.manufacture.ManufactureRepository;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProductionFactory;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionFactory;
import ca.ulaval.glo4003.evulution.domain.production.complete.CompleteAssemblyProductionFactory;
import ca.ulaval.glo4003.evulution.domain.production.exceptions.CarNotAssociatedWithManufactureException;
import ca.ulaval.glo4003.evulution.domain.sale.SaleDomainService;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductionLineTest {

    @Mock
    private CarAssemblyLine carAssemblyLine;

    @Mock
    private BatteryAssemblyLineSequential batteryAssemblyLineSequential;

    @Mock
    private CompleteAssemblyLineSequential completeAssemblyLineSequential;

    @Mock
    private ManufactureRepository manufactureRepository;

    @Mock
    private SaleDomainService saleDomainService;

    @Mock
    private BatteryProductionFactory batteryProductionFactory;

    @Mock
    private CarProductionFactory carProductionFactory;

    @Mock
    private CompleteAssemblyProductionFactory completeAssemblyProductionFactory;

    @Mock
    private ProductionLineEmailNotifier productionLineEmailNotifier;

    private ProductionLine productionLine;

    @BeforeEach
    void setup() {
        this.productionLine = new ProductionLine(carAssemblyLine, batteryAssemblyLineSequential,
                completeAssemblyLineSequential, manufactureRepository, saleDomainService, batteryProductionFactory,
                carProductionFactory, completeAssemblyProductionFactory, productionLineEmailNotifier);
    }

    @Test
    public void givenAlreadyShutdown_whenShutdown_thenThrowAssemblyLineIsShutdownException()
            throws AssemblyLineIsShutdownException {
        this.productionLine.shutdown();
        Assertions.assertThrows(AssemblyLineIsShutdownException.class, () -> this.productionLine.shutdown());
    }

    @Test
    public void whenShutdown_thenAllAssemblyLinesAreShutdownAndEmailIsSent() throws AssemblyLineIsShutdownException {
        this.productionLine.shutdown();
        verify(batteryAssemblyLineSequential).shutdown();
        verify(carAssemblyLine).shutdown();
        verify(completeAssemblyLineSequential).shutdown();
    }

    @Test
    public void givenAlreadyReactivated_whenReactivate_thenThrowsAssemblyLineIsShutdownException() {
        Assertions.assertThrows(AssemblyLineIsNotShutdownException.class, () -> this.productionLine.reactivate());
    }

    @Test
    public void whenReactivate_thenReactivateIsCalledForEveryAssemblyLine()
            throws AssemblyLineIsShutdownException, AssemblyLineIsNotShutdownException {
        this.productionLine.shutdown();

        this.productionLine.reactivate();

        verify(batteryAssemblyLineSequential).reactivate();
        verify(carAssemblyLine).reactivate();
        verify(completeAssemblyLineSequential).reactivate();
    }

    @Test
    public void whenSetCarAssemblyLine_thenCallTransferAssemblyLine() {
        this.productionLine.setCarAssemblyLine(carAssemblyLine);
        verify(carAssemblyLine).transferAssemblyLine(carAssemblyLine);
    }

    @Test
    public void whenAdvanceAssemblyLines_thenAdvanceAllAssemblyLines() throws InvalidMappingKeyException, DeliveryIncompleteException, CarNotAssociatedWithManufactureException, SaleNotFoundException, AccountNotFoundException {
        this.productionLine.advanceAssemblyLines();
        verify(carAssemblyLine).advance();
        verify(batteryAssemblyLineSequential).advance();
        verify(completeAssemblyLineSequential).advance();

    }

}
