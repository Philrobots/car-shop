package ca.ulaval.glo4003.evulution.domain.assemblyline.scheduler;

import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteCarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLine;

import java.util.Timer;
import java.util.TimerTask;

public class AssemblyLineScheduler {
    private BatteryAssemblyLine batteryAssemblyLine;
    private VehicleAssemblyLine vehicleAssemblyLine;
    private CompleteCarAssemblyLine completeCarAssemblyLine;
    private int timeToWaitForOneWeek;
    private Timer timer = new Timer();

    public AssemblyLineScheduler(BatteryAssemblyLine batteryAssemblyLine, VehicleAssemblyLine vehicleAssemblyLine, CompleteCarAssemblyLine completeCarAssemblyLine, int equivalenceOfOneWeekInSeconds) {
        this.batteryAssemblyLine = batteryAssemblyLine;
        this.vehicleAssemblyLine = vehicleAssemblyLine;
        this.completeCarAssemblyLine = completeCarAssemblyLine;
        this.timeToWaitForOneWeek = equivalenceOfOneWeekInSeconds * 1000;
    }

    public void startScheduling(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                advanceAssemblyLines();
            }
        }, 0 , timeToWaitForOneWeek);
    }

    private void advanceAssemblyLines(){
        this.vehicleAssemblyLine.advance();
        this.batteryAssemblyLine.advance();
        this.completeCarAssemblyLine.advance();
    }
}
