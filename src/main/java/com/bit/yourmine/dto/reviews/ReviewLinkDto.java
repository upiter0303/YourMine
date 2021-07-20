package com.bit.yourmine.dto.reviews;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewLinkDto {
    private String title;
    private String postId;
    private String userId;
}
