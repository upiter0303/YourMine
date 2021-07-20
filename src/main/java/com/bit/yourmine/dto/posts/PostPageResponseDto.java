package com.bit.yourmine.dto.posts;

public class PostPageResponseDto {
    private final Long id;
    private final String title;
    private final String status;
    private final Long price;
    private final String area;
    private final String category;
    private final String thumbnail;

    public PostPageResponseDto(PostsResponseDto entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.status = entity.getStatus();
        this.price = entity.getPrice();
        this.area = entity.getArea();
        this.category = entity.getCategory();
        this.thumbnail = entity.getThumbnail();
    }
}
