package ca.ulaval.glo4003.evulution.domain.car;

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;

public class Car {
    private String name;
    private String style;
    private Integer efficiencyEquivalenceRate;
    private Integer basePrice;
    private String timeToProduce;
    private String color;

    public Car(String name, String style, Integer efficiencyEquivalenceRate, Integer basePrice, String timeToProduce,
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

}
