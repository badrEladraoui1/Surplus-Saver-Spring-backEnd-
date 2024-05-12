package com.example.SurplusSaver__backEnd.controllers;

import com.example.SurplusSaver__backEnd.dao.entities.Interest;
import com.example.SurplusSaver__backEnd.dao.entities.Post;
import com.example.SurplusSaver__backEnd.dao.repositories.InterestRepository;
import com.example.SurplusSaver__backEnd.payload.InterestInfoDto;
import com.example.SurplusSaver__backEnd.services.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", maxAge = 3600)

@RequestMapping("/SurplusSaverApiV1/interests")

public class InterestController {

    @Autowired
    InterestService interestService;
    InterestRepository interestRepository;

    public InterestController(InterestService interestService , InterestRepository interestRepository   ) {
        this.interestService = interestService;
        this.interestRepository = interestRepository;
    }


    @PreAuthorize("hasRole('ROLE_CONSUMER')")
    @PostMapping("/expressInterest")
    public ResponseEntity<?> expressInterest(@RequestHeader("Authorization") String token, @RequestBody Interest interest) {
        // Validate the request
        if (interest == null || interest.getUserId() == null || interest.getPostId() == null) {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        }

        // Check if the user has already expressed interest in this post
        if (interestService.existsByUserIdAndPostId(interest.getUserId(), interest.getPostId())) {
            return new ResponseEntity<>("You have already expressed interest in this post", HttpStatus.BAD_REQUEST);
        }

        // Save the Interest object in the database
        Interest savedInterest = interestService.saveInterest(interest);

        // If the Interest object was not saved successfully, return an error response
        if (savedInterest == null) {
            return new ResponseEntity<>("An error occurred while expressing interest", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Return a success response to the client
        return new ResponseEntity<>("Successfully expressed interest", HttpStatus.CREATED);
    }


    //    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
//    @GetMapping("/myInterests")
//    public ResponseEntity<List<Post>> getMyInterests(@RequestHeader("Authorization") String token) {
//        List<Post> posts = interestService.getMyInterests(token);
//        return new ResponseEntity<>(posts, HttpStatus.OK);
//    }
    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @GetMapping("/myInterests")
    public ResponseEntity<List<InterestInfoDto>> getMyInterests(@RequestHeader("Authorization") String token) {
        List<InterestInfoDto> interestInfos = interestService.getMyInterests(token);
        return new ResponseEntity<>(interestInfos, HttpStatus.OK);
    }


//    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
//    @PostMapping("/{interestId}/accept")
//    public ResponseEntity<?> acceptInterest(@PathVariable Long interestId) {
//        // Call the service method to accept the interest
//        boolean isAccepted = interestService.acceptInterest(interestId);
//
//        // If the interest was successfully accepted, return a success response
//        if (isAccepted) {
//            return new ResponseEntity<>("Interest accepted successfully", HttpStatus.OK);
//        }
//
//        // If the interest was not successfully accepted because it was already accepted or cancelled, return a different error response
//        Optional<Interest> optionalInterest = interestRepository.findById(interestId);
//        if (optionalInterest.isPresent() &&
//                optionalInterest.get().getStatus() != null &&
//                (optionalInterest.get().getStatus().equals("accepted") || optionalInterest.get().getStatus().equals("cancelled"))) {
//            return new ResponseEntity<>("Interest has already been accepted or cancelled", HttpStatus.BAD_REQUEST);
//        }
//
//        // If the interest was not successfully accepted for another reason, return an error response
//        return new ResponseEntity<>("Failed to accept interest", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
//    @PostMapping("/{interestId}/cancel")
//    public ResponseEntity<?> cancelInterest(@PathVariable Long interestId) {
//        // Call the service method to cancel the interest
//        boolean isCancelled = interestService.cancelInterest(interestId);
//
//        // If the interest was successfully cancelled, return a success response
//        if (isCancelled) {
//            return new ResponseEntity<>("Interest cancelled successfully", HttpStatus.OK);
//        }
//
//        // If the interest was not successfully cancelled because it was already accepted or cancelled, return a different error response
//        Optional<Interest> optionalInterest = interestRepository.findById(interestId);
//        if (optionalInterest.isPresent() &&
//                optionalInterest.get().getStatus() != null &&
//                (optionalInterest.get().getStatus().equals("accepted") || optionalInterest.get().getStatus().equals("cancelled"))) {
//            return new ResponseEntity<>("Interest has already been accepted or cancelled", HttpStatus.BAD_REQUEST);
//        }
//
//        // If the interest was not successfully cancelled for another reason, return an error response
//        return new ResponseEntity<>("Failed to cancel interest", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PostMapping("/{interestId}/accept")
    public ResponseEntity<?> acceptInterest(@PathVariable Long interestId) {
        Optional<Interest> optionalInterest = interestRepository.findById(interestId);
        if (optionalInterest.isPresent()) {
            Interest interest = optionalInterest.get();
            if (interest.getStatus() == null) {
                interest.setStatus("pending");
                interestRepository.save(interest);
            }
            // Call the service method to accept the interest
            boolean isAccepted = interestService.acceptInterest(interestId);

            // If the interest was successfully accepted, return a success response
            if (isAccepted) {
                return new ResponseEntity<>("Interest accepted successfully", HttpStatus.OK);
            }

            // If the interest was not successfully accepted because it was already accepted or cancelled, return a different error response
            if (interest.getStatus().equals("accepted") || interest.getStatus().equals("cancelled")) {
                return new ResponseEntity<>("Interest has already been accepted or cancelled", HttpStatus.BAD_REQUEST);
            }
        }

        // If the interest was not successfully accepted for another reason, return an error response
        return new ResponseEntity<>("Failed to accept interest", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PostMapping("/{interestId}/cancel")
    public ResponseEntity<?> cancelInterest(@PathVariable Long interestId) {
        Optional<Interest> optionalInterest = interestRepository.findById(interestId);
        if (optionalInterest.isPresent()) {
            Interest interest = optionalInterest.get();
            if (interest.getStatus() == null) {
                interest.setStatus("pending");
                interestRepository.save(interest);
            }
            // Call the service method to cancel the interest
            boolean isCancelled = interestService.cancelInterest(interestId);

            // If the interest was successfully cancelled, return a success response
            if (isCancelled) {
                return new ResponseEntity<>("Interest cancelled successfully", HttpStatus.OK);
            }

            // If the interest was not successfully cancelled because it was already accepted or cancelled, return a different error response
            if (interest.getStatus().equals("accepted") || interest.getStatus().equals("cancelled")) {
                return new ResponseEntity<>("Interest has already been accepted or cancelled", HttpStatus.BAD_REQUEST);
            }
        }

        // If the interest was not successfully cancelled for another reason, return an error response
        return new ResponseEntity<>("Failed to cancel interest", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
