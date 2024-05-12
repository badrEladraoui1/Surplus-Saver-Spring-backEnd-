package com.example.SurplusSaver__backEnd.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoInterests {
    private String name;
    private String username;
    private String email;
    private String address;
    private String phone;
    private String imagePath;
}
