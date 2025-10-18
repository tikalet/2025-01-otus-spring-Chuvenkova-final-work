package ru.otus.laboratory.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.otus.laboratory.model.StaffAuth;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private StaffAuth staff;

    public CustomUserDetails(StaffAuth staff) {
        this.staff = staff;
    }

    @Override
    public String getPassword() {
        return staff.getPassword();
    }

    @Override
    public String getUsername() {
        return staff.getLogin();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return staff.getAuthorityList();
    }

    public Long getStaffId() {
        return staff.getStaffId();
    }
}
