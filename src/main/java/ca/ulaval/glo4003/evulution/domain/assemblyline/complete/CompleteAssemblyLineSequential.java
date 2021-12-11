package ca.ulaval.glo4003.evulution.domain.assemblyline.complete;

import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.email.ProductionLineEmailNotifier;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProductionRepository;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionRepository;
import ca.ulaval.glo4003.evulution.domain.production.complete.CompleteAssemblyProduction;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;

import java.time.LocalDate;
import java.util.LinkedList;

public class CompleteAssemblyLineSequential {

    private static final double FIFTY_PERCENT_CHANCE = 0.5;
    private static final Integer ASSEMBLY_DELAY_IN_WEEKS = 1;
    private static final Integer RANDOM_CASE_OF_TWO_WEEKS_DELIVERY = 2;
    private static final Integer CASE_OF_NORMAL_ONE_WEEK_DELIVERY = 1;
    private static final Integer ASSEMBLY_FINISHED = 0;

    private final CarProductionRepository carProductionRepository;
    private final BatteryProductionRepository batteryProductionRepository;
    private AssemblyLineMediator assemblyLineMediator;
    private ProductionLineEmailNotifier productionLineEmailNotifier;
    private LinkedList<CompleteAssemblyProduction> waitingList = new LinkedList<>();
    private CompleteAssemblyProduction currentProduction;
    private int weeksRemaining;
    private boolean isCarCompleteInProduction = false;
    private boolean isBatteryInFire = false;

    public CompleteAssemblyLineSequential(CarProductionRepository carProductionRepository,
            BatteryProductionRepository batteryProductionRepository,
            ProductionLineEmailNotifier productionLineEmailNotifier) {
        this.carProductionRepository = carProductionRepository;
        this.batteryProductionRepository = batteryProductionRepository;
        this.productionLineEmailNotifier = productionLineEmailNotifier;
    }

    public void addProduction(CompleteAssemblyProduction completeAssemblyProduction) {
        waitingList.add(completeAssemblyProduction);
    }

    public void advance() throws DeliveryIncompleteException {

        if (!isCarCompleteInProduction || isBatteryInFire) {
            System.out.println("Skipping complete car assembly line ");
            return;
        }

        if (weeksRemaining == RANDOM_CASE_OF_TWO_WEEKS_DELIVERY) {
            System.out.println("2 weeks remaining");
            LocalDate expectedDate = this.currentProduction.addDelayInWeeks(ASSEMBLY_DELAY_IN_WEEKS);
            productionLineEmailNotifier.sendAssemblyDelayEmail(currentProduction.getProductionId(), expectedDate);
            this.weeksRemaining--;
        } else if (weeksRemaining == CASE_OF_NORMAL_ONE_WEEK_DELIVERY) {
            System.out.println("1 weeks remaining");
            this.weeksRemaining--;
        } else if (weeksRemaining == ASSEMBLY_FINISHED) {
            System.out.println("Car is done");
            this.currentProduction.ship();
            ProductionId productionId = currentProduction.getProductionId();
            this.carProductionRepository.remove(productionId);
            this.batteryProductionRepository.remove(productionId);
            assemblyLineMediator.notify(this.getClass());
            this.isCarCompleteInProduction = false;
        }
    }

    public void shutdown() {
        this.isBatteryInFire = true;
        if (isCarCompleteInProduction)
            this.waitingList.addFirst(currentProduction);
        this.isCarCompleteInProduction = false;
    }

    public void reactivate() {
        this.isBatteryInFire = false;
    }

    public void startNext() {

        if (this.isBatteryInFire)
            return;

        setUpForProduction();
    }

    private void setUpForProduction() {
        this.currentProduction = this.waitingList.pop();
        this.weeksRemaining = Math.random() < FIFTY_PERCENT_CHANCE ? ASSEMBLY_DELAY_IN_WEEKS * 2
                : ASSEMBLY_DELAY_IN_WEEKS;

        productionLineEmailNotifier.sendAssemblyStartedEmail(currentProduction.getProductionId(), weeksRemaining);

        this.isCarCompleteInProduction = true;
    }

    public void setMediator(AssemblyLineMediator assemblyLineMediator) {
        this.assemblyLineMediator = assemblyLineMediator;
    }
}
