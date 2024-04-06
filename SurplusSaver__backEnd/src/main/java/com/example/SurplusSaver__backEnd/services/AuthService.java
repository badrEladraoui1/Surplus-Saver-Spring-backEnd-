package com.example.SurplusSaver__backEnd.services;


import com.example.SurplusSaver__backEnd.payload.LoginDto;
import com.example.SurplusSaver__backEnd.payload.SignUpDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String signup(SignUpDto signUpDto, String role);
}
