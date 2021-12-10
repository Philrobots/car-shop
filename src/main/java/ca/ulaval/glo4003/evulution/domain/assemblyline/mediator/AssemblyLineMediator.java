package ca.ulaval.glo4003.evulution.domain.assemblyline.mediator;

public interface AssemblyLineMediator {
    boolean notify(Object assemblyLineClass);

    boolean shouldCarReactivateProduction();

    boolean shouldBatteryReactivateProduction();
}
