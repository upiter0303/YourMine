package com.bit.yourmain.dto.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {

    private Long id;
    private String title;
    private String content;
    private String status;

    @Builder
    public PostsUpdateRequestDto(Long id, String title, String content, String status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
    }
}