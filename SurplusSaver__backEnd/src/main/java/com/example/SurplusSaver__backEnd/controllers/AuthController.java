package com.example.SurplusSaver__backEnd.controllers;

import com.example.SurplusSaver__backEnd.dto.JwtAuthResponse;
import com.example.SurplusSaver__backEnd.dto.LoginDto;
import com.example.SurplusSaver__backEnd.dto.SignUpDto;
import com.example.SurplusSaver__backEnd.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", maxAge = 3600)
@RequestMapping("/SurplusSaverApiV1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login REST API
    @PostMapping(value = {"/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }


    // Build Register REST API
    @PostMapping(value = { "/signup/{role}"})
    public ResponseEntity<String> register(@RequestBody SignUpDto registerDto, @PathVariable String role){
        String response = authService.signup(registerDto, role);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}





