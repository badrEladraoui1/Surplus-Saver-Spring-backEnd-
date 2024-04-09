package com.example.SurplusSaver__backEnd.security;


import com.example.SurplusSaver__backEnd.dao.entities.User;
import com.example.SurplusSaver__backEnd.dao.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));

        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        // i think i should pass a list in the third param which contains authorities and other attributes that i will need

// go back to this if i didnt work        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getAddress(), user.getPhone(), authorities);

    }
}

//    Spring Security uses the UserDetailsService interface, which contains the loadUserByUsername(String username) method to look up UserDetails for a given username.
//        The UserDetails interface represents an authenticated user object and Spring Security provides
//        an out-of-the-box implementation of org.springframework.security.core.userdetails.User.

//In summary, the CustomUserDetailsService class provides a custom implementation for loading user details during authentication. It retrieves user information from the database
// using the UserRepository, converts roles to authorities, and constructs a UserDetails object for Spring Security to use during authentication and authorization processes.
// This allows for flexible handling of user authentication within the Spring Security framework.

