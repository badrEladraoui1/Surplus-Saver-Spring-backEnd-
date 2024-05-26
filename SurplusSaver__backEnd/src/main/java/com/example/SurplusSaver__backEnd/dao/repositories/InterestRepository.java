package com.example.SurplusSaver__backEnd.dao.repositories;

import com.example.SurplusSaver__backEnd.dao.entities.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    List<Interest> findByUserId(Long userId);

    List<Interest> findByPostIdIn(List<Long> postIds);

    List<Interest> findByUserIdAndStatus(Long userId, String status); // Get interests by user ID and status

    List<Interest> findByPostIdInAndStatus(List<Long> postIds, String pending);
}
