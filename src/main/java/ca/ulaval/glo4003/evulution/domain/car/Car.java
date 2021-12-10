package ca.ulaval.glo4003.evulution.domain.car;

import java.math.BigDecimal;

public class Car {
    private final String name;
    private final String style;
    private final Integer efficiencyEquivalenceRate;
    private final BigDecimal basePrice;
    private final String timeToProduce;
    private final String color;

    public Car(String name, String style, Integer efficiencyEquivalenceRate, BigDecimal basePrice, String timeToProduce,
            String color) {
        this.name = name;
        this.style = style;
        this.efficiencyEquivalenceRate = efficiencyEquivalenceRate;
        this.basePrice = basePrice;
        this.timeToProduce = timeToProduce;
        this.color = color;
    }

    public Integer getEfficiencyEquivalenceRate() {
        return efficiencyEquivalenceRate;
    }

    public String getName() {
        return this.name;
    }

    public int getTimeToProduceAsInt() {
        return Integer.parseInt(timeToProduce);
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }
}
