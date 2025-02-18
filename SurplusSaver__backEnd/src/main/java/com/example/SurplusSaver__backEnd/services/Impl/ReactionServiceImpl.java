package com.example.SurplusSaver__backEnd.services.Impl;

import com.example.SurplusSaver__backEnd.dao.entities.Post;
import com.example.SurplusSaver__backEnd.dao.entities.Reaction;
import com.example.SurplusSaver__backEnd.dao.entities.User;
import com.example.SurplusSaver__backEnd.dao.repositories.PostRepository;
import com.example.SurplusSaver__backEnd.dao.repositories.ReactionRepository;
import com.example.SurplusSaver__backEnd.dao.repositories.UserRepository;
import com.example.SurplusSaver__backEnd.dto.PostWithReactionsDto;
import com.example.SurplusSaver__backEnd.dto.ReactionDto;
import com.example.SurplusSaver__backEnd.security.JwtTokenProvider;
import com.example.SurplusSaver__backEnd.services.ReactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    private PostRepository postRepository;

    public ReactionServiceImpl(ReactionRepository reactionRepository, JwtTokenProvider jwtTokenProvider, UserRepository userRepository, PostRepository postRepository) {
        this.reactionRepository = reactionRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Reaction addReaction(String token, Long postId, Reaction reaction) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found with id : " + postId));
        reaction.setUser(user);
        reaction.setPost(post);
        reaction.setEmojiBasedOnType();
        return reactionRepository.save(reaction);
    }

    @Override
    public void deleteReaction(String token, Long reactionId) { // Added reactionId parameter
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
        Reaction reaction = reactionRepository.findById(reactionId).orElseThrow(() -> new EntityNotFoundException("Reaction not found with id : " + reactionId));
        if (!reaction.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Reaction not found with id : " + reactionId);
        }
        reactionRepository.deleteById(reactionId);
    }

    @Override
    public List<Reaction> getAllReactions(String token, Long postId) {
        return reactionRepository.findAllByPostId(postId);
    }

    public List<PostWithReactionsDto> getPostsAndReactionsByUserId(String token) {
        // Extract the user ID from the token
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);

        // Fetch the posts for the user
        List<Post> posts = postRepository.findByUserId(userId);

        // Map each Post to a PostWithReactionsDto
        List<PostWithReactionsDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            // Fetch the Reaction objects for the post
            List<Reaction> reactions = reactionRepository.findByPostId(post.getId());

            // If there are no reactions for this post, skip it
            if (reactions.isEmpty()) {
                continue;
            }

            // Map each Reaction to a ReactionDto
            List<ReactionDto> reactionDtos = reactions.stream().map(reaction -> {
                ReactionDto reactionDto = new ReactionDto();
                reactionDto.setUsername(reaction.getUser().getUsername());
                reactionDto.setType(reaction.getType());
                reactionDto.setEmoji(reaction.getEmoji());
                return reactionDto;
            }).collect(Collectors.toList());

            // Create a PostWithReactionsDto and set its fields
            PostWithReactionsDto postDto = new PostWithReactionsDto();
            postDto.setId(post.getId());
            postDto.setPostDescription(post.getPostDescription());
            postDto.setReactions(reactionDtos); // Set the reactions field with the ReactionDto objects

            // Add the PostWithReactionsDto to the list
            postDtos.add(postDto);
        }

        return postDtos;
    }


}
