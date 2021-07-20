package com.bit.yourmine.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReadCheckDto {
    private final String roomId;
    private final String userName;
}
