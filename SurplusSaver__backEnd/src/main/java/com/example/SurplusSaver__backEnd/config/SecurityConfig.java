package com.example.SurplusSaver__backEnd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity

public class SecurityConfig {
    private UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest()
//                .permitAll())
//                .csrf(AbstractHttpConfigurer::disable);
//        return http.build();
//    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests((authorize) ->
                //authorize.anyRequest().authenticated()
                authorize.requestMatchers("/SurplusSaverApiV1/auth/signup/{role}").permitAll()
                        .requestMatchers("/SurplusSaverApiV1/auth/signin").permitAll()
                        .requestMatchers(HttpMethod.GET, "/SurplusSaverApiV1/**").permitAll()
                        .anyRequest().authenticated()


        ).csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}

// In summary, the SecurityConfig class sets up security configurations for your Spring Boot application, including defining beans for password encoding,
// authentication manager, and security filter chain. It also enables method-level security and allows for custom user details service for authentication.

// We are using the Spring security provided BCryptPasswordEncoder class to encrypt the passwords.

//In Spring Security 5.6, we can enable annotation-based security using the @EnableMethodSecurity annotation on any @Configuration instance.
// @EnableMethodSecurity enables @PreAuthorize, @PostAuthorize, @PreFilter, and @PostFilter by default.
