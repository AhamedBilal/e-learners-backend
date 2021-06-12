package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    boolean existsByCourse_IdAndUser_Username(Long aLong, String username);
}
