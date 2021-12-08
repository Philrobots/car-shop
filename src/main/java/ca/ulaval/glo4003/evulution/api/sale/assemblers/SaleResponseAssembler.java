package ca.ulaval.glo4003.evulution.api.sale.assemblers;

import ca.ulaval.glo4003.evulution.api.sale.dto.SaleResponse;
import ca.ulaval.glo4003.evulution.service.sale.dto.SaleCreatedDto;

public class SaleResponseAssembler {
    public SaleResponse toResponse(SaleCreatedDto saleCreatedDto) {
        SaleResponse saleResponse = new SaleResponse();
        saleResponse.delivery_id = saleCreatedDto.deliveryId;
        saleResponse.transaction_id = saleCreatedDto.saleId;
        return saleResponse;
    }
}
