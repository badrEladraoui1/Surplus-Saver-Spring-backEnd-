package com.example.SurplusSaver__backEnd.services.Impl;

import com.example.SurplusSaver__backEnd.dao.entities.User;
import com.example.SurplusSaver__backEnd.dao.repositories.RoleRepository;
import com.example.SurplusSaver__backEnd.dao.repositories.UserRepository;
import com.example.SurplusSaver__backEnd.security.JwtTokenProvider;
import com.example.SurplusSaver__backEnd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getUsersByRole(String token, String role) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
        return userRepository.findAllByRoleName(role);
    }


    // TODO : THIS NOT WORKING
    @Override
    public void deleteUser(String token, Long id) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));

        // Remove the user from each role
        user.getRoles().forEach(role -> {
            role.getUsers().remove(user);
            if (role.getUsers().isEmpty()) {
                // delete the association in the users_roles table
                userRepository.deleteAssociationWithRole(userId, role.getId());
                roleRepository.delete(role); // delete the role if no users are associated with it
            } else {
                roleRepository.save(role); // update the role if there are still users associated with it
            }
        });

        // Remove the roles from the user
        user.setRoles(null);
        userRepository.save(user);

        // Flush the changes to the database
        userRepository.flush();

        // Now you can delete the User
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(String token) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
    }

    @Override
    public User saveUser(String token, User user) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));

        // Check if the user is authorized to update the profile
        if (!existingUser.getId().equals(user.getId())) {
            throw new IllegalArgumentException("User is not authorized to update this profile");
        }

        return userRepository.save(user);
    }

    @Override
    public String getUserProfilePictureUrl(String token) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));

        return user.getImagePath();
    }


}
