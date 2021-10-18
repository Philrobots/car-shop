package ca.ulaval.glo4003.evulution.domain.account;

public abstract class Account {
    public String email;
    public String password;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isAuthenticationValid(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
