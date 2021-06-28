package com.bit.yourmain.domain.users;

import com.bit.yourmain.domain.attention.Attention;
import com.bit.yourmain.domain.posts.Posts;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private Long score;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users", cascade = CascadeType.REMOVE)
    private List<Posts> posts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users", cascade = CascadeType.REMOVE)
    private List<Attention> attentions = new ArrayList<>();

    @Builder
    public Users(String name, String id, String password, String phone, String address, String detailAddress, String profile, Long score, Role role) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.detailAddress = detailAddress;
        this.profile = profile;
        this.score = score;
        this.role = role;
    }

    public Users update(String id) {
        this.id = id;
        return this;
    }
}
