package ca.ulaval.glo4003.evulution.domain.car;

import java.math.BigDecimal;

public class Battery {
    private String type;
    private String baseNRCANRange;
    private Integer capacity;
    private BigDecimal price;
    private Integer timeToProduce;
    private boolean isAssembled = false;

    public Battery(String type, String baseNRCANRange, Integer capacity, BigDecimal price, Integer timeToProduce) {
        this.type = type;
        this.baseNRCANRange = baseNRCANRange;
        this.capacity = capacity;
        this.price = price;
        this.timeToProduce = timeToProduce;
    }

    public Integer calculateEstimatedRange(Integer efficiencyEquivalenceRate) {
        return (Integer.parseInt(this.baseNRCANRange) * efficiencyEquivalenceRate) / 100;
    }

    public String getType() {
        return type;
    }

    public void setBatteryAsAssembled() {
        isAssembled = true;
    }

    public int getTimeToProduce() {
        return timeToProduce;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
