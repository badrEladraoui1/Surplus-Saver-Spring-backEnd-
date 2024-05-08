package com.example.SurplusSaver__backEnd.services.Impl;

import com.example.SurplusSaver__backEnd.dao.entities.Role;
import com.example.SurplusSaver__backEnd.dao.entities.User;
import com.example.SurplusSaver__backEnd.dao.repositories.RoleRepository;
import com.example.SurplusSaver__backEnd.dao.repositories.UserRepository;
import com.example.SurplusSaver__backEnd.exceptions.SurplusApiException;
import com.example.SurplusSaver__backEnd.payload.LoginDto;
import com.example.SurplusSaver__backEnd.payload.SignUpDto;
import com.example.SurplusSaver__backEnd.security.JwtTokenProvider;
import com.example.SurplusSaver__backEnd.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        return token;
//        return "User signedIn successfully!.";
    }

    public String signup(SignUpDto signUpDto, String role) {
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new SurplusApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new SurplusApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setAddress(signUpDto.getAddress());
        user.setPhone(signUpDto.getPhone());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_" + role.toUpperCase()).get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return role + " registered successfully!.";
    }

//    @Override
//    public String signup(SignUpDto signUpDto) {
//        // add check for username exists in database
//        if (userRepository.existsByUsername(signUpDto.getUsername())) {
//            throw new SurplusApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
//        }
//
//        // add check for email exists in database
//        if (userRepository.existsByEmail(signUpDto.getEmail())) {
//            throw new SurplusApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
//        }
//
//        User user = new User();
//        user.setName(signUpDto.getName());
//        user.setUsername(signUpDto.getUsername());
//        user.setEmail(signUpDto.getEmail());
//        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
//
//        Set<Role> roles = new HashSet<>();
//        Role userRole = roleRepository.findByName("ROLE_USER").get();
//        roles.add(userRole);
//        user.setRoles(roles);
//
//        userRepository.save(user);
//
//        return "User registered successfully!.";
//    }

    @Override
    public String generateToken(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String newToken = jwtTokenProvider.generateToken(authentication);
        return newToken;
    }

}
