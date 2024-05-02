package com.example.SurplusSaver__backEnd.config;

import com.example.SurplusSaver__backEnd.security.JwtAuthenticationEntryPoint;
import com.example.SurplusSaver__backEnd.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity


public class SecurityConfig {
    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
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

        // trying out cors configuration
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:5173/"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        http.cors(cors -> cors.configurationSource(request -> corsConfiguration))
                .csrf(csrf -> csrf.disable())
                // trying out cors configuration


//        http.csrf(csrf -> csrf.disable()) return this if the above lines does not work
                .authorizeHttpRequests((authorize) ->
                        //authorize.anyRequest().authenticated()
                        authorize.requestMatchers(HttpMethod.GET, "/SurplusSaverApiV1/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/SurplusSaverApiV1/auth/signin").permitAll()
                                .requestMatchers(HttpMethod.POST,"/SurplusSaverApiV1/auth/signup/{role}").permitAll()
                                .requestMatchers(HttpMethod.POST,"/SurplusSaverApiV1/posts/createPost").hasAuthority("ROLE_RESTAURANT")
                                .requestMatchers(HttpMethod.GET,"/SurplusSaverApiV1/posts/viewPersonalPosts").hasAuthority("ROLE_RESTAURANT")
                                .requestMatchers(HttpMethod.DELETE,"/SurplusSaverApiV1/posts/deletePost/{id}").hasAuthority("ROLE_RESTAURANT")
                                .requestMatchers(HttpMethod.PUT,"/SurplusSaverApiV1/posts/modifyPost/{id}").hasAuthority("ROLE_RESTAURANT")
                                .requestMatchers(HttpMethod.GET,"/SurplusSaverApiV1/posts/getPostById/{id}").hasAuthority("ROLE_RESTAURANT")
                                .requestMatchers(HttpMethod.GET,"/SurplusSaverApiV1/posts/getAllPosts").hasAnyAuthority("ROLE_RESTAURANT", "ROLE_CONSUMER")
                                .requestMatchers(HttpMethod.POST,"/SurplusSaverApiV1/posts/savePost/{id}").hasAuthority("ROLE_CONSUMER")
                                .requestMatchers(HttpMethod.GET,"/SurplusSaverApiV1/posts/getSavedPosts").hasAuthority("ROLE_CONSUMER")
                                .requestMatchers(HttpMethod.DELETE,"/SurplusSaverApiV1/posts/removeSavedPost/{id}").hasAuthority("ROLE_CONSUMER")
                                .anyRequest().authenticated()

                ).exceptionHandling( exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                ).sessionManagement( session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//
//        http.authorizeHttpRequests((authorize) ->
//                //authorize.anyRequest().authenticated()
//                authorize.requestMatchers("/SurplusSaverApiV1/auth/signup/{role}").permitAll()
//                        .requestMatchers("/SurplusSaverApiV1/auth/signin").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/SurplusSaverApiV1/**").permitAll()
//                        .anyRequest().authenticated()
//
//
//        ).csrf(AbstractHttpConfigurer::disable);
//
//        return http.build();
//    }
}

// In summary, the SecurityConfig class sets up security configurations for your Spring Boot application, including defining beans for password encoding,
// authentication manager, and security filter chain. It also enables method-level security and allows for custom user details service for authentication.

// We are using the Spring security provided BCryptPasswordEncoder class to encrypt the passwords.

//In Spring Security 5.6, we can enable annotation-based security using the @EnableMethodSecurity annotation on any @Configuration instance.
// @EnableMethodSecurity enables @PreAuthorize, @PostAuthorize, @PreFilter, and @PostFilter by default.
