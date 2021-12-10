package ca.ulaval.glo4003.evulution.domain.assemblyline.mediator;

import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;

public interface AssemblyLineMediator {
    boolean notify(Object assemblyLineClass);

    boolean shouldCarReactivateProduction();

    boolean shouldBatteryReactivateProduction();
}
