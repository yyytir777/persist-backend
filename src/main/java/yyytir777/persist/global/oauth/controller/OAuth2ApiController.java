package yyytir777.persist.global.oauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yyytir777.persist.global.oauth.service.SocialLoginService;

@Controller
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
@Tag(name = "OAuth2 API Controller")
public class OAuth2ApiController {

    private final SocialLoginService socialLoginService;

//    @Operation(summary = "카카오 로그인", description = "존재하지 않는 회원이면 회원가입으로 진행됨")
//    @GetMapping("/kakao")
//    public String kakaoLogin() {
//        String redirectUrl = kakaoLoginService.login();
//        return "redirect:" + redirectUrl;
//    }

    @Operation(summary = "구글 로그인")
    @GetMapping("/google")
    public String googleLogin() {
        String redirectUrl = socialLoginService.login();
        return "redirect:" + redirectUrl;
    }
}
