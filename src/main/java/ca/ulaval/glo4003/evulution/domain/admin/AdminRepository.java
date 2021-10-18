package ca.ulaval.glo4003.evulution.domain.admin;

public interface AdminRepository {
    void addAdmin(Admin admin);

    Admin getAdminByEmail(String email);
}
