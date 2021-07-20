package com.bit.yourmine.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ChatDBRepository extends JpaRepository<ChatDB, Long> {
    List<ChatDB> findAllByChatRoomNoOrderByFulTime(Long no);

    @Query("SELECT c FROM ChatDB c WHERE c.listener = :listener AND c.read is NULL")
    List<ChatDB> findAllByListener(String listener);

    @Transactional
    @Modifying
    @Query("UPDATE ChatDB c SET c.read = :no WHERE c.no = :no")
    void readCheck(Long no);
}
