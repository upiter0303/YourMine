package com.bit.yourmine.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersModifyRequestDto {
    private String name;
    // private String password;
    private String phone;
    private String address;
    private String detailAddress;
    private String email;
    private Long score;

    @Builder
    public UsersModifyRequestDto(String name, String phone, String address, String detailAddress, String email, Long score) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.detailAddress = detailAddress;
        this.email = email;
        this.score = score;
    }
}
