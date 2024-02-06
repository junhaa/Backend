package com.bid.auction.domain.user.domain.repository;

import com.bid.auction.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String num);
    Optional<User> findByEmail(String email);
}
