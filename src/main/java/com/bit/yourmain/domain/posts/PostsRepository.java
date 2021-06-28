package com.bit.yourmain.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

    Optional<Posts> findById(Long id);

    @Query("SELECT p FROM Posts p ORDER BY p.hit DESC")
    List<Posts> HitDesc();

    List<Posts> findByCategoryOrderByIdDesc(String category);

    @Query("SELECT p FROM Posts p WHERE p.area LIKE :Area% ORDER BY p.id")
    List<Posts> findByAddress(String Area);

    // Posts Searching in Title and Content
    @Query("SELECT p FROM Posts p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<Posts> findAllSearch(String keyword);

    @Transactional
    @Modifying
    @Query("UPDATE Posts p SET p.hit = p.hit+1 WHERE p.id = :id")
    void hitUpdate(Long id);
}