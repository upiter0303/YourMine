package com.bit.yourmine.domain.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    SEMI("ROLE_SEMI"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String value;
}