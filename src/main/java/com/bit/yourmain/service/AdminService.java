package com.bit.yourmain.service;

import com.bit.yourmain.domain.admin.AdminRepository;
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
}
