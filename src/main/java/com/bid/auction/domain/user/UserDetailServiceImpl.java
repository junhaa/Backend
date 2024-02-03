package com.bid.auction.domain.user;

import com.bid.auction.global.exception.GeneralException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.bid.auction.global.enums.statuscode.ErrorStatus.INVALID_USER_NUM;

@Service
@Log4j2
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String num) throws GeneralException {
        User user = userRepository.findByName(num)
                .orElseThrow(() -> {
                    log.error(INVALID_USER_NUM.getMessage());
                    return new GeneralException(INVALID_USER_NUM);
                });

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        return new org
                .springframework
                .security
                .core
                .userdetails
                .User(user.getName(), user.getPassword(), grantedAuthorities);
    }
}

