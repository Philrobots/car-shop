package ca.ulaval.glo4003.evulution.api.sale.assemblers;

import ca.ulaval.glo4003.evulution.api.sale.dto.EstimatedRangeResponse;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.EstimatedRangeDto;

public class EstimatedRangeResponseAssembler {

    public EstimatedRangeResponse toResponse(EstimatedRangeDto estimatedRangeDto) {
        EstimatedRangeResponse estimatedRangeResponse = new EstimatedRangeResponse();
        estimatedRangeResponse.estimated_range = estimatedRangeDto.estimatedRange;
        return estimatedRangeResponse;
    }
}
