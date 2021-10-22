package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.CompleteAssemblyException;
import ca.ulaval.glo4003.evulution.domain.email.Email;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.email.EmailSender;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import jakarta.ws.rs.core.Link;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class CompleteCarAssemblyLine {
    // TODO : variable d'environnement
    private static final double FIFTY_PERCENT_CHANCE = 0.5;
    private static final Integer ASSEMBLY_DELAY_IN_WEEKS = 1;

    private final Integer assemblyTimeInWeeks;
    private final Integer timeOfWaitForOneWeek;
    private final EmailFactory emailFactory;
    private final EmailSender emailSender;
    private LinkedList<Sale> completeCars = new LinkedList<>();
    private Sale currentSale;
    private int weekRemining;
    private boolean isCarCompleteInProduction = false;

    public CompleteCarAssemblyLine(Integer equivalenceOfOneWeekInSeconds, Integer assemblyTimeInWeeks,
            EmailFactory emailFactory, EmailSender emailSender) {
        this.timeOfWaitForOneWeek = equivalenceOfOneWeekInSeconds * 1000;
        this.assemblyTimeInWeeks = assemblyTimeInWeeks;
        this.emailFactory = emailFactory;
        this.emailSender = emailSender;
    }

    public void addCommand(Sale sale) {
        this.completeCars.add(sale);
    }

    public void completeCarCommand(Sale sale) {
        try {
            if (Math.random() < FIFTY_PERCENT_CHANCE)
                Thread.sleep((long) this.assemblyTimeInWeeks * timeOfWaitForOneWeek);
            else {
                LocalDate newExpectedDeliveryDate = sale.addDelayInWeeks(ASSEMBLY_DELAY_IN_WEEKS);
                Email email = emailFactory.createAssemblyDelayEmail(List.of(sale.getEmail()), newExpectedDeliveryDate);
                emailSender.sendEmail(email);
                Thread.sleep((long) (this.assemblyTimeInWeeks + ASSEMBLY_DELAY_IN_WEEKS) * timeOfWaitForOneWeek);
            }
            sale.deliverToCampus();
        } catch (InterruptedException e) {
            throw new CompleteAssemblyException();
        }
    }

    public void advance() {

        if (!isCarCompleteInProduction)
            return;

        if (weekRemining == 2) {
            LocalDate newExpectedDeliveryDate = this.currentSale.addDelayInWeeks(ASSEMBLY_DELAY_IN_WEEKS);
            Email email = emailFactory.createAssemblyDelayEmail(List.of(this.currentSale.getEmail()),
                    newExpectedDeliveryDate);
            emailSender.sendEmail(email);
            this.weekRemining--;
        } else if (weekRemining == 1) {
            this.isCarCompleteInProduction = false;
            this.weekRemining--;
        }
    }

    // TODO Refactor mon breault ca pu
    public void startNext() {
        this.currentSale = this.completeCars.pop();

        if (Math.random() < FIFTY_PERCENT_CHANCE) {
            this.weekRemining = 2;
        } else {
            this.weekRemining = 1;
        }

        this.isCarCompleteInProduction = true;

    }
}
