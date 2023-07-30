package com.grandlies.reviewservices.controllers;

import com.grandlies.reviewservices.entity.Review;
import com.grandlies.reviewservices.models.ReviewListResponse;
import com.grandlies.reviewservices.models.ReviewResponse;
import com.grandlies.reviewservices.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private  ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @GetMapping("/{visibility}")
    public ResponseEntity<ReviewListResponse> getAllReviewsByVisibility(@PathVariable("visibility") boolean visibility) {
        return ResponseEntity.ok(reviewService.getAllReviewsByVisibility(visibility));
    }
    @GetMapping("/{visibility}/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable("visibility") boolean visibility, @PathVariable("id") Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(visibility, id));
    }
    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@RequestBody Review review) {
        return ResponseEntity.ok(reviewService.createReview(review));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable("id") Long id, @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.updateReview(id, review));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewResponse> deleteReview(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reviewService.deleteReview(id));
    }
}
