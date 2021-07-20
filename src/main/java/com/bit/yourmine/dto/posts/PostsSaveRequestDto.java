package com.bit.yourmine.dto.posts;
import com.bit.yourmine.domain.posts.Posts;
import com.bit.yourmine.domain.users.Users;
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
    private String status;
    private String author;
    private Long price;
    private String area;
    private String way; //  A: 직거래, B: 택배
    private String ofSize;
    private String category;
    private Long hit;
    private Users users;

    @Builder
    public PostsSaveRequestDto(String title, String content, String status, String author, Long price, String area, String way, String ofSize, String category, Long hit) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.author = author;
        this.price = price;
        this.area = area;
        this.way = way;
        this.ofSize = ofSize;
        this.category = category;
        this.hit = hit;
    }



    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .status(status)
                .price(price)
                .area(area)
                .way(way)
                .ofSize(ofSize)
                .category(category)
                .hit(hit)
                .users(users)
                .build();
    }
}