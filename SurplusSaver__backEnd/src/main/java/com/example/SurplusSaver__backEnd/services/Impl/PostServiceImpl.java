package com.example.SurplusSaver__backEnd.services.Impl;

import com.example.SurplusSaver__backEnd.dao.entities.Post;
import com.example.SurplusSaver__backEnd.dao.entities.User;
import com.example.SurplusSaver__backEnd.dao.repositories.PostRepository;
import com.example.SurplusSaver__backEnd.dao.repositories.UserRepository;
import com.example.SurplusSaver__backEnd.payload.PostDto;
import com.example.SurplusSaver__backEnd.security.JwtTokenProvider;
import com.example.SurplusSaver__backEnd.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String createPost(String token, Post post) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
        post.setUser(user);
        postRepository.save(post);
        return "Post created successfully!.";
    }
}
