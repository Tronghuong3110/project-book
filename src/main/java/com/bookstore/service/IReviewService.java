package com.bookstore.service;

import com.bookstore.model.dto.ReviewDto;
import com.bookstore.model.response.ResponseReview;


public interface IReviewService {
    ReviewDto save(ReviewDto reviewDto, Long bookId);
    String delete(Long reviewId);
    ResponseReview findAll(Long bookId);
    ResponseReview update(ReviewDto reviewDto);
}
