package ca.ulaval.glo4003.evulution.domain.assemblyline.mediator;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyState;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;

public interface AssemblyLineMediator {
    void notify(Class assemblyLineClass) throws EmailException;

    AssemblyState getState();
}
