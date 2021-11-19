package ca.ulaval.glo4003.evulution.domain.assemblyline.mediator;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyState;

public interface AssemblyLineMediator {
    void notify(Class assemblyLineClass);

    AssemblyState getState();
}
