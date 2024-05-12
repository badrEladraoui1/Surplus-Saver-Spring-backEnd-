package com.example.SurplusSaver__backEnd.services;

import com.example.SurplusSaver__backEnd.dao.entities.Interest;
import com.example.SurplusSaver__backEnd.dao.entities.Post;
import com.example.SurplusSaver__backEnd.payload.InterestInfoDto;

import java.util.List;

public interface InterestService {

    Interest saveInterest(Interest interest);

    boolean existsByUserIdAndPostId(Long userId, Long postId);

//    List<Post> getMyInterests(String token);

    List<InterestInfoDto> getMyInterests(String token);

    boolean acceptInterest(Long interestId);

    boolean cancelInterest(Long interestId);
}
