package com.bit.yourmain.controller.api;

import com.bit.yourmain.dto.chat.*;
import com.bit.yourmain.service.ChatService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatService chatService;

    @PostMapping("/chat/db")
    public void chatSet(@RequestBody ChatSaveRequestDto saveRequestDto) {
        chatService.roomCheck(saveRequestDto.getRoomId());
        chatService.chatSave(
                saveRequestDto.getContent(), saveRequestDto.getSpeaker(), saveRequestDto.getListener(),
                saveRequestDto.getFulTime(), saveRequestDto.getRoomId());
        chatService.chatIn(saveRequestDto.getRoomId());
    }

    @GetMapping("/chat/db/demand/{roomId}")
    public String chatResponse(@PathVariable String roomId) {
        List<ChatResponseDto> responseDto = null;
        try {
            responseDto = chatService.chatResponse(roomId);
        } catch (NoSuchElementException e) {
            System.out.println("room null");
        }
        return new Gson().toJson(responseDto);
    }

    @GetMapping("/chat/db/list/{id}")
    public String myChatList(@PathVariable String id) {
        List<ChatRoomListDto> list = chatService.getSortList(id);
        return new Gson().toJson(list);
    }

    @PutMapping("/readCheck")
    public void readCheck(@RequestBody ReadCheckDto readCheckDto) {
        try {
            chatService.readCheck(readCheckDto);
        } catch (NoSuchElementException e) {
            System.out.println("chat null");
        }
    }

    @GetMapping("/alarm/{id}")
    public boolean isNew(@PathVariable String id) {
        return chatService.isNew(id);
    }

    @PutMapping("/chatOut")
    public void chatOut(@RequestBody ChatOutDto chatOutDto) {
        if (chatOutDto.getId().equals(chatOutDto.getIdentify().substring(chatOutDto.getIdentify().indexOf("-")+1))) {
            chatOutDto.setPosition("buyer");
        } else {
            chatOutDto.setPosition("seller");
        }
        chatService.chatOut(chatOutDto);
    }
}
