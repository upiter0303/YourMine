package com.bit.yourmain.service;

import com.bit.yourmain.domain.admin.AdminRepository;
import com.bit.yourmain.domain.users.Users;
import com.bit.yourmain.dto.admin.UsersModifyRequestDto;
import com.bit.yourmain.dto.admin.UsersResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Transactional
    public List<UsersResponseDto> findAll() {
        return adminRepository.findAll().stream().map(UsersResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long no, UsersModifyRequestDto requestDto) {
        Users users = adminRepository.findByNo(no).orElseThrow(() -> new IllegalArgumentException("해당 유저 없음!"));
        users.update(requestDto.getName(), requestDto.getPhone(), requestDto.getAddress(), requestDto.getDetailAddress(), requestDto.getEmail(), requestDto.getScore());
        return no;
    }

    public UsersResponseDto findByNo(Long no) {
        Users entity = adminRepository.findByNo(no).orElseThrow(() -> new IllegalArgumentException("해당 유저 없음!"));
        return new UsersResponseDto(entity);
    }
}
