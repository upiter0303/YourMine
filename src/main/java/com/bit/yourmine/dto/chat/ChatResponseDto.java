package com.bit.yourmine.dto.chat;

import com.bit.yourmine.domain.chat.ChatDB;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class ChatResponseDto implements Serializable {
    private String speaker;
    private String content;
    private LocalDateTime fulTime;

    @Builder
    public ChatResponseDto(ChatDB chatDB) {
        this.speaker = chatDB.getSpeaker();
        this.content = chatDB.getContent();
        this.fulTime = chatDB.getFulTime();
    }
}
