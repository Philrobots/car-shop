package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.email.Email;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.email.EmailSender;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class CompleteCarAssemblyLine {
    // TODO : variable d'environnement
    private static final double FIFTY_PERCENT_CHANCE = 0.5;
    private static final Integer ASSEMBLY_DELAY_IN_WEEKS = 1;

    private final EmailFactory emailFactory;
    private final EmailSender emailSender;
    private LinkedList<Sale> waitingList = new LinkedList<>();
    private Sale currentSale;
    private int weeksRemaining;
    private boolean isCarCompleteInProduction = false;

    public CompleteCarAssemblyLine(EmailFactory emailFactory, EmailSender emailSender) {
        this.emailFactory = emailFactory;
        this.emailSender = emailSender;
    }

    public void addCommand(Sale sale) {
        this.waitingList.add(sale);
    }

    // public void completeCarCommand(Sale sale) {
    // try {
    // if (Math.random() < FIFTY_PERCENT_CHANCE)
    // Thread.sleep((long) this.assemblyTimeInWeeks * timeOfWaitForOneWeek);
    // else {
    // LocalDate newExpectedDeliveryDate = sale.addDelayInWeeks(ASSEMBLY_DELAY_IN_WEEKS);
    // Email email = emailFactory.createAssemblyDelayEmail(List.of(sale.getEmail()), newExpectedDeliveryDate);
    // emailSender.sendEmail(email);
    // Thread.sleep((long) (this.assemblyTimeInWeeks + ASSEMBLY_DELAY_IN_WEEKS) * timeOfWaitForOneWeek);
    // }
    // sale.deliverToCampus();
    // } catch (InterruptedException e) {
    // throw new CompleteAssemblyException();
    // }
    // }

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
            System.out.println("CAR FULLY ASSEMBLED");
            this.isCarCompleteInProduction = false;
        }
    }

    public void startNext() {
        this.currentSale = this.waitingList.pop();
        this.weeksRemaining = Math.random() < FIFTY_PERCENT_CHANCE ? ASSEMBLY_DELAY_IN_WEEKS * 2
                : ASSEMBLY_DELAY_IN_WEEKS;
        this.isCarCompleteInProduction = true;
    }
}
