package com.dia.delivery.user.controller;

import com.dia.delivery.common.jwt.JwtUtil;
import com.dia.delivery.user.UserRoleEnum;
import com.dia.delivery.user.dto.*;
import com.dia.delivery.common.dto.ApiResponseDto;
import com.dia.delivery.common.security.UserDetailsImpl;
import com.dia.delivery.user.service.RegisterEmail;
import com.dia.delivery.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Locale;

@Slf4j
//@Controller//-> 앞단 완료되면 바꿀 예정
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final MessageSource messageSource;
    private final RegisterEmail registerMail;
    @PostMapping("/users/signup")
    public void signup(@Valid @RequestBody AuthRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            throw new IllegalArgumentException(
                    messageSource.getMessage(
                            "not.signup.form",
                            null,
                            "Not Signup Form",
                            Locale.getDefault()
                    ));
        }
        userService.signup(requestDto);

    }

    /*@PostMapping("/user/email-auth")
    public ResponseEntity<ApiResponseDto> sendMail(@RequestBody EmailAuthRequestDto requestDto) throws MessagingException {
         userService.sendMail(requestDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("이메일보내기 성공", 200));
    }*/


    // 회원 관련 정보 받기
    @GetMapping("/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        UserRoleEnum role = userDetails.getUser().getRole();
    //    boolean isAdmin = (role == UserRoleEnum.ADMIN);
        boolean isOwner = (role == UserRoleEnum.OWNER);
        return new UserInfoDto(username, isOwner);
       // return new UserInfoDto(username, isAdmin, isOwner);
    }

    //회원정보 변경

    @PutMapping("/inform/{id}")
    public ResponseEntity<ApiResponseDto> changeUserPassword(@PathVariable Long id, @Valid @RequestBody UpdateRequestDto requestDto, BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            throw new IllegalArgumentException("비밀번호 입력 양식을 맞춰주세요.");
        }
        //비밀번호는 사용자에게 노출되면 안되니까 body로 받는다.
        userService.changeUserPassword(id,requestDto);
        return ResponseEntity.ok().body(new ApiResponseDto("회원수정 완료", HttpStatus.CREATED.value()));

    }

    // 회원탈퇴
    @DeleteMapping("/withdrawal")
    public void delete(@RequestBody DeleteRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.delete(requestDto, userDetails.getUser());

    }

    //메일 인증번호 받기

    @PostMapping("login/mailConfirm")
    @ResponseBody
    String mailConfirm(@RequestParam("email") String email) throws Exception {

        String code = registerMail.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }
}

// @Slf4j
// @RestController
// @RequiredArgsConstructor
// public class UserController {
//     private final UserService userService;
//     @PostMapping("/users/signup")
//     public ResponseEntity<ApiResponseDto> signUp(@RequestBody SignUpRequestDto requestDto){
//         userService.signup(requestDto);
//         return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("회원가입 성공", 200));
//     }

//     @GetMapping("/users/user-info")
//     public UserResponseDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
//         return new UserResponseDto(userDetails.getUser());
//     }
// }

