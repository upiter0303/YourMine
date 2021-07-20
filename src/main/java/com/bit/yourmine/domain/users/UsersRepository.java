package com.bit.yourmine.domain.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findById(String id);
    Optional<Users> findByEmail(String email);
    Optional<Users> findByName(String name);

    @Query("SELECT u FROM Users u ORDER BY u.role, u.no")
    Page<Users> findAll(Pageable pageable);
    Optional<Users> findByNo(Long no);
}
