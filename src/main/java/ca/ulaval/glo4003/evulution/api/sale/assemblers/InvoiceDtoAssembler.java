package ca.ulaval.glo4003.evulution.api.sale.assemblers;

import ca.ulaval.glo4003.evulution.api.sale.dto.InvoiceRequest;
import ca.ulaval.glo4003.evulution.service.sale.dto.InvoiceDto;

public class InvoiceDtoAssembler {
    public InvoiceDto fromRequest(InvoiceRequest invoiceRequest) {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.accountNumber = invoiceRequest.account_no;
        invoiceDto.bankNumber = invoiceRequest.bank_no;
        invoiceDto.frequency = invoiceRequest.frequency;
        return invoiceDto;
    }
}
