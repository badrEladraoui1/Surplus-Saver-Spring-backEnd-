package com.example.SurplusSaver__backEnd.controllers;

import com.example.SurplusSaver__backEnd.dao.entities.Post;
import com.example.SurplusSaver__backEnd.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SurplusSaverApiV1/posts")

public class PostController {

    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create SurplusSaver post rest api
    @PostMapping("/createPost")
    public ResponseEntity<?> createPost(@RequestHeader("Authorization") String token, @RequestBody Post post) {
        postService.createPost(token, post);
        return ResponseEntity.ok().build();
    }


}
