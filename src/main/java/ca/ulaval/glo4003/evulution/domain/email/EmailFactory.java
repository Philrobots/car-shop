package ca.ulaval.glo4003.evulution.domain.email;

import java.time.LocalDate;
import java.util.List;

public class EmailFactory {

    public Email createAssemblyDelayEmail(List<String> recipients, LocalDate newDeliveryDate) {
        // TODO Check with the client
        String subject = "Evulution warning delay";
        String message = "We are sorry to inform you we encountered difficulties during the assembly step. Expecting new delivery date: "
                + newDeliveryDate.toString();
        return new Email(recipients, subject, message);
    }
}
