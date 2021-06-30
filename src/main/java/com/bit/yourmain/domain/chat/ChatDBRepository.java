package com.bit.yourmain.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatDBRepository extends JpaRepository<ChatDB, Long> {
    List<ChatDB> findAllByChatRoomNoOrderByCreatedDate(Long no);
}
