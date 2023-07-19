package com.dia.delivery.user.dto;

import com.dia.delivery.user.UserRoleEnum;
import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private String username;
    private String password;
    private UserRoleEnum role = UserRoleEnum.OWNER; // 회원 권한 (ADMIN, USER)
}