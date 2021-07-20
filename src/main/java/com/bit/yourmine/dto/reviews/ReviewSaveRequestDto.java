package com.bit.yourmine.dto.reviews;

import com.bit.yourmine.domain.posts.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewSaveRequestDto {
    private Long postId;
    private String buyer;
    private String seller;

    @Builder
    public ReviewSaveRequestDto(String seller, String roomId) {
        this.postId = Long.parseLong(roomId.substring(0, roomId.indexOf("-")));
        this.buyer = roomId.substring(roomId.indexOf("-")+1);
        this.seller = seller;
    }

    public Review toEntity() {
        return Review.builder()
                    .postId(postId)
                    .buyer(buyer)
                    .seller(seller)
                    .build();
    }
}
