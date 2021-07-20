package com.bit.yourmine.domain.files;

import com.bit.yourmine.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "Files_seq_gen",
        sequenceName = "files_pk",
        initialValue = 1,
        allocationSize = 1
)
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "Files_seq_gen")
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Posts_no")
    private Posts posts;

    @Builder
    public Files(String fileName, Posts posts) {
        this.fileName = fileName;
        this.posts = posts;
    }
}
