package com.bit.yourmain.service;

import com.bit.yourmain.domain.chat.ChatDB;
import com.bit.yourmain.domain.chat.ChatDBRepository;
import com.bit.yourmain.domain.chat.ChatRoom;
import com.bit.yourmain.domain.chat.ChatRoomRepository;
import com.bit.yourmain.dto.chat.ChatResponseDto;
import com.bit.yourmain.dto.chat.ChatRoomListDto;
import com.bit.yourmain.dto.chat.ReadCheckDto;
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

    public void roomCheck(String roomId) {
        ChatRoom chatRoom = null;
        try {
            chatRoom = roomRepository.findByIdentify(roomId).get();
        } catch (Exception e) {
            System.out.println("null room");
        }
        if (chatRoom == null) {
            roomRepository.save(new ChatRoom(roomId));
        }
    }

    public void chatSave(String content, String speaker, String listener, String roomId) {
        dbRepository.save(new ChatDB(content, speaker, listener, roomRepository.findByIdentify(roomId).get()));
    }

    public List<ChatResponseDto> chatResponse(String roomId) throws Exception {
        List<ChatDB> chatDBS = dbRepository.findAllByChatRoomNoOrderByCreatedDate(
                roomRepository.findByIdentify(roomId).get().getNo());
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
            listDto.setName(data.substring(data.indexOf("-")+1));
            roomListDtos.add(listDto);
        }
        return roomListDtos;
    }

    public List<ChatRoomListDto> getBuyList(String id) {
        List<ChatRoom> chatRooms = roomRepository.findBuyList("-" + id);
        List<ChatRoomListDto> roomListDtos = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            ChatRoomListDto listDto = new ChatRoomListDto(chatRoom);
            listDto.setTitle(postsService.findById(listDto.getPostId()).getTitle());
            roomListDtos.add(listDto);
        }
        return roomListDtos;
    }

    public void readCheck(ReadCheckDto readCheckDto) {
        List<ChatDB> chatDBList = roomRepository.findByIdentify(readCheckDto.getRoomId()).get().getChatDBS();
        for (ChatDB db: chatDBList) {
            if (db.getRead() == null && !db.getSpeaker().equals(readCheckDto.getUserName())) {
                dbRepository.readCheck(db.getNo());
            }
        }
    }

    public boolean isNew(String id) {
        List<ChatDB> isNew = dbRepository.findAllByListener(id);
        return !isNew.isEmpty();
    }

    public void delRoom(Long id) {
        roomRepository.delete(roomRepository.findById(id).get());
    }
}
