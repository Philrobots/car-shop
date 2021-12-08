package ca.ulaval.glo4003.evulution.domain.assemblyline.mediator;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyState;
import ca.ulaval.glo4003.evulution.domain.assemblyline.complete.CompleteAssemblyLineSeq;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;

public class AssemblyLineMediatorImpl implements AssemblyLineMediator {

    private BatteryAssemblyLineSequential batteryAssemblyLine;
    private CompleteAssemblyLineSeq completeAssemblyLine;
    private CarAssemblyLineSequential carAssemblyLine;
    private AssemblyState state = AssemblyState.CAR;

    public AssemblyLineMediatorImpl(BatteryAssemblyLineSequential batteryAssemblyLine,
            CompleteAssemblyLineSeq completeAssemblyLine, CarAssemblyLineSequential carAssemblyLine) {
        this.batteryAssemblyLine = batteryAssemblyLine;
        this.completeAssemblyLine = completeAssemblyLine;
        this.carAssemblyLine = carAssemblyLine;
    }

    @Override
    public void notify(Class assemblyLineClass) throws EmailException {
        if (assemblyLineClass.equals(CarAssemblyLineSequential.class)) {
            state = AssemblyState.BATTERY;
            this.batteryAssemblyLine.startNext();
        } else if (assemblyLineClass.equals(BatteryAssemblyLineSequential.class)) {
            state = AssemblyState.ASSEMBLY;
            this.completeAssemblyLine.startNext();
        } else if (assemblyLineClass.equals(CompleteAssemblyLineSeq.class)) {
            state = AssemblyState.CAR;
            this.carAssemblyLine.startNext();
        }
    }

    @Override
    public AssemblyState getState() {
        return state;
    }
}
