package com.bit.yourmain.dto.reviews;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class ReviewScoreSetDto {
    private Long no;
    private Long score;
    private String position;
    private String id;

    public ReviewScoreSetDto(ReviewResponseDto reviewResponseDto) {
        this.no = reviewResponseDto.getNo();
        this.position = reviewResponseDto.getPosition();
    }
}