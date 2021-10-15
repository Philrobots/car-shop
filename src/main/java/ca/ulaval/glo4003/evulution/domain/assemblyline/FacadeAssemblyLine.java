package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.Map;

public interface FacadeAssemblyLine {

    AssemblyStatus getStatus(TransactionId transactionId);

    void newCommand(TransactionId transactionId, String command);

    void advance();
}
