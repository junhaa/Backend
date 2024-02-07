package com.bid.auction.domain.user.presentation.dto;


import com.bid.auction.domain.user.domain.entity.Role;
import com.bid.auction.domain.user.domain.entity.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserReqDto {

    String name;

    String nickName;
    String email;

    String password;
    String profileUrl;

    String phoneNum;

    String address;
    Role role;

    public static UserReqDto from(User user){
        return UserReqDto.builder()
                .name(user.getName())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .password(user.getPassword())
                .profileUrl(user.getProfileUrl())
                .phoneNum(user.getPhoneNum())
                .address(user.getAddress())
                .role(user.getRole())
                .build();
    }
}
