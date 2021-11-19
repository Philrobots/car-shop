package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle.VehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsNotShutdownException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsShutdownException;
import ca.ulaval.glo4003.evulution.domain.email.Email;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.production.BatteryProduction;
import ca.ulaval.glo4003.evulution.domain.production.VehicleProduction;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;

import java.util.*;

public class ProductionLine {

    private final VehicleAssemblyLine vehicleAssemblyLine;
    private final BatteryAssemblyLine batteryAssemblyLine;
    private final CompleteCarAssemblyLine completeCarAssemblyLine;
    private final EmailFactory emailFactory;
    private Set<String> emails = new HashSet<String>();
    private boolean isShutdown = false;

    public ProductionLine(VehicleAssemblyLine vehicleAssemblyLine, BatteryAssemblyLine batteryAssemblyLine,
            CompleteCarAssemblyLine completeCarAssemblyLine, EmailFactory emailFactory) {
        this.vehicleAssemblyLine = vehicleAssemblyLine;
        this.batteryAssemblyLine = batteryAssemblyLine;
        this.completeCarAssemblyLine = completeCarAssemblyLine;
        this.emailFactory = emailFactory;
    }

    public void addSaleToAssemblyLines(VehicleProduction vehicleProduction, BatteryProduction batteryProduction,
            Sale sale) {
        this.vehicleAssemblyLine.addProduction(vehicleProduction);
        this.batteryAssemblyLine.addProduction(batteryProduction);
        this.completeCarAssemblyLine.addCommand(sale);
        this.emails.add(sale.getEmail());
    }

    public void advanceAssemblyLines() {
        this.vehicleAssemblyLine.advance();
        this.batteryAssemblyLine.advance();
        this.completeCarAssemblyLine.advance();
    }

    public void shutdown() {
        if (isShutdown)
            throw new AssemblyLineIsShutdownException();
        this.vehicleAssemblyLine.shutdown();
        this.batteryAssemblyLine.shutdown();
        this.completeCarAssemblyLine.shutdown();
        this.isShutdown = true;
        this.sendEmailToConsumers();
    }

    private void sendEmailToConsumers() {
        List<String> recipients = new ArrayList<String>();
        recipients.addAll(this.emails);
        this.emailFactory.createAssemblyFireBatteriesEmail(recipients).send();
    }

    public void reactivate() {
        if (!isShutdown)
            throw new AssemblyLineIsNotShutdownException();
        this.vehicleAssemblyLine.reactivate();
        this.batteryAssemblyLine.reactivate();
        this.completeCarAssemblyLine.reactivate();
        this.isShutdown = false;
    }
}