package ca.ulaval.glo4003.evulution.domain.email;

import java.time.LocalDate;
import java.util.List;

public class EmailFactory {

    private EmailSender emailSender;
    private String DELAY_SUBJECT = "Evulution warning delay";
    private String FIRE_BATTERIES_EMAIL = "We are sorry to inform you we encountered problems during the battery building stage. The new delivery date is undetermined";
    private String NEW_DELAY_EMAIL = "We are sorry to inform you we encountered difficulties during the assembly step. Expecting new delivery date: ";
    private String STATUS_UPDATE_SUBJECT = "Update on your Evulution order";
    private String BATTERY_BUILT_EMAIL = "Your battery is currently in production. Expected build time in weeks: ";
    private String VEHICLE_BUILT_EMAIL = "Your vehicle is currently in production. Expected build time in weeks: ";
    private String VEHICLE_COMPLETED_EMAIL = "Your command is currently in assembly. Expected build time in weeks: ";

    public EmailFactory(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public Email createAssemblyDelayEmail(List<String> recipients, LocalDate newDeliveryDate) {
        String message = NEW_DELAY_EMAIL + newDeliveryDate.toString();
        return new Email(recipients, DELAY_SUBJECT, message, emailSender);
    }

    public Email createAssemblyFireBatteriesEmail(List<String> recipients) {
        return new Email(recipients, DELAY_SUBJECT, FIRE_BATTERIES_EMAIL, emailSender);
    }

    public Email createBatteryBuiltEmail(List<String> recipients, Integer productionTimeInWeeks) {
        String message = BATTERY_BUILT_EMAIL + productionTimeInWeeks;
        return new Email(recipients, STATUS_UPDATE_SUBJECT, message, emailSender);
    }

    public Email createVehicleBuiltEmail(List<String> recipients, Integer productionTimeInWeeks) {
        String message = VEHICLE_BUILT_EMAIL + productionTimeInWeeks;
        return new Email(recipients, STATUS_UPDATE_SUBJECT, message, emailSender);
    }

    public Email createVehicleCompletedEmail(List<String> recipients, Integer productionTimeInWeeks) {
        String message = VEHICLE_COMPLETED_EMAIL + productionTimeInWeeks;
        return new Email(recipients, STATUS_UPDATE_SUBJECT, message, emailSender);
    }
}
