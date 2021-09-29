package ca.ulaval.glo4003.evulution.domain.customer;

import java.time.LocalDate;

public class Customer {

    // rester attentif, peut être qu'on aura besoin d'un id dans le futur plutot que d'utiliser le email comme primary
    // key
    private String name;
    private LocalDate birthDate;
    private String email;
    private String password;
    // Faire attention cest peut etre preferable de faire un interface avec differentes implementations plutot qu'un
    // field
    private Role role;
    private final Gender gender;

    public Customer(String name, LocalDate birthDate, String email, String password, Gender gender) {
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.role = Role.CUSTOMER;
        this.gender = gender;
    }

    public Customer(String name, LocalDate birthDate, String email, String password, Role role, Gender gender) {
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.role = role;
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAuthenticationValid(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
}
