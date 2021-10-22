package ca.ulaval.glo4003.evulution.domain.assemblyline.mediator;

import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public interface AssemblyLineMediator {
    void notify(Class assemblyLineClass);
}
