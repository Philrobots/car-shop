package ca.ulaval.glo4003.evulution.domain.car;

import java.math.BigDecimal;

public class Car {
    private String name;
    private String style;
    private Integer efficiencyEquivalenceRate;
    private BigDecimal basePrice;
    private String timeToProduce;
    private String color;

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

    public String getStyle() {
        return style;
    }

    public int getTimeToProduceAsInt() {
        return Integer.parseInt(timeToProduce);
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }
}
