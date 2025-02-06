package yyytir777.persist.global.jwtToken.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.TokenException;
import yyytir777.persist.global.jwtToken.dto.JwtInfoDto;
import yyytir777.persist.global.jwtToken.service.TokenService;
import yyytir777.persist.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
public class JwtTokenController {

    private final TokenService tokenService;

    @PostMapping("/reissue")
    public ApiResponse<JwtInfoDto> createAccessToken(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        if(header == null) throw new TokenException(ErrorCode.HEADER_IS_NULL);

        String refreshToken = header.split(" ")[1];
        return ApiResponse.onSuccess(tokenService.createAccessTokenByRefreshToken(refreshToken));
    }

    @GetMapping("/memberId")
    public ApiResponse<Long> getMemberIdByAccessToken(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        if(header == null) throw new TokenException(ErrorCode.HEADER_IS_NULL);

        String accessToken = header.split(" ")[1];

        return ApiResponse.onSuccess(tokenService.getMemberIdByAccessToken(accessToken));
    }

    @GetMapping("/loginCheck")
    public ApiResponse<?> loginCheck() {
        return ApiResponse.onSuccess();
    }
}
