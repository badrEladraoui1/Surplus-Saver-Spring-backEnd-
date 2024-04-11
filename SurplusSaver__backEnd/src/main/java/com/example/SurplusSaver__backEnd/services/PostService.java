package com.example.SurplusSaver__backEnd.services;

import com.example.SurplusSaver__backEnd.dao.entities.Post;
import com.example.SurplusSaver__backEnd.payload.PostDto;

import java.util.List;

public interface PostService {
 String createPost(String token, Post post);
 List<Post> viewPersonalPosts(String token);

}
