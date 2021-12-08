package ca.ulaval.glo4003.evulution.service.manufacture;

import ca.ulaval.glo4003.evulution.service.manufacture.dto.EstimatedRangeDto;

public class EstimatedRangeAssembler {
    public EstimatedRangeDto estimatedRangeToDto(int estimatedRange) {
        return new EstimatedRangeDto(estimatedRange);
    }
}
