package yyytir777.persist.global.jwt.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yyytir777.persist.global.jwt.dto.AccessTokenResponseDto;
import yyytir777.persist.global.jwt.service.TokenService;
import yyytir777.persist.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/reissue")
    public ApiResponse<AccessTokenResponseDto> createAccessToken(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        String refreshToken = header.split(" ")[1];

        return ApiResponse.onSuccess(tokenService.createAccessTokenByRefreshToken(refreshToken));
    }

    @GetMapping("/memberId")
    public ApiResponse<String> getMemberIdByAccessToken(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        String accessToken = header.split(" ")[1];

        return ApiResponse.onSuccess(tokenService.getMemberIdByAccessToken(accessToken));
    }
}
