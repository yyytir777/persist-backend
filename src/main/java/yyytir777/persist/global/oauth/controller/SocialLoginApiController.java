package yyytir777.persist.global.oauth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import yyytir777.persist.global.oauth.service.SocialLoginService;

import java.util.Map;

@Controller
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
@Tag(name = "OAuth2 API Controller")
public class SocialLoginApiController {

    private final Map<String, SocialLoginService> socialLoginServices;

    @GetMapping("/{provider}")
    public String login(@PathVariable String provider) {
        SocialLoginService socialLoginService = socialLoginServices.get(provider);
        if(socialLoginService == null) throw new IllegalArgumentException("지원하지 않는 로그인 제공자입니다.");

        String redirectUrl = socialLoginService.login();
        return "redirect:" + redirectUrl;

    }
}
