package ca.ulaval.glo4003.evulution.service.sale;

import ca.ulaval.glo4003.evulution.api.sale.dto.EstimatedRangeDto;

public class EstimatedRangeAssembler {
    public EstimatedRangeDto EstimatedRangeToDto(Integer estimatedRange) {
        return new EstimatedRangeDto(estimatedRange);
    }
}
