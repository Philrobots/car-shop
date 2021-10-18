package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.CompleteAssemblyException;
import ca.ulaval.glo4003.evulution.domain.email.Email;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.email.EmailSender;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;

import java.time.LocalDate;
import java.util.List;

public class CompleteCarAssemblyLine {
    // TODO : variable d'environnement
    private static final double FIFTY_PERCENT_CHANCE = 0.5;
    private static final int ASSEMBLY_DELAY_IN_WEEKS = 1;

    private final int timeOfWaitForOneWeek;
    private final EmailFactory emailFactory;
    private final EmailSender emailSender;

    public CompleteCarAssemblyLine(int equivalenceOfOneWeekInSeconds, EmailFactory emailFactory,
            EmailSender emailSender) {
        this.timeOfWaitForOneWeek = equivalenceOfOneWeekInSeconds * 1000;
        this.emailFactory = emailFactory;
        this.emailSender = emailSender;
    }

    public void completeCarCommand(Sale sale) {
        try {
            if (Math.random() < FIFTY_PERCENT_CHANCE)
                Thread.sleep((long) sale.getAssemblyTimeInWeek() * timeOfWaitForOneWeek);
            else {
                LocalDate newExpectedDeliveryDate = sale.addDelayInWeeks(ASSEMBLY_DELAY_IN_WEEKS);
                Email email = emailFactory.createAssemblyDelayEmail(List.of(sale.getEmail()), newExpectedDeliveryDate);
                emailSender.sendEmail(email);
                Thread.sleep((long) (sale.getAssemblyTimeInWeek() + ASSEMBLY_DELAY_IN_WEEKS) * timeOfWaitForOneWeek);
            }
        } catch (InterruptedException e) {
            throw new CompleteAssemblyException();
        }
    }
}
