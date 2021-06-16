package com.bit.yourmain.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table
@SequenceGenerator(
        name = "Users_seq_gen",
        sequenceName = "users_pk",
        initialValue = 1,
        allocationSize = 1
)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "Users_seq_gen")
    private Long no;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String id;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String detailAddress;

    @Column(nullable = true)
    private String profile;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Users(String name, String id, String password, String phone, String address, String detailAddress, String profile, Role role) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.detailAddress = detailAddress;
        this.profile = profile;
        this.role = role;
    }

    public Users update(String id) {
        this.id = id;
        return this;
    }

    public Users develop(String phone, String address) {
        this.phone = phone;
        this.address = address;
        this.role = Role.USER;
        return this;
    }
}
