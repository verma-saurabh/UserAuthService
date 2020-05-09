package com.example.UserAuthService.service;

import com.example.UserAuthService.entity.User;
import com.example.UserAuthService.repository.UserRepository;
import com.example.UserAuthService.utils.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public JwtProvider jwtProvider;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User %s does not exist", s))

        );

        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .authorities(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "ROLE_ADMIN";
                    }
                })
                .password(user.getPassword())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    /**
     * Extract username and roles from a validated jwt string.
     *
     * @param jwtToken jwt string
     * @return UserDetails if valid, Empty otherwise
     */
    public Optional<UserDetails> loadUserByJwtToken(String jwtToken) {
        if (jwtProvider.isValidToken(jwtToken)) {
            return Optional.of(
                    withUsername(jwtProvider.getUsername(jwtToken))
                            .password("") //token does not have password but field may not be empty
                            .accountExpired(false)
                            .accountLocked(false)
                            .credentialsExpired(false)
                            .disabled(false)
                            .build());
        }
        return Optional.empty();
    }
}
