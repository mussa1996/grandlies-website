package com.grandlies.reviewservices.services;

import com.grandlies.reviewservices.entity.Review;
import com.grandlies.reviewservices.exceptions.InvalidReviewException;
import com.grandlies.reviewservices.repository.ReviewRepository;
import com.grandlies.reviewservices.models.ReviewListResponse;
import com.grandlies.reviewservices.models.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private  ReviewRepository reviewRepository;
    private  Map<Boolean, String> reviewStatusMessages = new HashMap<>();

    // Constructor to inject ReviewRepository and initialize the reviewStatusMessages map
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        reviewStatusMessages.put(true, "Visible review found");
        reviewStatusMessages.put(false, "Invisible review found");
    }
    @PreAuthorize("hasRole('Admin') or hasRole('User')")
    public ReviewListResponse getAllReviewsByVisibility(boolean visibility) {
        List<Review> reviews = visibility ? reviewRepository.findByVisible(true) : reviewRepository.findByVisible(false);
        String message = reviews.isEmpty() ? "No " + (visibility ? "visible" : "invisible") + " reviews found."
                : "List of " + (visibility ? "visible" : "invisible") + " reviews fetched successfully";
        return new ReviewListResponse(reviews, message);
    }
    @PreAuthorize("hasRole('Admin') or hasRole('User')")
    public ReviewResponse getReviewById(boolean visibility, Long id) {
        Optional<Review> reviewOptional = reviewRepository.findByIdAndVisible(id, visibility);
        String notFoundMessage = reviewStatusMessages.get(visibility);
        Review review = reviewOptional.orElse(null);
        String message = review != null ? notFoundMessage : "Review not found";
        return new ReviewResponse(message, review);
    }
    @PreAuthorize("hasRole('Admin') or hasRole('User')")
    public ReviewResponse createReview(Review review) {
        if (review.getRating() == 0 || review.getContent() == null || review.getContent().isEmpty()) {
            throw new InvalidReviewException("Rating and content are required.");
        }
        review.setVisible(null);
        Review savedReview = reviewRepository.save(review);
        return new ReviewResponse("Review created successfully", savedReview);
    }
    @PreAuthorize("hasRole('Admin') or hasRole('User')")
    public ReviewResponse updateReview(Long id, Review review) {
        Optional<Review> existingReviewOptional = reviewRepository.findById(id);
        if (existingReviewOptional.isPresent()) {
            Review existingReview = existingReviewOptional.get();
            if (!existingReview.getVisible() && review.getVisible() != null && review.getVisible()) {
                existingReview.setVisible(true);
            }
            Review updatedReview = reviewRepository.save(existingReview);
            return new ReviewResponse("Review visibility updated successfully", updatedReview);
        } else {
            return new ReviewResponse("Review not found", null);
        }
    }
    @PreAuthorize("hasRole('Admin') or hasRole('User')")
    public ReviewResponse deleteReview(Long id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            reviewRepository.deleteById(id);
            return new ReviewResponse("Review deleted successfully", review);
        } else {
            return new ReviewResponse("Review not found", null);
        }
    }
}
