package com.bit.yourmine.domain.attention;

import com.bit.yourmine.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@SequenceGenerator(
        name = "Attention_seq_gen",
        sequenceName = "attention_pk",
        initialValue = 1,
        allocationSize = 1
)
public class Attention {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "Attention_seq_gen")
    private Long id;

    @Column(nullable = false)
    private Long postNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Users_no")
    private Users users;

    @Builder
    public Attention(Long postNo, Users users) {
        this.postNo = postNo;
        this.users = users;
    }
}
