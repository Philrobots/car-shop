package ca.ulaval.glo4003.evulution.infrastructure.admin;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.admin.Admin;
import ca.ulaval.glo4003.evulution.domain.admin.AdminRepository;

import java.util.HashMap;
import java.util.Map;

public class AdminRepositoryInMemory implements AdminRepository {
    private Map<String, Admin> admins= new HashMap<>();
    @Override
    public void addAdmin(Admin admin) {
        admins.put(admin.getEmail(), admin);
    }

    @Override
    public Admin getAdminByEmail(String email) {
        return admins.get(email);
    }
}
