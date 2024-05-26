package com.example.SurplusSaver__backEnd.controllers;

import com.example.SurplusSaver__backEnd.dao.entities.Reaction;
import com.example.SurplusSaver__backEnd.dto.PostWithReactionsDto;
import com.example.SurplusSaver__backEnd.services.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/SurplusSaverApiV1/posts")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @PostMapping("/{postId}/reactions")
    public ResponseEntity<Reaction> addReaction(@RequestHeader("Authorization") String token, @PathVariable Long postId, @RequestBody Map<String, String> body) {
        String type = body.get("reaction");
        Reaction reaction = new Reaction();
        reaction.setType(type);
        Reaction savedReaction = reactionService.addReaction(token, postId, reaction);
        return new ResponseEntity<>(savedReaction, HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}/reactions/{reactionId}")
    public ResponseEntity<Void> deleteReaction(@RequestHeader("Authorization") String token, @PathVariable Long reactionId) {
        reactionService.deleteReaction(token, reactionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{postId}/reactions")
    public ResponseEntity<List<Reaction>> getAllReactions(@RequestHeader("Authorization") String token, @PathVariable Long postId) {
        List<Reaction> reactions = reactionService.getAllReactions(token, postId);
        return new ResponseEntity<>(reactions, HttpStatus.OK);
    }

    @GetMapping("/reactions")
    public ResponseEntity<List<PostWithReactionsDto>> getPostsAndReactionsByUserId(@RequestHeader("Authorization") String token) {
        List<PostWithReactionsDto> posts = reactionService.getPostsAndReactionsByUserId(token);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}

//package com.example.SurplusSaver__backEnd.controllers;
//
//import com.example.SurplusSaver__backEnd.dao.entities.Reaction;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/reactions")
//public class ReactionController {
//
//    @Autowired
//    private WebSocketController webSocketController;
//
//    @PostMapping
//    public Reaction addReaction(@RequestBody Reaction reaction) {
//        // Add reaction
//        webSocketController.updateLikes(reaction);
//        return reaction;
//    }
//
//    @DeleteMapping("/{id}")
//    public void removeReaction(@PathVariable String id) {
//        // Remove reaction
//        webSocketController.updateLikes(null);
//    }
//}