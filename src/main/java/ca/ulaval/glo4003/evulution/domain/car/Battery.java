package ca.ulaval.glo4003.evulution.domain.car;

import java.math.BigDecimal;

public class Battery {
    private String name;
    private String baseNRCANRange;
    private Integer capacity;
    private BigDecimal price;
    private String timeToProduce;

    public Battery(String name, String baseNRCANRange, Integer capacity, BigDecimal price, String timeToProduce) {
        this.name = name;
        this.baseNRCANRange = baseNRCANRange;
        this.capacity = capacity;
        this.price = price;
        this.timeToProduce = timeToProduce;
    }

    public Integer calculateEstimatedRange(Integer efficiencyEquivalenceRate) {
        return (Integer.parseInt(this.baseNRCANRange) * efficiencyEquivalenceRate) / 100;
    }

    public String getName() {
        return name;
    }
}
