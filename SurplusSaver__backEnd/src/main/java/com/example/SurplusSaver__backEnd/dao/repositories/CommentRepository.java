package com.example.SurplusSaver__backEnd.dao.repositories;

import com.example.SurplusSaver__backEnd.dao.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
