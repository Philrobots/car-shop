package ca.ulaval.glo4003.evulution.domain.assemblyline.mediator;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyLineType;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;

public interface AssemblyLineMediator {
    void notify(Class assemblyLineClass) throws EmailException;

    AssemblyLineType getState();
}
