package com.example.SurplusSaver__backEnd.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emoji;
    private String type;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    public void setEmojiBasedOnType() {
        switch (type) {
            case "thumbsUp":
                this.emoji = "\uD83D\uDC4D"; // Unicode for thumbs up emoji
                break;
            case "handHoldingHeart":
                this.emoji = "\uD83E\uDE4C"; // Unicode for hand holding heart emoji
                break;
            case "thumbsDown":
                this.emoji = "\uD83D\uDC4E"; // Unicode for thumbs down emoji
                break;
            default:
                throw new IllegalArgumentException("Invalid reaction type: " + type);
        }
    }
}