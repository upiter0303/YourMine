package com.bit.yourmine.domain.files;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilesRepository extends JpaRepository<Files, Long> {
    List<Files> findAllByPostsId(Long id);
    Files findByFileName(String name);
}
