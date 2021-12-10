package ca.ulaval.glo4003.evulution.domain.assemblyline.mediator;

public interface AssemblyLineMediator {
    void notify(Object assemblyLineClass);

    boolean isCarState();

    boolean isCompleteOrBatteryState();
}
