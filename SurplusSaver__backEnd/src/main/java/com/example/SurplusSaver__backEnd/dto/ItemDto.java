package com.example.SurplusSaver__backEnd.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private String itemName;
    private String itemType;
    private String quantity;
    private String description;

    // getters and setters
}