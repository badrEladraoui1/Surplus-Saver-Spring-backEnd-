package com.example.SurplusSaver__backEnd.controllers;

import com.example.SurplusSaver__backEnd.dao.entities.Post;
import com.example.SurplusSaver__backEnd.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", maxAge = 3600)
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


}
