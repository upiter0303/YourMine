package com.bit.yourmain.dto.posts;

import com.bit.yourmain.domain.posts.Posts;
import com.bit.yourmain.domain.users.Users;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final String status;
    private final Users users;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.status = entity.getStatus();
        this.users = entity.getUsers();
    }
}