package com.example.SurplusSaver__backEnd.payload;

import com.example.SurplusSaver__backEnd.dao.entities.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InterestWithItemsDto {
    private Long id;
    private Long userId;
    private Long postId;
    private String status;
    private List<Item> items;
    private String username;
    private String phone;
    // Getters and setters
}
