package com.example.SurplusSaver__backEnd.controllers;

import com.example.SurplusSaver__backEnd.dao.entities.Post;
import com.example.SurplusSaver__backEnd.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", maxAge = 3600)
//@CrossOrigin(
//        origins = "http://localhost:5173/",
//        allowedHeaders = {"Authorization", "Content-Type"},
//        exposedHeaders = {"Authorization", "Content-Type"},
//        allowCredentials = "true",
//        maxAge = 3600
//)
@RequestMapping("/SurplusSaverApiV1/posts")

public class PostController {

    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create SurplusSaver post rest api

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PostMapping("/createPost")
    public ResponseEntity<?> createPost(@RequestHeader("Authorization") String token, @RequestBody Post post) {
        String response =  postService.createPost(token, post);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @GetMapping("/viewPersonalPosts")
    public ResponseEntity<List<Post>> viewPersonalPosts(@RequestHeader("Authorization") String token) {
        List<Post> response = postService.viewPersonalPosts(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<?> deletePost(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        postService.deletePost(token, id);
        return new ResponseEntity<>("Post will be deleted in a moment !", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PutMapping("/modifyPost/{id}")
    public ResponseEntity<?> modifyPost(@RequestHeader("Authorization") String token, @RequestBody Post newPostData, @PathVariable Long id) {
        postService.modifyPost(token, newPostData, id);
        return new ResponseEntity<>("Post modified successfully!", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @GetMapping("/getPostById/{id}")
    public ResponseEntity<Post> getPostById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Post response = postService.getPostById(token, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT') || hasRole('ROLE_CONSUMER')")
    @GetMapping("/getAllPosts")
    public ResponseEntity<List<Post>> getAllPosts(@RequestHeader("Authorization") String token) {
        List<Post> response = postService.getAllPosts(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_CONSUMER')")
    @PostMapping("/savePost/{id}")
    public ResponseEntity<?> savePost(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        String response = postService.savePost(token, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_CONSUMER')")
    @GetMapping("/getSavedPosts")
    public ResponseEntity<List<Post>> getSavedPosts(@RequestHeader("Authorization") String token) {
        List<Post> response = postService.getSavedPosts(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
