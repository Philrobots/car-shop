package ca.ulaval.glo4003.evulution;

import ca.ulaval.glo4003.evulution.service.assemblyLine.AssemblyLineService;
import ca.ulaval.glo4003.evulution.service.invoice.InvoiceService;

import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {
    private final int timeToWaitForOneWeek;
    private final AssemblyLineService assemblyLineService;
    private final InvoiceService invoiceService;
    private final Timer timer = new Timer();

    public Scheduler(int equivalenceOfOneWeekInSeconds, AssemblyLineService assemblyLineService,
            InvoiceService invoiceService) {
        this.timeToWaitForOneWeek = equivalenceOfOneWeekInSeconds * 1000;
        this.assemblyLineService = assemblyLineService;
        this.invoiceService = invoiceService;
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                assemblyLineService.advanceAssemblyLines();
                invoiceService.makePayments();
            }
        }, 0, timeToWaitForOneWeek);
    }
}
