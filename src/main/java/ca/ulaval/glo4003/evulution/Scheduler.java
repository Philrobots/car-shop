package ca.ulaval.glo4003.evulution;

import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionLine;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoicePayment;
import ca.ulaval.glo4003.evulution.domain.production.exceptions.CarNotAssociatedWithManufactureException;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;

import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {
    private final int timeToWaitForOneWeek;
    private final ProductionLine productionLine;
    private final InvoicePayment invoicePayment;
    private final Timer timer = new Timer();

    public Scheduler(int equivalenceOfOneWeekInSeconds, ProductionLine productionLine, InvoicePayment invoicePayment) {
        this.timeToWaitForOneWeek = equivalenceOfOneWeekInSeconds * 1000;
        this.productionLine = productionLine;
        this.invoicePayment = invoicePayment;
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    productionLine.advanceAssemblyLines();
                    invoicePayment.makePayments();
                    System.out.println(" ------------- ");
                } catch (DeliveryIncompleteException | SaleNotFoundException | AccountNotFoundException
                        | CarNotAssociatedWithManufactureException e) {
                    e.printStackTrace();
                }
            }
        }, 0, timeToWaitForOneWeek);
    }
}
