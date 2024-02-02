package com.bid.auction.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bid.auction.domain.product.entity.AuctionPostImage;

public interface AuctionPostImageRepository extends JpaRepository<AuctionPostImage, Long> {
}
