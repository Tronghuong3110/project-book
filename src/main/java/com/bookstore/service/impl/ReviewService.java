package com.bookstore.service.impl;

import com.bookstore.Exception.RescourceNotFoundException;
import com.bookstore.converter.ReviewConverter;
import com.bookstore.model.dto.ReviewDto;
import com.bookstore.model.entity.BookEntity;
import com.bookstore.model.entity.ReviewEntity;
import com.bookstore.model.entity.UserEntity;
import com.bookstore.model.response.ResponseReview;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.ReviewRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReviewService implements IReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public ReviewDto save(ReviewDto reviewDto, Long bookId) {
        // get user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity user = userRepository.findByUserName(userName).get();

        // get book
        BookEntity book = bookRepository.findOneByIdAndStatus(bookId, 0)
                .orElseThrow(() -> new RescourceNotFoundException());

        ReviewEntity reviewEntity = ReviewConverter.toEntity(reviewDto);
        reviewEntity.setUser(user);
        reviewEntity.setBook(book);
        reviewEntity.setCreateDate(new Date());
        reviewEntity = reviewRepository.save(reviewEntity);
        return ReviewConverter.toDto(reviewEntity);
    }


    @Override
    public ResponseReview findAll(Long bookId) {
        ResponseReview responseReview = new ResponseReview();
        List<ReviewEntity> entities = reviewRepository.findAllByBook_Id(bookId);
        List<ReviewDto> results = new ArrayList<>();
        double star = 0;
        double count = 0;
        for(ReviewEntity entity : entities) {
            results.add(ReviewConverter.toDto(entity));
            if(entity.getStar() != null) {
                star += entity.getStar();
                count ++;
            }
        }
        responseReview.setTotalReview((long)count);
        responseReview.setReviews(results);
        // solve star
        System.out.println(star / entities.size());
        star = (double)Math.round((star / count * 10)) / 10;
        responseReview.setStar(star);
        return responseReview;
    }


    // update review
    @Override
    public ResponseReview update(ReviewDto reviewDto) {
        // get user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String newUserName = authentication.getName();

        // get user owner
        ReviewEntity reviewEntity = reviewRepository.findById(reviewDto.getId())
                .orElseThrow(() -> new RescourceNotFoundException());
        UserEntity userEntity = reviewEntity.getUser();
        String ownerReview = userEntity.getUserName();
        if(!newUserName.equals(ownerReview)) {
            System.out.println(newUserName);
            System.out.println(ownerReview);
            return new ResponseReview("Bạn không có quyền chỉnh sửa bình luận này");
        }

        reviewEntity.setComment(reviewDto.getComment());
        reviewEntity.setModifiedDate(new Date());
        List<ReviewDto> tmp = new ArrayList<>();
        reviewEntity = reviewRepository.save(reviewEntity);
        tmp.add(ReviewConverter.toDto(reviewEntity));
        return new ResponseReview(tmp);
    }

    // delete review
    @Override
    public String delete(Long reviewId) {
        // get user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String newUserName = authentication.getName();

        // get user owner
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RescourceNotFoundException());
        UserEntity userEntity = reviewEntity.getUser();
        String ownerReview = userEntity.getUserName();

        if(!newUserName.equals(ownerReview)) {
            return "Bạn không có quyền xóa bình luận này!";
        }
        reviewRepository.deleteById(reviewId);
        return "Xóa bình luận thành công!";
    }

}

// add update delete comment ==> OK
