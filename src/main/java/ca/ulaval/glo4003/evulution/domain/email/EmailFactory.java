package ca.ulaval.glo4003.evulution.domain.email;

import java.time.LocalDate;
import java.util.List;

public class EmailFactory {

    private EmailSender emailSender;
    private String DELAY_SUBJECT = "Evulution warning delay";
    private String FIRE_BATTERIES_EMAIL = "We are sorry to inform you we encountered problems during the battery building stage. The new delivery date is undetermined";
    private String NEW_DELAY_EMAIL = "We are sorry to inform you we encountered difficulties during the assembly step. Expecting new delivery date: ";
    private String STATUS_UPDATE_SUBJECT = "Update on your Evulution order";
    private String BATTERY_BUILT_EMAIL = "We are happy to inform you that your battery has been built. We will continue to keep you updated regarding the state of your vehicle.";
    private String VEHICLE_BUILT_EMAIL = "We are happy to inform you that your vehicle has been built. We will continue to keep you updated regarding the state of your vehicle.";
    private String VEHICLE_COMPLETED_EMAIL = "We are happy to inform you that your vehicle assembly is completed. We will send you an email soon regarding delivery.";

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

    public Email createBatteryBuiltEmail(List<String> recipients) {
        return new Email(recipients, STATUS_UPDATE_SUBJECT, BATTERY_BUILT_EMAIL, emailSender);
    }

    public Email createVehicleBuiltEmail(List<String> recipients) {
        return new Email(recipients, STATUS_UPDATE_SUBJECT, VEHICLE_BUILT_EMAIL, emailSender);
    }

    public Email createVehicleCompletedEmail(List<String> recipients) {
        return new Email(recipients, STATUS_UPDATE_SUBJECT, VEHICLE_COMPLETED_EMAIL, emailSender);
    }
}
