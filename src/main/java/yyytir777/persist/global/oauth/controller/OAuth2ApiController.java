package yyytir777.persist.global.oauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/oauth")
public class OAuth2ApiController {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String url;

    @Operation(summary = "카카오 로그인", description = "존재하지 않는 회원이면 회원가입으로 진행됨")
    @GetMapping("/kakao")
    public String kakaoLogin() {
        String redirectUrl = url + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code";
        return "redirect:" + redirectUrl;
    }
}
