package com.bit.yourmine.domain.posts;

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
    private String buyerReviewContent;

    @Column
    private String sellerReviewContent;

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
}
