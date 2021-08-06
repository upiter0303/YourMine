package com.bit.yourmine.domain.posts;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p WHERE p.status = :wait ORDER BY p.id DESC")
    List<Posts> findAllDesc(Pageable pageable, String wait);

    @Query("SELECT p FROM Posts p WHERE p.status = :wait ORDER BY p.hit DESC, p.id DESC")
    List<Posts> HitDesc(Pageable pageable, String wait);

    @Query("SELECT p FROM Posts p WHERE p.category = :category AND p.status = :wait ORDER BY p.id DESC")
    List<Posts> findByCategory(String category, Pageable pageable, String wait);

    @Query("SELECT p FROM Posts p WHERE p.area LIKE :Area% AND p.status = :wait ORDER BY p.id DESC")
    List<Posts> findByAddress(String Area, Pageable pageable, String wait);

    // Posts Searching in Title and Content
    @Query("SELECT p FROM Posts p WHERE p.title LIKE %:keyword% AND p.status = :wait OR p.content LIKE %:keyword% AND p.id < :id AND p.status = :wait ORDER BY p.id DESC")
    List<Posts> findAllSearch(String keyword, Pageable pageable, String wait);

    // Posts on YourPage
    @Query("Select p FROM Posts p WHERE p.users.no = :usersNo ORDER BY p.id DESC")
    List<Posts> findAllYour(Long usersNo);

    @Transactional
    @Modifying
    @Query("UPDATE Posts p SET p.hit = p.hit+1 WHERE p.id = :id")
    void hitUpdate(Long id);

    Optional<Posts> findById(Long id);
}