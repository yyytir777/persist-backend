package yyytir777.persist.global.oauth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yyytir777.persist.global.jwt.dto.JwtInfoDto;
import yyytir777.persist.global.oauth.dto.KakaoTokenDto;
import yyytir777.persist.global.oauth.service.ApplicationLoginService;
import yyytir777.persist.global.oauth.service.KakaoLoginService;
import yyytir777.persist.global.response.ApiResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "SocialLogin API Controller")
public class SocialLoginController {

    private final KakaoLoginService kakaoLoginService;
    private final ApplicationLoginService applicationLoginService;

    @GetMapping("/oauth/kakao/callback")
    public ApiResponse<JwtInfoDto> kakaoCallback(String code) {
        KakaoTokenDto.Response responseDto = kakaoLoginService.getToken(code);
        String email = kakaoLoginService.getEmail(responseDto.getAccess_token());
        JwtInfoDto jwtInfoDto = applicationLoginService.login(email);
        return ApiResponse.onSuccess(jwtInfoDto);
    }
}
