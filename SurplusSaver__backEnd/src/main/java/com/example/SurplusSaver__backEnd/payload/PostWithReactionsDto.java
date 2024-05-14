package com.example.SurplusSaver__backEnd.payload;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PostWithReactionsDto {
    private Long id;
    private String title;
    private String postDescription;
    private List<ReactionDto> reactions;
}