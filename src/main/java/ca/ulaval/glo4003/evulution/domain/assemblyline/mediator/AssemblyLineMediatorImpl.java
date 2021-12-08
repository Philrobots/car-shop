package ca.ulaval.glo4003.evulution.domain.assemblyline.mediator;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyState;
import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle.CarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;

public class AssemblyLineMediatorImpl implements AssemblyLineMediator {

    private BatteryAssemblyLine batteryAssemblyLine;
    private CompleteAssemblyLine completeAssemblyLine;
    private CarAssemblyLine carAssemblyLine;
    private AssemblyState state = AssemblyState.CAR;

    public AssemblyLineMediatorImpl(BatteryAssemblyLine batteryAssemblyLine, CompleteAssemblyLine completeAssemblyLine,
                                    CarAssemblyLine carAssemblyLine) {
        this.batteryAssemblyLine = batteryAssemblyLine;
        this.completeAssemblyLine = completeAssemblyLine;
        this.carAssemblyLine = carAssemblyLine;
    }

    @Override
    public void notify(Class assemblyLineClass) throws EmailException {
        if (assemblyLineClass.equals(CarAssemblyLine.class)) {
            state = AssemblyState.BATTERY;
            this.batteryAssemblyLine.startNext();
        } else if (assemblyLineClass.equals(BatteryAssemblyLine.class)) {
            state = AssemblyState.ASSEMBLY;
            this.completeAssemblyLine.startNext();
        } else if (assemblyLineClass.equals(CompleteAssemblyLine.class)) {
            state = AssemblyState.CAR;
            this.carAssemblyLine.startNext();
        }
    }

    @Override
    public AssemblyState getState() {
        return state;
    }
}
