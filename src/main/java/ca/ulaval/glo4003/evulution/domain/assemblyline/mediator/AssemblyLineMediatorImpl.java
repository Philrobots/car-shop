package ca.ulaval.glo4003.evulution.domain.assemblyline.mediator;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyState;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteCarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle.VehicleAssemblyLine;

public class AssemblyLineMediatorImpl implements AssemblyLineMediator {

    private BatteryAssemblyLine batteryAssemblyLine;
    private CompleteCarAssemblyLine completeCarAssemblyLine;
    private VehicleAssemblyLine vehicleAssemblyLine;
    private AssemblyState state = AssemblyState.CAR;

    public AssemblyLineMediatorImpl(BatteryAssemblyLine batteryAssemblyLine,
            CompleteCarAssemblyLine completeCarAssemblyLine, VehicleAssemblyLine vehicleAssemblyLine) {
        this.batteryAssemblyLine = batteryAssemblyLine;
        this.completeCarAssemblyLine = completeCarAssemblyLine;
        this.vehicleAssemblyLine = vehicleAssemblyLine;
    }

    @Override
    public void notify(Class assemblyLineClass) {
        if (assemblyLineClass.equals(VehicleAssemblyLine.class)) {
            state = AssemblyState.BATTERY;
            this.batteryAssemblyLine.startNext();
        } else if (assemblyLineClass.equals(BatteryAssemblyLine.class)) {
            state = AssemblyState.ASSEMBLY;
            this.completeCarAssemblyLine.startNext();
        } else if (assemblyLineClass.equals(CompleteCarAssemblyLine.class)) {
            state = AssemblyState.CAR;
            this.vehicleAssemblyLine.startNext();
        }
    }

    @Override
    public AssemblyState getState() {
        return state;
    }
}
