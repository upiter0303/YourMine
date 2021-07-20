package com.bit.yourmine.dto.attention;

import com.bit.yourmine.domain.attention.Attention;
import com.bit.yourmine.domain.users.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttentionRequestDto {

    private final String userId;
    private final Long postNo;

    public Attention toEntity(Users users) {
        return Attention.builder()
                        .postNo(postNo)
                        .users(users)
                        .build();
    }
}
