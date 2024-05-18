package com.example.SurplusSaver__backEnd.services.Impl;

import com.example.SurplusSaver__backEnd.dao.entities.Item;
import com.example.SurplusSaver__backEnd.dao.entities.Post;
import com.example.SurplusSaver__backEnd.dao.entities.User;
import com.example.SurplusSaver__backEnd.dao.repositories.PostRepository;
import com.example.SurplusSaver__backEnd.dao.repositories.UserRepository;
import com.example.SurplusSaver__backEnd.security.JwtTokenProvider;
import com.example.SurplusSaver__backEnd.services.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Post> viewPersonalPosts(String token) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
        return postRepository.findByUser(user);
    }

    @Override
    public void deletePost(String token, Long postId) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found with id : " + postId));
        if (!post.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Post not found with id : " + postId);
        }
        postRepository.deleteById(postId);
    }

    @Override
    public void modifyPost(String token, Post newPostData, Long postId) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
        Post existingPost = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found with id : " + postId));
        if (!existingPost.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Post not found with id : " + postId);
        }

        // Update the post data
        existingPost.setRestaurantName(newPostData.getRestaurantName());
        existingPost.setPostDescription(newPostData.getPostDescription());

        // Get the existing items
        List<Item> existingItems = existingPost.getItems();

        // Clear the collection
        existingItems.clear();

        // Add the new items
        existingItems.addAll(newPostData.getItems());

        // Save the updated post
        postRepository.save(existingPost);
    }

//    @Override
//    public void modifyPost(String token, Post newPostData, Long postId) {
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
//        Post existingPost = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found with id : " + postId));
//        if (!existingPost.getUser().getId().equals(user.getId())) {
//            throw new EntityNotFoundException("Post not found with id : " + postId);
//        }
//
//        // Update the post data
//        existingPost.setRestaurantName(newPostData.getRestaurantName());
//        existingPost.setPostDescription(newPostData.getPostDescription());
//        existingPost.setItems(newPostData.getItems());
//
//        // Save the updated post
//        postRepository.save(existingPost);
//    }

    @Override
    public Post getPostById(String token, Long postId) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found with id : " + postId));
        if (!post.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Post not found with id : " + postId);
        }
        return post;
    }

    @Override
    public List<Post> getAllPosts(String token) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
        List<Post> posts = postRepository.findAllBy();

        posts.forEach(post -> {
            if (post.getUserProfilePictureUrl() == null || post.getUserProfilePictureUrl().isEmpty()) {
                post.setUserProfilePictureUrl("images/default_restau_image.jpg");
            }
        });

        return posts;
    }
//    @Override
//    public List<Post> getAllPosts(String token) {
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
//        return postRepository.findAllBy();
//    }

    public String savePost(String token, Long postId) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found with id : " + postId));

        if (user.getSavedPosts().contains(post)) {
            return "Post is already saved!";
        } else {
            user.getSavedPosts().add(post);
            userRepository.save(user);
            return "Post saved successfully!";
        }
    }

    @Override
    public List<Post> getSavedPosts(String token) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
        return user.getSavedPosts();
    }

    @Override
    public String removeSavedPost(String token, Long postId) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found with id : " + postId));

        if (user.getSavedPosts().contains(post)) {
            user.getSavedPosts().remove(post);
            userRepository.save(user);
            return "Post removed from saved posts!";
        } else {
            return "Post is not saved!";
        }
    }

    public List<Post> searchPosts(String token, String keyword) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
        return postRepository.searchByKeyword(keyword);
    }


}
