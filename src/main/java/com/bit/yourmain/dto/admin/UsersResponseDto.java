package com.bit.yourmain.dto.admin;

import com.bit.yourmain.domain.users.Role;
import com.bit.yourmain.domain.users.Users;
import lombok.Getter;

@Getter
public class UsersResponseDto {
    private Long no;
    private String name;
    private String id;
    private String phone;
    private String address;
    private String detailAddress;
    private String email;
    private Long score;
    private Role role;

    public UsersResponseDto(Users entity) {
        this.no = entity.getNo();
        this.name = entity.getName();
        this.id = entity.getId();
        this.phone = entity.getPhone();
        this.address = entity.getAddress();
        this.detailAddress = entity.getDetailAddress();
        this.email = entity.getEmail();
        this.score = entity.getScore();
        this.role = entity.getRole();
    }
}
