package com.bit.yourmain.service;

import com.bit.yourmain.domain.attention.Attention;
import com.bit.yourmain.domain.attention.AttentionRepository;
import com.bit.yourmain.domain.users.Users;
import com.bit.yourmain.dto.attention.AttentionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttentionService {

    private final AttentionRepository attentionRepository;
    private final UsersService usersService;

    public boolean attentionCheck(AttentionRequestDto requestDto) {
        Users users = usersService.getUsers(requestDto.getUserId());
        Attention attention = null;
        try {
            attention = attentionRepository.findByUsersNoAndPostNo(users.getNo(), requestDto.getPostNo());
        } catch (NullPointerException e) {

        }
        return attention == null;
    }

    public boolean attentionSave(AttentionRequestDto requestDto) {
        Users users = usersService.getUsers(requestDto.getUserId());
        attentionRepository.save(requestDto.toEntity(users));
        return true;
    }

    public boolean attentionDelete(AttentionRequestDto requestDto) {
        Users users = usersService.getUsers(requestDto.getUserId());
        Attention attention = attentionRepository.findByUsersNoAndPostNo(users.getNo(), requestDto.getPostNo());
        attentionRepository.delete(attention);
        return false;
    }

    public List<Long> findAllByUsersNo(Long no) {
        List<Attention> attentionList = attentionRepository.findAllByUsersNo(no);
        List<Long> idList = new ArrayList<>();
        for (Attention attention: attentionList) {
            idList.add(attention.getPostNo());
        }
        return idList;
    }
}
