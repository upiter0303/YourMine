package com.bit.yourmine.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatSaveRequestDto {

    private String speaker;
    private String listener;
    private String content;
    private LocalDateTime fulTime;
    private String roomId;
}
