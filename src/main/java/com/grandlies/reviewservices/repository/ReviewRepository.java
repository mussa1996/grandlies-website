package com.grandlies.reviewservices.repository;

import com.grandlies.reviewservices.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByVisible(Boolean visible);

    Optional<Review> findByIdAndVisible(Long id, Boolean visible);
}

