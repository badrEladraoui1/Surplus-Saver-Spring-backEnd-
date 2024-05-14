package com.example.SurplusSaver__backEnd.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReactionDto {
    private String username;
    private String emoji;
    private String type;
}
