package com.bit.yourmain.dto.chat;

import com.bit.yourmain.domain.chat.ChatDB;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ChatResponseDto implements Serializable {
    private String speaker;
    private String content;

    @Builder
    public ChatResponseDto(ChatDB chatDB) {
        this.speaker = chatDB.getSpeaker();
        this.content = chatDB.getContent();
    }
}
