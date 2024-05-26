package com.example.SurplusSaver__backEnd.services;


import com.example.SurplusSaver__backEnd.dao.entities.User;
import com.example.SurplusSaver__backEnd.dto.LoginDto;
import com.example.SurplusSaver__backEnd.dto.SignUpDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String signup(SignUpDto signUpDto, String role);

    String generateToken(User user);
}
