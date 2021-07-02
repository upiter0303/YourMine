package com.bit.yourmain.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "Review_seq_gen",
        sequenceName = "review_pk",
        initialValue = 1,
        allocationSize = 1
)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "Review_seq_gen")
    private Long no;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private String buyer;

    @Column(nullable = false)
    private String seller;

    @Column
    private Long buyerScore;

    @Column
    private Long sellerScore;

    @Builder
    public Review(Long postId, String buyer, String seller) {
        this.postId = postId;
        this.buyer = buyer;
        this.seller = seller;
    }

    public void updateBuyerScore(Long score) {
        this.buyerScore = score;
    }

    public void updateSellerScore(Long score) {
        this.sellerScore = score;
    }
}