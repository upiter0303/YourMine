package com.bit.yourmine.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserModifyDto {
    private final String name;
    private final String id;
    private final String phone;
    private final String address;
    private final String detailAddress;
}
