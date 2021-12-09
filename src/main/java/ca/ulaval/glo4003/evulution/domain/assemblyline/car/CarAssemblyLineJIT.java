package ca.ulaval.glo4003.evulution.domain.assemblyline.car;

import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;
import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;

import java.util.LinkedList;
import java.util.List;

public class CarAssemblyLineJIT implements CarAssemblyLine {
    private LinkedList<CarProduction> carWaitingList = new LinkedList<>();
    @Override
    public void addProduction(CarProduction carProduction) throws EmailException {

    }

    @Override
    public void advance() throws EmailException {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void reactivate() throws EmailException {

    }

    @Override
    public void startNext() throws EmailException {

    }

    @Override
    public void transferWaitingList(CarAssemblyLine carAssemblyLine) {

    }

    @Override
    public List<CarProduction> getWaitingList() {
//        if (isCarInProduction) this.carProductionWaitList.add(currentCarProduction);
        LinkedList<CarProduction> returnList = new LinkedList<>(this.carWaitingList);
        carWaitingList.clear();

        return returnList;
    }
}
