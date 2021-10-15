package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;

public class CompleteCarAssemblyLine {
    private static final double FIFTY_PERCENT_CHANCE = 0.5;
    private final int timeOfWaitForOneWeek;

    public CompleteCarAssemblyLine(int equivalenceOfOneWeekInSeconds) {
        this.timeOfWaitForOneWeek = equivalenceOfOneWeekInSeconds * 1000;
    }

    public void completeCarCommand(Delivery delivery) {
        try {
            if (Math.random() < FIFTY_PERCENT_CHANCE)
                Thread.sleep(timeOfWaitForOneWeek);
            else
                Thread.sleep(timeOfWaitForOneWeek * 2);
            delivery.deliverToCampus();
        } catch (InterruptedException e) {
        }
    }
}
