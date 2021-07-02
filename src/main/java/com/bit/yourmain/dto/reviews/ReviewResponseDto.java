package com.bit.yourmain.dto.reviews;

import com.bit.yourmain.domain.posts.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {
    private Long no;
    private Long postId;
    private String buyer;
    private String seller;
    private String title;
    private String position;

    public ReviewResponseDto(Review review) {
        this.no = review.getNo();
        this.postId = review.getPostId();
        this.buyer = review.getBuyer();
        this.seller = review.getSeller();
    }
}
