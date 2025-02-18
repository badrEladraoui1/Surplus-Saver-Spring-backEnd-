package com.example.SurplusSaver__backEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private String restaurantName;
    private String postDescription;
    private List<ItemDto> items;

}