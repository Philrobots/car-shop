package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle.VehicleRepository;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryRepository;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryStatus;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class CompleteCarAssemblyLine {

    private static final double FIFTY_PERCENT_CHANCE = 0.5;
    private static final Integer ASSEMBLY_DELAY_IN_WEEKS = 1;

    private final EmailFactory emailFactory;
    private final VehicleRepository vehicleRepository;
    private final BatteryRepository batteryRepository;
    private AssemblyLineMediator assemblyLineMediator;
    private LinkedList<Sale> waitingList = new LinkedList<>();
    private Sale currentSale;
    private int weeksRemaining;
    private boolean isCarCompleteInProduction = false;
    private boolean isBatteryInFire = false;

    public CompleteCarAssemblyLine(EmailFactory emailFactory, VehicleRepository vehicleRepository,
            BatteryRepository batteryRepository) {
        this.emailFactory = emailFactory;
        this.vehicleRepository = vehicleRepository;
        this.batteryRepository = batteryRepository;
    }

    public void addCommand(Sale sale) {
        this.waitingList.add(sale);
    }

    public void advance() {

        if (!isCarCompleteInProduction || isBatteryInFire) {
            System.out.println("CompleteCarAssemblyLine skipped");
            return;
        }

        System.out.println("CompleteCarAssemblyLine advance with: " + this.currentSale.getEmail());

        if (weeksRemaining == 2) {
            LocalDate newExpectedDeliveryDate = this.currentSale.addDelayInWeeks(ASSEMBLY_DELAY_IN_WEEKS);
            emailFactory.createAssemblyDelayEmail(List.of(this.currentSale.getEmail()), newExpectedDeliveryDate).send();
            this.weeksRemaining--;
        } else if (weeksRemaining == 1) {
            this.weeksRemaining--;
        } else if (weeksRemaining == 0) {
            this.currentSale.setDeliveryStatus(DeliveryStatus.SHIPPED);
            this.vehicleRepository.remove(this.currentSale.getCarName());
            this.batteryRepository.remove(this.currentSale.getBatteryType());
            assemblyLineMediator.notify(this.getClass());
            this.isCarCompleteInProduction = false;
        }
    }

    public void shutdown() {
        this.isBatteryInFire = true;
        if (isCarCompleteInProduction)
            this.waitingList.add(currentSale);
        this.isCarCompleteInProduction = false;
    }

    public void reactivate() {
        this.isBatteryInFire = false;
    }

    public void setWeeksRemaining(int weeksRemaining) {
        this.weeksRemaining = weeksRemaining;
    }

    public void setCurrentSale(Sale sale) {
        this.currentSale = sale;
    }

    public void setIsCarCompleteInProduction(boolean isCarCompleteInProduction) {
        this.isCarCompleteInProduction = isCarCompleteInProduction;
    }

    public void startNext() {

        if (this.isBatteryInFire)
            return;

        setUpForProduction();
    }

    public List<Sale> getSalesWaitingList() {
        return this.waitingList;
    }

    public boolean getIsCarInProduction() {
        return this.isCarCompleteInProduction;
    }

    private void setUpForProduction() {
        this.currentSale = this.waitingList.pop();
        this.weeksRemaining = Math.random() < FIFTY_PERCENT_CHANCE ? ASSEMBLY_DELAY_IN_WEEKS * 2
                : ASSEMBLY_DELAY_IN_WEEKS;
        emailFactory.createVehicleCompletedEmail(List.of(this.currentSale.getEmail()), this.weeksRemaining).send();
        this.isCarCompleteInProduction = true;
    }

    public void setMediator(AssemblyLineMediator assemblyLineMediator) {
        this.assemblyLineMediator = assemblyLineMediator;
    }
}
