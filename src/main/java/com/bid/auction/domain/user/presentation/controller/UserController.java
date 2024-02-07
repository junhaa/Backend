package com.bid.auction.domain.user.presentation.controller;

import com.bid.auction.domain.user.application.UserService;
import com.bid.auction.domain.user.presentation.dto.TokenDto;
import com.bid.auction.domain.user.presentation.dto.UserLoginReqDto;
import com.bid.auction.domain.user.presentation.dto.UserReqDto;
import com.bid.auction.global.exception.GeneralException;
import com.bid.auction.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ApiResponse<TokenDto> login(@Valid @RequestBody UserLoginReqDto userLoginReqDto) {
        System.out.println(userLoginReqDto.getName());
        return ApiResponse.onSuccess(userService.login(userLoginReqDto));
    }

    @PostMapping("/signup")
    public ApiResponse<UserReqDto> signUp(@Valid @RequestBody UserReqDto userCreateReqDto) {
        return ApiResponse.onSuccess(userService.signUpUser(userCreateReqDto));
    }
}
