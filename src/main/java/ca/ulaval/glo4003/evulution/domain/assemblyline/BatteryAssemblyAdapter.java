package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public interface BatteryAssemblyAdapter {
    AssemblyStatus getStatus(TransactionId transactionId);

    void newBatteryCommand(TransactionId transactionId, String command);

    void advance();
}
