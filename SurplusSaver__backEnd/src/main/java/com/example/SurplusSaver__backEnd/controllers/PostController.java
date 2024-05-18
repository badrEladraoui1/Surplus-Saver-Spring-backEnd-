package com.example.SurplusSaver__backEnd.controllers;

import com.example.SurplusSaver__backEnd.dao.entities.Interest;
import com.example.SurplusSaver__backEnd.dao.entities.Post;
import com.example.SurplusSaver__backEnd.services.InterestService;
import com.example.SurplusSaver__backEnd.services.PostService;
import com.example.SurplusSaver__backEnd.services.ReactionService;
import com.example.SurplusSaver__backEnd.services.UserService;
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
    ReactionService reactionService;
    UserService userService;
    InterestService interestService;

    public PostController(PostService postService, ReactionService reactionService, UserService userService , InterestService interestService) {
        this.postService = postService;
        this.reactionService = reactionService;
        this.userService = userService;
        this.interestService = interestService;
    }



    // create SurplusSaver post rest api

//    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
//    @PostMapping("/createPost")
//    public ResponseEntity<?> createPost(@RequestHeader("Authorization") String token, @RequestBody Post post) {
//
//        // Create the post
//        String response = postService.createPost(token, post);
//
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
@PreAuthorize("hasRole('ROLE_RESTAURANT')")
@PostMapping("/createPost")
public ResponseEntity<?> createPost(@RequestHeader("Authorization") String token, @RequestBody Post post) {
    // Get the user's profile picture URL
    String userProfilePictureUrl = userService.getUserProfilePictureUrl(token);

    // If the user's profile picture URL is null or empty, set a default picture URL
    if (userProfilePictureUrl == null || userProfilePictureUrl.isEmpty()) {
        userProfilePictureUrl = "/images/default_restau_image.jpg";
    }

    // Set the user's profile picture URL in the post
    post.setUserProfilePictureUrl(userProfilePictureUrl);

    // Create the post
    String response = postService.createPost(token, post);

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

    @PreAuthorize("hasRole('ROLE_CONSUMER')")
    @DeleteMapping("/removeSavedPost/{id}")
    public ResponseEntity<?> removeSavedPost(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        String response = postService.removeSavedPost(token, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_CONSUMER')")
    @PostMapping("/expressInterest")
    public ResponseEntity<?> expressInterest(@RequestHeader("Authorization") String token, @RequestBody Interest interest) {
        // Validate the request
        if (interest == null || interest.getUserId() == null || interest.getPostId() == null) {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        }

        // Save the Interest object in the database
        Interest savedInterest = interestService.saveInterest(interest);

        // Return a response to the client
        return new ResponseEntity<>(savedInterest, HttpStatus.CREATED);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchWantedPosts(@RequestHeader("Authorization") String token,@RequestParam String keyword) {
        List<Post> posts = postService.searchPosts(token, keyword);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


}
