package ca.ulaval.glo4003.evulution.domain.car;

import ca.ulaval.glo4003.evulution.domain.car.exceptions.BadCarSpecsException;

import java.math.BigDecimal;
import java.util.List;

public class CarFactory {

    private List<ModelInformationDto> modelMapperDtos;

    public CarFactory(List<ModelInformationDto> modelMapperDtos) {
        this.modelMapperDtos = modelMapperDtos;
    }

    public Car create(String name, String color) throws BadCarSpecsException {
        for (ModelInformationDto modelMapperDto : modelMapperDtos) {
            if (modelMapperDto.name.equals(name) && color.equals("white")) {
                return new Car(modelMapperDto.name, modelMapperDto.style, modelMapperDto.effeciency_equivalence_rate,
                        BigDecimal.valueOf(modelMapperDto.base_price), Integer.parseInt(modelMapperDto.time_to_produce),
                        color);
            }
        }

        throw new BadCarSpecsException();

    }
}
