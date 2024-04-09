package com.example.SurplusSaver__backEnd.dao.repositories;

import com.example.SurplusSaver__backEnd.dao.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
