package com.bit.yourmain.domain.posts;

import com.bit.yourmain.domain.BaseTimeEntity;
import com.bit.yourmain.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String author;
    
    @Column(nullable = false)
    private String status;  //  거래대기, 거래중, 거래완료로 구분

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Users_no")
    private Users users;

    @Builder
    public Posts(String title, String content, String author, String status, Users users) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.status = status;
        this.users = users;
    }

    public void update(String title, String content, String status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }
}