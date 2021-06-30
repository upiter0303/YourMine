package com.bit.yourmain.controller.api;

import com.bit.yourmain.dto.chat.ChatResponseDto;
import com.bit.yourmain.dto.chat.ChatSaveRequestDto;
import com.bit.yourmain.service.ChatService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatService chatService;

    @PostMapping("/chat/db")
    public void chatSet(@RequestBody ChatSaveRequestDto saveRequestDto) {
        chatService.roomCheck(saveRequestDto.getRoomId());
        chatService.chatSave(
                saveRequestDto.getContent(), saveRequestDto.getSpeaker(), saveRequestDto.getRoomId());
    }

    @GetMapping("/chat/db/demand/{roomId}")
    public String chatResponse(@PathVariable String roomId) {
        List<ChatResponseDto> responseDto = new ArrayList<>();
        try {
            responseDto = chatService.chatResponse(roomId);
        } catch (Exception e) {
            System.out.println("null db");
        }
        String json = new Gson().toJson(responseDto);
        return json;
    }
}
