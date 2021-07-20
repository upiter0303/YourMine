package com.bit.yourmine.dto.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostsUpdateRequestDto {

    private Long id;
    private String title;
    private String content;
    private Long price;
    private String area;
    private String way; //  A: 직거래, B: 택배
    private String ofSize;
    private String category;

    @Builder
    public PostsUpdateRequestDto(Long id, String title, String content, Long price, String area, String way, String ofSize, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.price = price;
        this.area = area;
        this.way = way;
        this.ofSize = ofSize;
        this.category = category;
    }
}