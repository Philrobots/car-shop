package ca.ulaval.glo4003.evulution.domain.customer;

// Peut être un probleme d'archi : pas sur que c'est une bonne idée de faire un enum au lieu d'une nouvelle classe qui part de customer...
public enum Role {
    MANAGER("Manager"), CUSTOMER("Customer");

    private final String role;

    Role(String role) {
        this.role = role;
    }

}
