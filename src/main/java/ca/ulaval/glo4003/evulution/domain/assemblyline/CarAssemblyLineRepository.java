package ca.ulaval.glo4003.evulution.domain.assemblyline;

import java.util.Map;

public interface CarAssemblyLineRepository {
    Map<String, Integer> getCarTimeToProduce();
}
