package com.bit.yourmain.service;

import com.bit.yourmain.domain.posts.Review;
import com.bit.yourmain.domain.posts.ReviewRepository;
import com.bit.yourmain.dto.reviews.ReviewResponseDto;
import com.bit.yourmain.dto.reviews.ReviewSaveRequestDto;
import com.bit.yourmain.dto.reviews.ReviewScoreSetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PostsService postsService;
    private final UsersService usersService;

    public void save(ReviewSaveRequestDto requestDto) {
        reviewRepository.save(requestDto.toEntity());
    }

    public ReviewResponseDto getReview(Long no) {
        return new ReviewResponseDto(reviewRepository.findByNo(no).get());
    }

    public List<ReviewResponseDto> getSellReview(String id) {
        return getList(reviewRepository.findSellReview(id), "seller");
    }

    public List<ReviewResponseDto> getBuyReview(String id) {
        return getList(reviewRepository.findBuyReview(id), "buyer");
    }

    public List<ReviewResponseDto> getList(List<Review> reviewList, String position) {
        List<ReviewResponseDto> responseDto = new ArrayList<>();
        try {
            for (Review review : reviewList) {
                ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
                reviewResponseDto.setTitle(postsService.findById(review.getPostId()).getTitle());
                reviewResponseDto.setPosition(position);
                responseDto.add(reviewResponseDto);
            }
        } catch (Exception e) {
            System.out.println("get List error");
        }
        return responseDto;
    }

    @Transactional
    public void setScore(ReviewScoreSetDto scoreSetDto) {
        try {
            if (scoreSetDto.getPosition().equals("buyer")) {
                reviewRepository.setBuyerScore(scoreSetDto.getNo(), scoreSetDto.getScore());
            } else {
                reviewRepository.setSellerScore(scoreSetDto.getNo(), scoreSetDto.getScore());
            }
            usersService.setScore(scoreSetDto);
        } catch (Exception e) {
            System.out.println("review set score error");
        }
    }
}
