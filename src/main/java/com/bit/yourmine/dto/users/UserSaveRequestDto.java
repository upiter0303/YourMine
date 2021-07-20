package com.bit.yourmine.dto.users;

import com.bit.yourmine.domain.users.Role;
import com.bit.yourmine.domain.users.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserSaveRequestDto {

    private String name;
    private String id;
    private String password;
    private String phone;
    private String address;
    private String detailAddress;
    private String email;
    private Long score;
    private Role role;

    public Users toEntity() {
        return Users.builder()
                    .name(name)
                    .id(id)
                    .password(password)
                    .phone(phone)
                    .address(address)
                    .detailAddress(detailAddress)
                    .email(email)
                    .score(score)
                    .role(role)
                    .build();
    }
}
