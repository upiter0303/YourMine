package com.bit.yourmain.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findByIdentify(String roomId);
    List<ChatRoom> findAllByPostId(Long id);

    @Query("SELECT c FROM ChatRoom c WHERE c.identify LIKE %:id")
    List<ChatRoom> findBuyList(String id);
}
