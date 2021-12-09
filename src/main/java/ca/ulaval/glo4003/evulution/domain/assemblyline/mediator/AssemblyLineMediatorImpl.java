package ca.ulaval.glo4003.evulution.domain.assemblyline.mediator;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyLineType;
import ca.ulaval.glo4003.evulution.domain.assemblyline.complete.CompleteAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;

public class AssemblyLineMediatorImpl implements AssemblyLineMediator {

    private BatteryAssemblyLineSequential batteryAssemblyLine;
    private CompleteAssemblyLineSequential completeAssemblyLine;
    private CarAssemblyLineSequential carAssemblyLine;
    private AssemblyLineType state = AssemblyLineType.CAR;

    public AssemblyLineMediatorImpl(BatteryAssemblyLineSequential batteryAssemblyLine, CompleteAssemblyLineSequential completeAssemblyLine,
                                    CarAssemblyLineSequential carAssemblyLine) {
        this.batteryAssemblyLine = batteryAssemblyLine;
        this.completeAssemblyLine = completeAssemblyLine;
        this.carAssemblyLine = carAssemblyLine;
    }

    @Override
    public void notify(Class assemblyLineClass) throws EmailException {
        if (assemblyLineClass.equals(CarAssemblyLineSequential.class)) {
            state = AssemblyLineType.BATTERY;
            this.batteryAssemblyLine.startNext();
        } else if (assemblyLineClass.equals(BatteryAssemblyLineSequential.class)) {
            state = AssemblyLineType.COMPLETE;
            this.completeAssemblyLine.startNext();
        } else if (assemblyLineClass.equals(CompleteAssemblyLineSequential.class)) {
            state = AssemblyLineType.CAR;
            this.carAssemblyLine.startNext();
        }
    }

    @Override
    public AssemblyLineType getState() {
        return state;
    }
}
