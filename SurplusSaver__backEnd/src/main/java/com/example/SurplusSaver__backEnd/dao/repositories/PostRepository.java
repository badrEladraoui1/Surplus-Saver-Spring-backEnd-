package com.example.SurplusSaver__backEnd.dao.repositories;

import com.example.SurplusSaver__backEnd.dao.entities.Post;
import com.example.SurplusSaver__backEnd.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
    List<Post> findAllBy();
    List<Post> findByUserId(Long userId);
    @Query("SELECT p FROM Post p JOIN p.items i WHERE p.restaurantName LIKE %:keyword% OR p.postDescription LIKE %:keyword% OR i.itemName LIKE %:keyword%")
    List<Post> searchByKeyword(@Param("keyword") String keyword);
}
