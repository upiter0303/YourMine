package com.bit.yourmain.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    SEMI("ROLE_SEMI"),
    USER("ROLE_USER");

    private final String value;
}