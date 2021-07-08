package com.bit.yourmain.domain.chat;

import com.bit.yourmain.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "ChatDB_seq_gen",
        sequenceName = "chatDB_pk",
        initialValue = 1,
        allocationSize = 1
)
public class ChatDB extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "ChatDB_seq_gen")
    private Long no;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String speaker;

    @Column(nullable = false)
    private String listener;

    @Column(nullable = false)
    private String sendTime;

    @Column(nullable = true)
    private Long read;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ChatRoom_no")
    private ChatRoom chatRoom;

    @Builder
    public ChatDB(String content, String speaker, String listener, String sendTime, ChatRoom chatRoom) {
        this.content = content;
        this.speaker = speaker;
        this.listener = listener;
        this.sendTime = sendTime;
        this.chatRoom = chatRoom;
    }
}
