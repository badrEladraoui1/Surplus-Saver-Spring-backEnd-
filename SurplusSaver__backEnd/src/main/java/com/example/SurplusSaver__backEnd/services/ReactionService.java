package com.example.SurplusSaver__backEnd.services;

import com.example.SurplusSaver__backEnd.dao.entities.Reaction;

import java.util.List;

public interface ReactionService {
    Reaction addReaction(String token, Long postId, Reaction reaction);
    void deleteReaction(String token, Long reactionId);
    List<Reaction> getAllReactions(String token, Long postId);
}
