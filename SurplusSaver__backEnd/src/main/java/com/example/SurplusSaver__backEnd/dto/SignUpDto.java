package com.example.SurplusSaver__backEnd.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String name;
    private String username;
    private String email;
    private String password;
    private String address;
    private String phone;
}