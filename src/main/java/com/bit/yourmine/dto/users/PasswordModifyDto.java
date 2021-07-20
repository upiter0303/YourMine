package com.bit.yourmine.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordModifyDto {
    private final String id;
    private final String password;
}
