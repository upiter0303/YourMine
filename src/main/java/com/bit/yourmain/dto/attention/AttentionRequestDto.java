package com.bit.yourmain.dto.attention;

import com.bit.yourmain.domain.attention.Attention;
import com.bit.yourmain.domain.users.Users;
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
