package com.bit.yourmine.dto.reviews;

import com.bit.yourmine.domain.posts.Review;
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

    private Long sellerScore;
    private Long buyerScore;
    private String sellerReviewContent;
    private String buyerReviewContent;

    public ReviewResponseDto(Review review) {
        this.no = review.getNo();
        this.postId = review.getPostId();
        this.buyer = review.getBuyer();
        this.seller = review.getSeller();

        this.sellerScore = review.getSellerScore();
        this.buyerScore = review.getBuyerScore();
        this.sellerReviewContent = review.getSellerReviewContent();
        this.buyerReviewContent = review.getBuyerReviewContent();
    }
}
