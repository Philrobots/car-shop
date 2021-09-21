package ca.ulaval.glo4003.evulution.service.sale;

import ca.ulaval.glo4003.evulution.api.sale.dto.TransactionIdDto;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public class TransactionIdAssembler {
    public TransactionIdDto transactionIdToDto(TransactionId transactionId) {
        return new TransactionIdDto(transactionId.getId());
    }

}
