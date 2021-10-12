package ca.ulaval.glo4003.evulution.domain.assemblyline;

public enum AssemblyStatus {
    RECEIVED("RECEIVED"),
    IN_PROGRESS("IN_PROGRESS"),
    ASSEMBLED("ASSEMBLED");

    private String status;

    private AssemblyStatus(String status) {
        this.status = status;
    }
}
