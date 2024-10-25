package yyytir777.persist.global.oauth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yyytir777.persist.global.jwt.dto.JwtInfoDto;
import yyytir777.persist.global.oauth.service.SocialLoginService;
import yyytir777.persist.global.response.ApiResponse;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "SocialLogin API Controller")
public class CallbackController {

    private final Map<String, SocialLoginService> socialLoginServices;

    @GetMapping("/oauth/{provider}/callback")
    public ApiResponse<JwtInfoDto> callback(@PathVariable String provider,
                                            @RequestParam(name = "code") String authCode) {
        SocialLoginService socialLoginService = socialLoginServices.get(provider);
        if(socialLoginService == null) throw new IllegalArgumentException("지원하지 않는 로그인 제공자입니다.");

        return ApiResponse.onSuccess(socialLoginService.callback(authCode));
    }
}