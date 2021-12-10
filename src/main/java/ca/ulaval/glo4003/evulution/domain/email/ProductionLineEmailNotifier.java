package ca.ulaval.glo4003.evulution.domain.email;

import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductionLineEmailNotifier {
    private HashMap<ProductionId, String> productionIdToEmail = new HashMap<>();
    private EmailFactory emailFactory;

    public void addEmailWithProduction(ProductionId productionId, String email) {
        productionIdToEmail.put(productionId, email);
    }

    public void removeEmailFromProductionId(ProductionId productionId){
        productionIdToEmail.remove(productionId);
    }

    public void sendCarStartedEmail(ProductionId productionId, Integer timeToProduce){
        try{
            String email = productionIdToEmail.get(productionId);
            emailFactory.createVehicleInProductionEmail(List.of(email), timeToProduce).send();
        } catch (EmailException e){
            e.printStackTrace();
        }
    }

    public void sendAssemblyStartedEmail(ProductionId productionId, Integer timeToProduce){
        try{
            String email = productionIdToEmail.get(productionId);
            emailFactory.createAssemblyInProductionEmail(List.of(email), timeToProduce).send();
        } catch (EmailException e){
            e.printStackTrace();
        }
    }

    public void sendBatteryStartedEmail(ProductionId productionId, Integer timeToProduce){
        try{
            String email = productionIdToEmail.get(productionId);
            emailFactory.createBatteryInProductionEmail(List.of(email), timeToProduce).send();
        } catch (EmailException e){
            e.printStackTrace();
        }
    }
    public void sendAssemblyDelayEmail(ProductionId productionId, LocalDate localDate){
        try{
            String email = productionIdToEmail.get(productionId);
            emailFactory.createAssemblyDelayEmail(List.of(email), localDate).send();
        } catch (EmailException e){
            e.printStackTrace();
        }
    }

    public void sendFireBatteriesEmail(){
        try{
            emailFactory.createAssemblyFireBatteriesEmail(List.copyOf(productionIdToEmail.values())).send();
        } catch (EmailException e){
            e.printStackTrace();
        }
    }

}
