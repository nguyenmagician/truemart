package com.example.truemart.repository;

import com.example.truemart.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
}
