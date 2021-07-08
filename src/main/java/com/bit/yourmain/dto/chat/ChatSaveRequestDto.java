package com.bit.yourmain.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatSaveRequestDto {

    private String speaker;
    private String listener;
    private String content;
    private String sendTime;
    private String roomId;
}
