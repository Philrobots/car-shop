package ca.ulaval.glo4003.evulution.domain.car;

import java.math.BigDecimal;

public class Battery {
    private final String type;
    private final String baseNRCANRange;
    private final Integer capacity;
    private final BigDecimal price;
    private final Integer timeToProduce;

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

    public int getTimeToProduce() {
        return timeToProduce;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
