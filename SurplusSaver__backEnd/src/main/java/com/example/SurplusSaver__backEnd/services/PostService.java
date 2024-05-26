package com.example.SurplusSaver__backEnd.services;

import com.example.SurplusSaver__backEnd.dao.entities.Post;

import java.util.List;

public interface PostService {
    String createPost(String token, Post post);

    List<Post> viewPersonalPosts(String token);

    void deletePost(String token, Long postId);

    void modifyPost(String token, Post newPostData, Long postId);

    Post getPostById(String token, Long postId);

    List<Post> getAllPosts(String token);

    String savePost(String token, Long postId);

    List<Post> getSavedPosts(String token);

    String removeSavedPost(String token, Long postId);
    List<Post> searchPosts(String token, String keyword);
}
