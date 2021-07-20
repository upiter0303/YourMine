package com.bit.yourmine.domain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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

    @Column(nullable = false)
    private Date lastTime;

    @Column(nullable = true)
    private Long sellerOut;

    @Column(nullable = true)
    private Long buyerOut;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
    private final List<ChatDB> chatDBS = new ArrayList<>();

    @Builder
    public ChatRoom(String identify) {
        this.postId = Long.parseLong(identify.substring(0, identify.indexOf("-")));
        this.identify = identify;
        this.lastTime = new Date();
        this.sellerOut = 0L;
        this.buyerOut = 0L;
    }

    public ChatRoom update(Date date) {
        this.lastTime = date;
        return this;
    }
}
