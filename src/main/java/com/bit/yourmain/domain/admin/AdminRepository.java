package com.bit.yourmain.domain.admin;

import com.bit.yourmain.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT u FROM Users u ORDER BY u.role, u.no")
    List<Users> findAll();

    Optional<Users> findByNo(Long no);
}
