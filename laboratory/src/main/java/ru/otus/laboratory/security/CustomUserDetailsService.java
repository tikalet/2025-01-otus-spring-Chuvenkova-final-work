package ru.otus.laboratory.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.model.StaffAuth;
import ru.otus.laboratory.repository.StaffRepository;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StaffAuth staffAuth = staffRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new CustomUserDetails(staffAuth);
    }
}
