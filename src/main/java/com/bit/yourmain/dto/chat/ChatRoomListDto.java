package com.bit.yourmain.dto.chat;

import com.bit.yourmain.domain.chat.ChatRoom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChatRoomListDto {
    private Long postId;
    private String identify;
    private String url;
    private String title;
    private String name;

    public ChatRoomListDto(ChatRoom chatRoom) {
        this.postId = chatRoom.getPostId();
        this.identify = chatRoom.getIdentify();
        this.url = chatRoom.getIdentify().replace("-", "/");
    }
}
