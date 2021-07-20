package com.bit.yourmine.domain.posts;

import com.bit.yourmine.domain.BaseTimeEntity;
import com.bit.yourmine.domain.files.Files;
import com.bit.yourmine.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "Posts_seq_gen",
        sequenceName = "posts_pk",
        initialValue = 1,
        allocationSize = 1
)
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "Posts_seq_gen")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String status;  //  거래대기, 거래중, 거래완료로 구분

    @Column(nullable = false)
    private Long price;

    @Column(nullable = true)
    private String area;

    @Column(nullable = true)
    private String way;

    @Column(nullable = true)
    private String ofSize;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Long hit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Users_no")
    private Users users;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "posts", cascade = CascadeType.REMOVE)
    private final List<Files> files = new ArrayList<>();

    @Builder
    public Posts(String title, String content, String status, Long price, String area, String way, String ofSize, String category, Long hit, Users users) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.price = price;
        this.area = area;
        this.way = way;
        this.ofSize = ofSize;
        this.category = category;
        this.hit = hit;
        this.users = users;
    }

    public void update(String title, String content, Long price, String area, String way, String ofSize, String category) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.area = area;
        this.way = way;
        this.ofSize = ofSize;
        this.category = category;
    }

    public void updateStatus(String status) {
        this.status = status;
    }
}