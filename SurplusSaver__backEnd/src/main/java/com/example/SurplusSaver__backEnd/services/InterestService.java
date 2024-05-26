package com.example.SurplusSaver__backEnd.services;

import com.example.SurplusSaver__backEnd.dao.entities.Interest;
import com.example.SurplusSaver__backEnd.dto.InterestInfoDto;
import com.example.SurplusSaver__backEnd.dto.InterestWithItemsDto;

import java.util.List;

public interface InterestService {

    Interest saveInterest(Interest interest);

    boolean existsByUserIdAndPostId(Long userId, Long postId);

//    List<Post> getMyInterests(String token);

    List<InterestInfoDto> getMyInterests(String token);

    boolean acceptInterest(Long interestId);

    boolean cancelInterest(Long interestId);

    List<InterestWithItemsDto> getInterestsAndItemsByUserId(String token);

    List<InterestWithItemsDto> getAcceptedInterestsByUserId(String token);
    List<InterestWithItemsDto> getPendingInterestsByUserId(String token);
    public List<InterestWithItemsDto> getCancelledInterestsByUserId(String token);
}
