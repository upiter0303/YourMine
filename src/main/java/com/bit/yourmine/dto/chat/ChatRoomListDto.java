package com.bit.yourmine.dto.chat;

import com.bit.yourmine.domain.chat.ChatDB;
import com.bit.yourmine.domain.chat.ChatRoom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ChatRoomListDto {
    private Long postId;
    private String identify;
    private String url1;
    private String url2;
    private String title;
    private String name;
    private String profile;
    private String lastChat;
    private Date lastTime;
    private Long newChatCount;

    public ChatRoomListDto(ChatRoom chatRoom) {
        this.postId = chatRoom.getPostId();
        this.identify = chatRoom.getIdentify();
        String url = chatRoom.getIdentify();
        this.url1 = url.substring(0, url.indexOf("-"));
        this.url2 = url.substring(url.indexOf("-")+1);
        this.lastTime = chatRoom.getLastTime();

        List<ChatDB> db = chatRoom.getChatDBS();
        this.lastChat = db.get(db.size()-1).getContent();
    }
}
