package ca.ulaval.glo4003.evulution.domain.car;

import ca.ulaval.glo4003.evulution.domain.car.exception.BadCarSpecsException;

import java.util.List;

public class CarFactory {

    private List<ModelInformationDto> modelMapperDtos;

    public CarFactory(List<ModelInformationDto> modelMapperDtos) {
        this.modelMapperDtos = modelMapperDtos;
    }

    public Car create(String name, String color) {
        for (ModelInformationDto modelMapperDto : modelMapperDtos) {
            if (modelMapperDto.name.equals(name) && color.equals("white")) {
                return new Car(modelMapperDto.name, modelMapperDto.style, modelMapperDto.effeciency_equivalence_rate,
                        modelMapperDto.base_price, modelMapperDto.time_to_produce, color);
            }
        }

        throw new BadCarSpecsException();

    }
}
