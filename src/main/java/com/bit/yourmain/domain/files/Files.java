package com.bit.yourmain.domain.files;

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

    @Builder
    public Files(String fileName) {
        this.fileName = fileName;
    }
}
