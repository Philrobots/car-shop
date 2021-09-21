package ca.ulaval.glo4003.evulution.api.customer.dto;

import jakarta.validation.constraints.NotNull;

public class CustomerDto {
    @NotNull
    public String name;

    @NotNull
    public String birthdate;

    @NotNull
    public String email;

    @NotNull
    public String password;
}
