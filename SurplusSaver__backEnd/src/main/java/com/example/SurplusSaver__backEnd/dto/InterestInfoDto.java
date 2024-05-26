package com.example.SurplusSaver__backEnd.dto;

import com.example.SurplusSaver__backEnd.dao.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterestInfoDto {
    private Long interestId;
    private Post post;
    private List<UserDtoInterests> users;
}
