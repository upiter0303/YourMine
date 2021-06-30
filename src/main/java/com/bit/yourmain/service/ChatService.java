package com.bit.yourmain.service;

import com.bit.yourmain.domain.chat.ChatDB;
import com.bit.yourmain.domain.chat.ChatDBRepository;
import com.bit.yourmain.domain.chat.ChatRoom;
import com.bit.yourmain.domain.chat.ChatRoomRepository;
import com.bit.yourmain.dto.chat.ChatResponseDto;
import com.bit.yourmain.dto.chat.ChatRoomListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository roomRepository;
    private final ChatDBRepository dbRepository;
    private final PostsService postsService;
    private final UsersService usersService;

    public void roomCheck(String roomId) {
        ChatRoom chatRoom = null;
        try {
            chatRoom = roomRepository.findByIdentify(roomId);
        } catch (Exception e) {
            System.out.println("null room");
        }
        if (chatRoom == null) {
            roomRepository.save(new ChatRoom(roomId));
        }
    }

    public void chatSave(String content, String speaker, String roomId) {
        dbRepository.save(new ChatDB(content, speaker, roomRepository.findByIdentify(roomId)));
    }

    public List<ChatResponseDto> chatResponse(String roomId) throws Exception {
        List<ChatDB> chatDBS = dbRepository.findAllByChatRoomNoOrderByCreatedDate(
                roomRepository.findByIdentify(roomId).getNo());
        List<ChatResponseDto> responseDto = new ArrayList<>();
        for (ChatDB chatDB: chatDBS) {
            responseDto.add(new ChatResponseDto(chatDB));
        }
        return responseDto;
    }

    public List<ChatRoomListDto> getChatList(Long id) {
        List<ChatRoom> chatRooms = roomRepository.findAllByPostId(id);
        List<ChatRoomListDto> roomListDtos = new ArrayList<>();
        for (ChatRoom chatRoom: chatRooms) {
            ChatRoomListDto listDto = new ChatRoomListDto(chatRoom);
            listDto.setTitle(postsService.findById(listDto.getPostId()).getTitle());
            String data = listDto.getIdentify();
            System.out.println(data.substring(data.indexOf("-")+1));
            listDto.setName(data.substring(data.indexOf("-")+1));
            roomListDtos.add(listDto);
        }
        return roomListDtos;
    }
}
