package com.example.SurplusSaver__backEnd.controllers;


import com.example.SurplusSaver__backEnd.dao.entities.Role;
import com.example.SurplusSaver__backEnd.dao.entities.User;
import com.example.SurplusSaver__backEnd.dao.repositories.RoleRepository;
import com.example.SurplusSaver__backEnd.dao.repositories.UserRepository;
import com.example.SurplusSaver__backEnd.payload.LoginDto;
import com.example.SurplusSaver__backEnd.payload.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/SurplusSaverApiV1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsernameOrEmail(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("You have been signed in successfully!", HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid credentials try again", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup/{role}")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto, @PathVariable String role) {

        // Check if the username is already taken
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // Check if the email is already taken
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // Create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPhone(signUpDto.getPhone());
        user.setAddress(signUpDto.getAddress());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        // Find the role by name
        Role roles = roleRepository.findByName("ROLE_" + role.toUpperCase()).orElseThrow(() ->
                new RuntimeException("Role not found: " + role));

        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        // Send role-based registration message
        String message;
        if ("consumer".equalsIgnoreCase(role)) {
            message = "You have been registered successfully as a consumer";
        } else if ("restaurant".equalsIgnoreCase(role)) {
            message = "You have been registered successfully as a restaurant";
        } else {
            message = "You have been registered successfully";
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
//    }
//
//    @PostMapping("consumer/signup")
//    public ResponseEntity<?> registerConsumer(@RequestBody SignUpDto signUpDto) {
//
//        // add check for username exists in a DB
//        if (userRepository.existsByUsername(signUpDto.getUsername())) {
//            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
//        }
//
//        // add check for email exists in DB
//        if (userRepository.existsByEmail(signUpDto.getEmail())) {
//            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
//        }
//
//        // create user object
//        User user = new User();
//        user.setName(signUpDto.getName());
//        user.setUsername(signUpDto.getUsername());
//        user.setEmail(signUpDto.getEmail());
//        user.setPhone(signUpDto.getPhone());
//        user.setAddress(signUpDto.getAddress());
//        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
//
//        Role roles = roleRepository.findByName("ROLE_CONSUMER").get();
//        user.setRoles(Collections.singleton(roles));
//
//        userRepository.save(user);
//
//        return new ResponseEntity<>("you have been registered successfully", HttpStatus.OK);
//
//    }
//
//    @PostMapping("/restaurant/signup")
//    public ResponseEntity<?> registerRestaurant(@RequestBody SignUpDto signUpDto) {
//
//        // add check for username exists in a DB
//        if (userRepository.existsByUsername(signUpDto.getUsername())) {
//            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
//        }
//
//        // add check for email exists in DB
//        if (userRepository.existsByEmail(signUpDto.getEmail())) {
//            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
//        }
//
//        // create user object
//        User user = new User();
//        user.setName(signUpDto.getName());
//        user.setUsername(signUpDto.getUsername());
//        user.setEmail(signUpDto.getEmail());
//        user.setPhone(signUpDto.getPhone());
//        user.setAddress(signUpDto.getAddress());
//        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
//
//        Role roles = roleRepository.findByName("ROLE_RESTAURANT").get();
//        user.setRoles(Collections.singleton(roles));
//
//        userRepository.save(user);
//
//        return new ResponseEntity<>("you have been registered successfully", HttpStatus.OK);
//
//    }

    @GetMapping("/all_users")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
