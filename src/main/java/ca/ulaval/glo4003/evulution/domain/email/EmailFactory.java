package ca.ulaval.glo4003.evulution.domain.email;

import java.time.LocalDate;
import java.util.List;

public class EmailFactory {
    private String SUBJECT = "Evulution warning delay";
    private String FIRE_BATTERIES_EMAIL = "We are sorry to inform you we encountered problems during the battery building stage. The new delivery date is undetermined";
    private String NEW_DELAY_EMAIL = "We are sorry to inform you we encountered difficulties during the assembly step. Expecting new delivery date: ";

    public Email createAssemblyDelayEmail(List<String> recipients, LocalDate newDeliveryDate) {
        String message = NEW_DELAY_EMAIL + newDeliveryDate.toString();
        return new Email(recipients, SUBJECT, message);
    }

    public Email createAssemblyFireBatteriesEmail(List<String> recipients) {
        return new Email(recipients, SUBJECT, FIRE_BATTERIES_EMAIL);
    }
}
