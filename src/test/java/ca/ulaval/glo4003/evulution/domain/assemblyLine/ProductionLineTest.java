package ca.ulaval.glo4003.evulution.domain.assemblyLine;

import ca.ulaval.glo4003.evulution.car_manufacture.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionLine;
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
import ca.ulaval.glo4003.evulution.domain.sale.SaleDomainService;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductionLineTest {

    @Mock
    CarAssemblyLine carAssemblyLine;

    @Mock
    BatteryAssemblyLineSequential batteryAssemblyLineSequential;

    @Mock
    CompleteAssemblyLineSequential completeAssemblyLineSequential;

    @Mock
    ManufactureRepository manufactureRepository;

    @Mock
    SaleDomainService saleDomainService;

    @Mock
    BatteryProductionFactory batteryProductionFactory;

    @Mock
    CarProductionFactory carProductionFactory;

    @Mock
    CompleteAssemblyProductionFactory completeAssemblyProductionFactory;

    @Mock
    ProductionLineEmailNotifier productionLineEmailNotifier;

    ProductionLine productionLine;


    @BeforeEach
    void setup() {
        productionLine = new ProductionLine(carAssemblyLine, batteryAssemblyLineSequential, completeAssemblyLineSequential, manufactureRepository,
                saleDomainService, batteryProductionFactory, carProductionFactory,
                completeAssemblyProductionFactory, productionLineEmailNotifier);
    }

    @Test
    public void givenAlreadyShutdown_whenShutdown_thenThrowAssemblyLineIsShutdownException() throws AssemblyLineIsShutdownException {
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
        Assertions.assertThrows(AssemblyLineIsNotShutdownException .class, () -> this.productionLine.reactivate());
    }

    @Test
    public void whenReactivate_thenReactivateIsCalledForEveryAssemblyLine() throws AssemblyLineIsShutdownException, AssemblyLineIsNotShutdownException {
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

}
