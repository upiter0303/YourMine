package com.bit.yourmain.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

    Optional<Posts> findById(Long id);

    // Posts Searching in Title and Content
    @Query("SELECT p FROM Posts p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<Posts> findAllSearch(String keyword);
}