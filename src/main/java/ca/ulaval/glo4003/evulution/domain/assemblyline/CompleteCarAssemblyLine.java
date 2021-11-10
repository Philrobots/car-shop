package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryStatus;
import ca.ulaval.glo4003.evulution.domain.email.Email;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.email.EmailSender;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class CompleteCarAssemblyLine {
    private static final double FIFTY_PERCENT_CHANCE = 0.5;
    private static final Integer ASSEMBLY_DELAY_IN_WEEKS = 1;

    private final EmailFactory emailFactory;
    private final EmailSender emailSender;
    private final VehicleRepository vehicleRepository;
    private final BatteryRepository batteryRepository;
    private LinkedList<Sale> waitingList = new LinkedList<>();
    private Sale currentSale;
    private int weeksRemaining;
    private boolean isCarCompleteInProduction = false;

    public CompleteCarAssemblyLine(EmailFactory emailFactory, EmailSender emailSender,
            VehicleRepository vehicleRepository, BatteryRepository batteryRepository) {
        this.emailFactory = emailFactory;
        this.emailSender = emailSender;
        this.vehicleRepository = vehicleRepository;
        this.batteryRepository = batteryRepository;
    }

    public void addCommand(Sale sale) {
        this.waitingList.add(sale);
    }

    public void advance() {

        if (!isCarCompleteInProduction)
            return;

        if (weeksRemaining == 2) {
            LocalDate newExpectedDeliveryDate = this.currentSale.addDelayInWeeks(ASSEMBLY_DELAY_IN_WEEKS);
            Email email = emailFactory.createAssemblyDelayEmail(List.of(this.currentSale.getEmail()),
                    newExpectedDeliveryDate);
            emailSender.sendEmail(email);
            this.weeksRemaining--;
        } else if (weeksRemaining == 1) {
            this.weeksRemaining--;
        } else if (weeksRemaining == 0) {
            this.currentSale.setDeliveryStatus(DeliveryStatus.SHIPPED);
            this.isCarCompleteInProduction = false;
        }
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
        this.currentSale = this.waitingList.pop();
        this.vehicleRepository.remove(this.currentSale.getCarName());
        this.batteryRepository.remove(this.currentSale.getBatteryType());
        this.weeksRemaining = Math.random() < FIFTY_PERCENT_CHANCE ? ASSEMBLY_DELAY_IN_WEEKS * 2
                : ASSEMBLY_DELAY_IN_WEEKS;
        this.isCarCompleteInProduction = true;
    }

    public List<Sale> getSalesWaitingList() {
        return this.waitingList;
    }

    public boolean getIsCarInProduction() {
        return this.isCarCompleteInProduction;
    }
}
