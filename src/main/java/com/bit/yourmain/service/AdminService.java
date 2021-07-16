package com.bit.yourmain.service;

import com.bit.yourmain.domain.users.Users;
import com.bit.yourmain.domain.users.UsersRepository;
import com.bit.yourmain.dto.admin.UsersModifyRequestDto;
import com.bit.yourmain.dto.admin.UsersResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UsersRepository usersRepository;

    @Transactional
    public Long update(Long no, UsersModifyRequestDto requestDto) {
        Users users = usersRepository.findByNo(no).orElseThrow(() -> new IllegalArgumentException("해당 유저 없음!"));
        users.update(requestDto.getName(), requestDto.getPhone(), requestDto.getAddress(), requestDto.getDetailAddress(), requestDto.getEmail(), requestDto.getScore());
        return no;
    }

    @Transactional
    public void delete(Long no) {
        Users users = usersRepository.findByNo(no).orElseThrow(() -> new IllegalArgumentException("해당 유저 없음!"));
        usersRepository.delete(users);
    }

    public UsersResponseDto findByNo(Long no) {
        Users entity = usersRepository.findByNo(no).orElseThrow(() -> new IllegalArgumentException("해당 유저 없음!"));
        return new UsersResponseDto(entity);
    }

    public List<UsersResponseDto> getResponseDtoList(Pageable pageable) {
        return usersRepository.findAll(pageable).stream().map(UsersResponseDto::new).collect(Collectors.toList());
    }
}
