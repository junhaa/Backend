package com.bid.auction.domain.user.application;

import com.bid.auction.domain.user.domain.entity.User;
import com.bid.auction.domain.user.domain.repository.UserRepository;
import com.bid.auction.domain.user.presentation.dto.TokenDto;
import com.bid.auction.domain.user.presentation.dto.UserLoginReqDto;
import com.bid.auction.domain.user.presentation.dto.UserReqDto;
import com.bid.auction.global.exception.GeneralException;
import com.bid.auction.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.bid.auction.global.enums.statuscode.ErrorStatus.INVALID_USER_PW;
import static com.bid.auction.global.enums.statuscode.ErrorStatus._USER_NOT_FOUND;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Log4j2
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserReqDto signUpUser(UserReqDto userCreateReqDto) throws GeneralException {
        validateDuplicateUser(userCreateReqDto.getEmail());
         User user = User.builder()
                 .name(userCreateReqDto.getName())
                 .nickName(userCreateReqDto.getNickName())
                 .email(userCreateReqDto.getEmail())
                 .password(passwordEncoder.encode(userCreateReqDto.getPassword()))
                 .profileUrl(userCreateReqDto.getProfileUrl())
                 .phoneNum(userCreateReqDto.getPhoneNum())
                 .address(userCreateReqDto.getAddress())
                 .role(userCreateReqDto.getRole())
                 .build();

        userRepository.save(user);

        return userCreateReqDto.from(user);
    }


    // 유저 중복 확인
    private void validateDuplicateUser(String email) throws GeneralException {
        Optional<User> findUsers = userRepository.findByEmail(email);
        if (!findUsers.isEmpty()){
            throw new GeneralException(_USER_NOT_FOUND);
        }
    }
    @Transactional
    public TokenDto login(UserLoginReqDto userLoginReqDto) throws GeneralException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginReqDto.getName(),
                            userLoginReqDto.getPassword()
                    )
            );

            TokenDto tokenDto = new TokenDto(
                    jwtTokenProvider.createAccessToken(authentication),
                    jwtTokenProvider.createRefreshToken(authentication)
            );

            return tokenDto;

        }catch(BadCredentialsException e){
            log.error(INVALID_USER_PW.getMessage());
            throw new GeneralException(INVALID_USER_PW);
        }
    }
}
