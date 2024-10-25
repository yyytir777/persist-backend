package yyytir777.persist.global.oauth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yyytir777.persist.global.jwt.dto.JwtInfoDto;
import yyytir777.persist.global.oauth.service.SocialLoginService;
import yyytir777.persist.global.response.ApiResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "SocialLogin API Controller")
public class SocialLoginCallbackController {

    private final SocialLoginService socialLoginService;

//    @GetMapping("/oauth/kakao/callback")
//    public ApiResponse<JwtInfoDto> kakaoCallback(String code) {
//        return ApiResponse.onSuccess(kakaoLoginService.callback(code));
//    }

    @GetMapping("/oauth/google/callback")
    public ApiResponse<JwtInfoDto> googleCallback(String code) {
        return ApiResponse.onSuccess(socialLoginService.callback(code));
    }
}