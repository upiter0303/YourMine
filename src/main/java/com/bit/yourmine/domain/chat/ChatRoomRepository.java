package com.bit.yourmine.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByIdentify(String roomId);
    List<ChatRoom> findAllByPostId(Long id);

    @Query("SELECT c FROM ChatRoom c WHERE c.identify LIKE %:id")
    List<ChatRoom> findBuyList(String id);

    @Transactional
    @Modifying
    @Query("UPDATE ChatRoom c SET c.buyerOut = 1 WHERE c.identify = :identify")
    void buyerOut(String identify);

    @Transactional
    @Modifying
    @Query("UPDATE ChatRoom c SET c.sellerOut = 1 WHERE c.identify = :identify")
    void sellerOut(String identify);

    @Transactional
    @Modifying
    @Query("UPDATE ChatRoom c SET c.sellerOut = 0, c.buyerOut = 0 WHERE c.identify = :identify")
    void chatIn(String identify);
}
