package com.bookstore.model.response;

import com.bookstore.model.dto.ReviewDto;

import java.util.List;

public class ResponseReview {
    private Long totalReview;
    private double star;
    private List<ReviewDto> reviews;
    private String message;

    public ResponseReview() {
    }

    public ResponseReview(String message) {
        this.message = message;
    }

    public ResponseReview(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }

    public Long getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(Long totalReview) {
        this.totalReview = totalReview;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
