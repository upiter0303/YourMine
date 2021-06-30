package com.bit.yourmain.domain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "ChatRoom_seq_gen",
        sequenceName = "chatRoom_pk",
        initialValue = 1,
        allocationSize = 1
)
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "chatRoom_seq_gen")
    private Long no;

    @Column
    private Long postId;

    @Column(nullable = false)
    private String identify;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
    private final List<ChatDB> chatDBS = new ArrayList<>();

    @Builder
    public ChatRoom(String identify) {
        this.postId = Long.parseLong(identify.substring(0, identify.indexOf("-")));
        this.identify = identify;
    }
}
