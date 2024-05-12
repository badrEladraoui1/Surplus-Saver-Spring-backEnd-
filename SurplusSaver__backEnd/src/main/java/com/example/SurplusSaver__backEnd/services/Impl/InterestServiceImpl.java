package com.example.SurplusSaver__backEnd.services.Impl;

import com.example.SurplusSaver__backEnd.dao.entities.Interest;
import com.example.SurplusSaver__backEnd.dao.entities.Post;
import com.example.SurplusSaver__backEnd.dao.entities.User;
import com.example.SurplusSaver__backEnd.dao.repositories.InterestRepository;
import com.example.SurplusSaver__backEnd.dao.repositories.PostRepository;
import com.example.SurplusSaver__backEnd.dao.repositories.UserRepository;
import com.example.SurplusSaver__backEnd.payload.InterestInfoDto;
import com.example.SurplusSaver__backEnd.payload.UserDtoInterests;
import com.example.SurplusSaver__backEnd.security.JwtTokenProvider;
import com.example.SurplusSaver__backEnd.services.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InterestServiceImpl implements InterestService {

    @Autowired
    InterestRepository interestRepository;
    JwtTokenProvider jwtTokenProvider;
    PostRepository postRepository;
    UserRepository userRepository;

    public InterestServiceImpl(InterestRepository interestRepository, JwtTokenProvider jwtTokenProvider, PostRepository postRepository, UserRepository userRepository) {
        this.interestRepository = interestRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Interest saveInterest(Interest interest) {
        return interestRepository.save(interest);
    }

    @Override
    public boolean existsByUserIdAndPostId(Long userId, Long postId) {
        return interestRepository.existsByUserIdAndPostId(userId, postId);
    }

//    @Override
//    public List<Post> getMyInterests(String token) {
//        // Extract the user ID from the token
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//
//        // Fetch all the Post objects for the user
//        List<Post> userPosts = postRepository.findByUserId(userId);
//
//        // Fetch all the Interest objects
//        List<Interest> interests = interestRepository.findAll();
//
//        // Filter the interests to only include those that match the user's posts
//        List<Post> posts = new ArrayList<>();
//        for (Post userPost : userPosts) {
//            for (Interest interest : interests) {
//                if (interest.getPostId().equals(userPost.getId())) {
//                    posts.add(userPost);
//                    break;
//                }
//            }
//        }
//
//        // Return the Post objects
//        return posts;
//    }


//    @Override
//    public List<Post> getMyInterests(String token) {
//        // Extract the user ID from the token
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//
//        // Fetch all the Post objects for the user
//        List<Post> userPosts = postRepository.findByUserId(userId);
//
//        // Get the IDs of the user's posts
//        List<Long> postIds = userPosts.stream().map(Post::getId).collect(Collectors.toList());
//
//        // Fetch the Interest objects that match the user's posts
//        List<Interest> interests = interestRepository.findByPostIdIn(postIds);
//
//        // Filter the user's posts to only include those that have a matching interest
//        List<Post> posts = userPosts.stream()
//                .filter(post -> interests.stream().anyMatch(interest -> interest.getPostId().equals(post.getId())))
//                .collect(Collectors.toList());
//
//        // Return the Post objects
//        return posts;
//    }

//    @Override
//    public List<InterestInfoDto> getMyInterests(String token) {
//        // Extract the user ID from the token
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//
//        // Fetch all the Post objects for the user
//        List<Post> userPosts = postRepository.findByUserId(userId);
//
//        // Get the IDs of the user's posts
//        List<Long> postIds = userPosts.stream().map(Post::getId).collect(Collectors.toList());
//
//        // Fetch the Interest objects that match the user's posts
//        List<Interest> interests = interestRepository.findByPostIdIn(postIds);
//
//        // Create a list of InterestInfo objects
//        List<InterestInfoDto> interestInfos = new ArrayList<>();
//        for (Post post : userPosts) {
//            for (Interest interest : interests) {
//                if (interest.getPostId().equals(post.getId())) {
//                    // Fetch the User who is interested in the post
//                    User user = userRepository.findById(interest.getUserId()).orElse(null);
//                    if (user != null) {
//                        // Add the InterestInfo object to the list
//                        interestInfos.add(new InterestInfoDto(post, user));
//                    }
//                    break;
//                }
//            }
//        }
//
//        // Return the list of InterestInfo objects
//        return interestInfos;
//    }

//    @Override
//    public List<InterestInfoDto> getMyInterests(String token) {
//        // Extract the user ID from the token
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//
//        // Fetch all the Post objects for the user
//        List<Post> userPosts = postRepository.findByUserId(userId);
//
//        // Get the IDs of the user's posts
//        List<Long> postIds = userPosts.stream().map(Post::getId).collect(Collectors.toList());
//
//        // Fetch the Interest objects that match the user's posts
//        List<Interest> interests = interestRepository.findByPostIdIn(postIds);
//
//        // Create a list of InterestInfo objects
//        List<InterestInfoDto> interestInfos = new ArrayList<>();
//        for (Post post : userPosts) {
//            for (Interest interest : interests) {
//                if (interest.getPostId().equals(post.getId())) {
//                    // Fetch the User who is interested in the post
//                    Optional<User> optionalUser = userRepository.findById(interest.getUserId());
//                    if (optionalUser.isPresent()) {
//                        User user = optionalUser.get();
//                        // Create a UserDto object with only the necessary fields
//                        UserDtoInterests userDto = new UserDtoInterests(user.getName(), user.getUsername(), user.getEmail(), user.getAddress(), user.getPhone(), user.getImagePath());
//                        // Add the InterestInfo object to the list
//                        interestInfos.add(new InterestInfoDto(post, userDto));
//                    }
//                    break;
//                }
//            }
//        }
//
//        return interestInfos;
//    }

//    @Override
//    public List<InterestInfoDto> getMyInterests(String token) {
//        // Extract the user ID from the token
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//
//        // Fetch all the Post objects for the user
//        List<Post> userPosts = postRepository.findByUserId(userId);
//
//        // Get the IDs of the user's posts
//        List<Long> postIds = userPosts.stream().map(Post::getId).collect(Collectors.toList());
//
//        // Fetch the Interest objects that match the user's posts
//        List<Interest> interests = interestRepository.findByPostIdIn(postIds);
//
//        // Create a list of InterestInfo objects
//        List<InterestInfoDto> interestInfos = new ArrayList<>();
//        for (Post post : userPosts) {
//            List<UserDtoInterests> users = new ArrayList<>();
//            for (Interest interest : interests) {
//                if (interest.getPostId().equals(post.getId())) {
//                    // Fetch the User who is interested in the post
//                    Optional<User> optionalUser = userRepository.findById(interest.getUserId());
//                    if (optionalUser.isPresent()) {
//                        User user = optionalUser.get();
//                        // Create a UserDto object with only the necessary fields
//                        UserDtoInterests userDto = new UserDtoInterests(user.getName(), user.getUsername(), user.getEmail(), user.getAddress(), user.getPhone(), user.getImagePath());
//                        // Add the UserDto object to the users list
//                        users.add(userDto);
//                    }
//                }
//            }
//            // Add the InterestInfo object to the list only if the users list is not empty
//            if (!users.isEmpty()) {
//                interestInfos.add(new InterestInfoDto(post, users));
//            }
//        }
//
//        return interestInfos;
//    }

    //    @Override
//    public List<InterestInfoDto> getMyInterests(String token) {
//        // Extract the user ID from the token
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//
//        // Fetch all the Post objects for the user
//        List<Post> userPosts = postRepository.findByUserId(userId);
//
//        // Get the IDs of the user's posts
//        List<Long> postIds = userPosts.stream().map(Post::getId).collect(Collectors.toList());
//
//        // Fetch the Interest objects that match the user's posts
//        List<Interest> interests = interestRepository.findByPostIdIn(postIds);
//
//        // Create a list of InterestInfo objects
//        List<InterestInfoDto> interestInfos = new ArrayList<>();
//        for (Post post : userPosts) {
//            List<UserDtoInterests> users = new ArrayList<>();
//            for (Interest interest : interests) {
//                if (interest.getPostId().equals(post.getId())) {
//                    // Fetch the User who is interested in the post
//                    Optional<User> optionalUser = userRepository.findById(interest.getUserId());
//                    if (optionalUser.isPresent()) {
//                        User user = optionalUser.get();
//                        // Create a UserDto object with only the necessary fields
//                        UserDtoInterests userDto = new UserDtoInterests(user.getName(), user.getUsername(), user.getEmail(), user.getAddress(), user.getPhone(), user.getImagePath());
//                        // Add the UserDto object to the users list
//                        users.add(userDto);
//
//                        // Add the InterestInfo object to the list only if the users list is not empty
//                        if (!users.isEmpty()) {
//                            InterestInfoDto interestInfoDto = new InterestInfoDto();
//                            interestInfoDto.setInterestId(interest.getId()); // Set the interestId field
//                            interestInfoDto.setPost(post);
//                            interestInfoDto.setUsers(users);
//                            interestInfos.add(interestInfoDto);
//                        }
//                    }
//                }
//            }
//        }
//
//        return interestInfos;
//    }
    @Override
    public List<InterestInfoDto> getMyInterests(String token) {
        // Extract the user ID from the token
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);

        // Fetch all the Post objects for the user
        List<Post> userPosts = postRepository.findByUserId(userId);

        // Get the IDs of the user's posts
        List<Long> postIds = userPosts.stream().map(Post::getId).collect(Collectors.toList());

        // Fetch the Interest objects that match the user's posts
        List<Interest> interests = interestRepository.findByPostIdIn(postIds);

        // Create a list of InterestInfo objects
        List<InterestInfoDto> interestInfos = new ArrayList<>();
        for (Post post : userPosts) {
            List<UserDtoInterests> users = new ArrayList<>();
            Interest currentInterest = null; // Define the interest variable here
            for (Interest interest : interests) {
                if (interest.getPostId().equals(post.getId())) {
                    currentInterest = interest; // Update the currentInterest variable here
                    // Fetch the User who is interested in the post
                    Optional<User> optionalUser = userRepository.findById(interest.getUserId());
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        // Create a UserDto object with only the necessary fields
                        UserDtoInterests userDto = new UserDtoInterests(user.getName(), user.getUsername(), user.getEmail(), user.getAddress(), user.getPhone(), user.getImagePath());
                        // Add the UserDto object to the users list
                        users.add(userDto);
                    }
                }
            }
            // Add the InterestInfo object to the list only if the users list is not empty
            if (!users.isEmpty() && currentInterest != null) { // Check if currentInterest is not null
                InterestInfoDto interestInfoDto = new InterestInfoDto();
                interestInfoDto.setInterestId(currentInterest.getId()); // Set the interestId field with currentInterest.getId()
                interestInfoDto.setPost(post);
                interestInfoDto.setUsers(users);
                interestInfos.add(interestInfoDto);
            }
        }

        return interestInfos;
    }

    @Override
    public boolean acceptInterest(Long interestId) {
        // Fetch the Interest object with the given ID
        Optional<Interest> optionalInterest = interestRepository.findById(interestId);

        // If the Interest object exists, update its status to "accepted" and save it
        if (optionalInterest.isPresent()) {
            Interest interest = optionalInterest.get();
            if (interest.getStatus().equals("accepted") || interest.getStatus().equals("cancelled")) {
                // If the interest is already accepted or cancelled, return false
                return false;
            }
            // Otherwise, update its status to "accepted" and save it
            interest.setStatus("accepted");
            interestRepository.save(interest);
            return true;
        }

        // If the Interest object does not exist, return false
        return false;
    }

    @Override
    public boolean cancelInterest(Long interestId) {
        // Fetch the Interest object with the given ID
        Optional<Interest> optionalInterest = interestRepository.findById(interestId);


        // If the Interest object exists, check its status
        if (optionalInterest.isPresent()) {
            Interest interest = optionalInterest.get();
            if (interest.getStatus().equals("accepted") || interest.getStatus().equals("cancelled")) {
                // If the interest is already accepted or cancelled, return false
                return false;
            }
            // Otherwise, update its status to "cancelled" and save it
            interest.setStatus("cancelled");
            interestRepository.save(interest);
            return true;
        }

        // If the Interest object does not exist, return false
        return false;
    }

}
