package com.example.SurplusSaver__backEnd.controllers;


import com.example.SurplusSaver__backEnd.dao.entities.User;
import com.example.SurplusSaver__backEnd.services.AuthService;
import com.example.SurplusSaver__backEnd.services.ImageService;
import com.example.SurplusSaver__backEnd.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/SurplusSaverApiV1/users")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    AuthService authService;
    ImageService imageService;


    public UserController(UserService userService, ImageService imageService, AuthService authService) {
        this.userService = userService;
        this.imageService = imageService;
        this.authService = authService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getUsersByRole/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@RequestHeader("Authorization") String token, @PathVariable String role) {
        List<User> response = userService.getUsersByRole(token, role);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        userService.deleteUser(token, id);
        return new ResponseEntity<>("User will be deleted in a moment !", HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String token) {
        User user = userService.getUserById(token);

        // Check if the user exists
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Return the user
        return ResponseEntity.ok(user);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUserProfilePic(@RequestHeader("Authorization") String token, @RequestParam("image") MultipartFile image) throws JsonProcessingException {
        User user = userService.getUserById(token);

        // Check if the user exists
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Save the image and get the path
        String imagePath = imageService.saveImage(image);

        // Update the image path
        user.setImagePath(imagePath); // Set the new image path

        // Save the updated user
        userService.saveUser(token, user);

        // Return the updated user and the new token
        return ResponseEntity.ok(user);
    }

}
