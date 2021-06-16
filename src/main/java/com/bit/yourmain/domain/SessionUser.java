package com.bit.yourmain.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private final Long no;
    private final String name;
    private final String id;
    private final String phone;
    private final String address;
    private final String detailAddress;
    private String profile;
    private final Role role;
    private final String password;

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public SessionUser(Users users) {
        this.no = users.getNo();
        this.name = users.getName();
        this.id = users.getId();
        this.phone = users.getPhone();
        this.address = users.getAddress();
        this.detailAddress = users.getDetailAddress();
        this.profile = users.getProfile();
        this.role = users.getRole();
        this.password = users.getPassword();
    }
}
