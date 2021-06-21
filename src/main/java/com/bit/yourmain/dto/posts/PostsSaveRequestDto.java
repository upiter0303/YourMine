package com.bit.yourmain.dto.posts;
import com.bit.yourmain.domain.posts.Posts;
import com.bit.yourmain.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private String author;
    private String status;
    private Users users;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author, String status) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.status = status;
    }



    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .status(status)
                .users(users)
                .build();
    }
}