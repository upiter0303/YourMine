package com.bit.yourmain.controller.api;

import com.bit.yourmain.dto.chat.ChatResponseDto;
import com.bit.yourmain.dto.chat.ChatSaveRequestDto;
import com.bit.yourmain.dto.chat.ReadCheckDto;
import com.bit.yourmain.service.ChatService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
                saveRequestDto.getContent(), saveRequestDto.getSpeaker(), saveRequestDto.getListener(), saveRequestDto.getRoomId());
    }

    @GetMapping("/chat/db/demand/{roomId}")
    public String chatResponse(@PathVariable String roomId) {
        List<ChatResponseDto> responseDto = new ArrayList<>();
        try {
            responseDto = chatService.chatResponse(roomId);
        } catch (Exception e) {
            System.out.println("null db");
        }
        return new Gson().toJson(responseDto);
    }

    @PutMapping("/chat/db/check")
    public void readCheck(@RequestBody ReadCheckDto readCheckDto) {
        chatService.readCheck(readCheckDto);
    }

    @GetMapping("/alarm/{id}")
    public boolean isNew(@PathVariable String id) {
        return chatService.isNew(id);
    }
}
