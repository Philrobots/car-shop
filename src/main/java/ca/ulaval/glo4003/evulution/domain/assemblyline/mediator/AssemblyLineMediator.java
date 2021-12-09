package ca.ulaval.glo4003.evulution.domain.assemblyline.mediator;

import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;

public interface AssemblyLineMediator {
    void notify(Object assemblyLineClass) throws EmailException;

    boolean shouldCarReactivateProduction();

    boolean shouldBatteryReactivateProduction();
}
