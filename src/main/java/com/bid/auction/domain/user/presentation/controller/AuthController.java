package com.bid.auction.domain.user.presentation.controller;

import com.bid.auction.domain.user.application.AuthService;
import com.bid.auction.domain.user.presentation.dto.TokenDto;
import com.bid.auction.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/reissue")
    public ApiResponse<TokenDto> reissue(String refreshToken){
        return ApiResponse.onSuccess(authService.reissueToken(refreshToken));
        //true, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), result
    }
}
