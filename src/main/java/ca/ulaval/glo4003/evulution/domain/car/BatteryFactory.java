package ca.ulaval.glo4003.evulution.domain.car;

import ca.ulaval.glo4003.evulution.domain.car.exceptions.BadCarSpecsException;

import java.math.BigDecimal;
import java.util.List;

public class BatteryFactory {

    private List<BatteryInformationDto> batteryMapperDtos;

    public BatteryFactory(List<BatteryInformationDto> batteryMapperDtos) {
        this.batteryMapperDtos = batteryMapperDtos;

    }

    public Battery create(String name) throws BadCarSpecsException {
        for (BatteryInformationDto batteryMapperDto : batteryMapperDtos) {
            if (batteryMapperDto.name.equals(name)) {
                return new Battery(batteryMapperDto.name, batteryMapperDto.base_NRCAN_range, batteryMapperDto.capacity,
                        BigDecimal.valueOf(batteryMapperDto.price), Integer.parseInt(batteryMapperDto.time_to_produce));
            }
        }
        throw new BadCarSpecsException();
    }
}
