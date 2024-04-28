package com.example.SurplusSaver__backEnd.services;

import com.example.SurplusSaver__backEnd.dao.entities.Post;
import com.example.SurplusSaver__backEnd.payload.PostDto;

import java.util.List;

public interface PostService {
 String createPost(String token, Post post);
 List<Post> viewPersonalPosts(String token);
 void deletePost(String token , Long postId);
 void modifyPost(String token, Post newPostData, Long postId);
 Post getPostById(String token , Long postId);
 List<Post> getAllPosts(String token);

}
