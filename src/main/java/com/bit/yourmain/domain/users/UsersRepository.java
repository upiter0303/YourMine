package com.bit.yourmain.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findById(String id);
    Optional<Users> findByEmail(String email);
}
