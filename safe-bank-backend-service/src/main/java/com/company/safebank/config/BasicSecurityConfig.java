package com.company.safebank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class BasicSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) ->
                requests.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated().
                        requestMatchers("/notices", "/contact", "/error").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // The "{noop}" prefix indicates that the password is stored in plain text (not recommended for production).
        UserDetails user = User.withUsername("user").password("{noop}12345").authorities("read").build();

        // Instead of plain text, you can store securely hashed passwords.
        // Use BCrypt (Spring Security’s default) to generate a password hash, e.g., via https://bcrypt-generator.com/.
        // Example below uses the hashed value of "54321".
        UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$12$3L07/jpL7x9plMEeooDeMebADTgKt.zdn9qqyffjT75Q8dv0qh.UW")
                .authorities("admin").build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    // Creates a DelegatingPasswordEncoder that uses BCrypt by default.
    // Supports multiple encodings (e.g., {bcrypt}, {noop}, {pbkdf2}, {scrypt})
    // determined by the prefix in the stored password.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}