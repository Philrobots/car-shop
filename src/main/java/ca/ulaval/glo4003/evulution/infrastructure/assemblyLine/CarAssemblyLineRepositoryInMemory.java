package ca.ulaval.glo4003.evulution.infrastructure.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.CarAssemblyLineRepository;
import ca.ulaval.glo4003.evulution.domain.car.ModelInformationDto;
import ca.ulaval.glo4003.evulution.infrastructure.mappers.JsonFileMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarAssemblyLineRepositoryInMemory implements CarAssemblyLineRepository {

    public Map<String, Integer> getCarTimeToProduce() {
        List<ModelInformationDto> parseModels = JsonFileMapper.parseModels();
        Map<String, Integer> carTimesProduce = new HashMap<>();

        for (ModelInformationDto modelInformationDto : parseModels) {
            Integer timeToProduce = Integer.parseInt(modelInformationDto.time_to_produce);
            carTimesProduce.put(modelInformationDto.name, timeToProduce);
        }

        return carTimesProduce;
    }
}
