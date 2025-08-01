package ru.otus.laboratory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.laboratory.dto.LoginDto;
import ru.otus.laboratory.security.CustomUserDetailsService;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final JwtEncoder jwtEncoder;

    private final PasswordEncoder passwordEncoder;

    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
        UserDetails userDetails = getUserDetails(loginDto);

        JwtClaimsSet claims = getJwtClaimsSet(userDetails);

        return new ResponseEntity<>(this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(),
                HttpStatus.OK);
    }

    private UserDetails getUserDetails(LoginDto loginDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getLogin());

        if (!passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password for %s".formatted(loginDto.getLogin()));
        }
        return userDetails;
    }

    private JwtClaimsSet getJwtClaimsSet(UserDetails userDetails) {
        Instant now = Instant.now();
        long expiry = 36000L;

        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(userDetails.getUsername())
                .claim("authorities", authorities)
                .build();
    }
}
