package com.example.UserAuthService.utils;

import com.example.UserAuthService.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtTokenFilter  extends GenericFilterBean {
    private static final String BEARER = "Bearer";

    private UserDetails userDetails;

    private UserDetailsServiceImpl userDetailsService;

    public JwtTokenFilter(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Determine if there is a JWT as part of the HTTP Request Header.
     * If it is valid then set the current context With the Authentication(user and roles) found in the token
     *
     * @param req         Servlet Request
     * @param res         Servlet Response
     * @param filterChain Filter Chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("Process request to check for a JSON Web Token ");
        //Check for Authorization:Bearer JWT
        String headerValue = ((HttpServletRequest) req).getHeader("Authorization");
        getBearerToken(headerValue).ifPresent(token -> {
            //Pull the Username and Roles from the JWT to construct the user details
            userDetailsService.loadUserByJwtToken(token).ifPresent(userDetails -> {
                this.userDetails = userDetails;
                //Add the user details (Permissions) to the Context for just this API invocation
                SecurityContextHolder.getContext().setAuthentication(
                        new PreAuthenticatedAuthenticationToken(userDetails,""));
            });
        });

        //move on to the next filter in the chains
        filterChain.doFilter(req, res);
    }


    /**
     * if present, extract the jwt token from the "Bearer <jwt>" header value.
     *
     * @param headerVal
     * @return jwt if present, empty otherwise
     */
    private Optional<String> getBearerToken(String headerVal) {
        if (headerVal != null && headerVal.startsWith(BEARER)) {
            return Optional.of(headerVal.replace(BEARER, "").trim());
        }
        return Optional.empty();
    }

    public boolean hasRole(String role) {
        log.info("Checking for " + role);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities.size() == 0) {
            return false;
        }
        Set<String> roles = authorities.stream().map(GrantedAuthority::toString)
                .map(String::toString).collect(Collectors.toSet());

        return roles.contains(role);
    }
}
