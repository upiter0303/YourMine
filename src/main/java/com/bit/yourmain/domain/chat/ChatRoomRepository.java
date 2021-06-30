package com.bit.yourmain.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findByIdentify(String roomId);
    List<ChatRoom> findAllByPostId(Long id);
}
