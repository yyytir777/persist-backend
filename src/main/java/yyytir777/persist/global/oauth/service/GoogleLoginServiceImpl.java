package yyytir777.persist.global.oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import yyytir777.persist.domain.member.dto.MemberInfoDto;
import yyytir777.persist.domain.member.entity.Member;
import yyytir777.persist.domain.member.repository.MemberRepository;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.MemberException;
import yyytir777.persist.global.jwt.JwtUtil;
import yyytir777.persist.global.jwt.dto.JwtInfoDto;
import yyytir777.persist.global.oauth.dto.CallbackResponse;
import yyytir777.persist.global.oauth.dto.google.GoogleInfoResponseDto;
import yyytir777.persist.global.oauth.dto.google.GoogleTokenDto;


@Service("google")
@RequiredArgsConstructor
public class GoogleLoginServiceImpl implements SocialLoginService{

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.provider.google.authorization-uri}")
    private String loginUrl;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String getInfoUrl;


    @Override
    public String login() {
        return loginUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code" + "&scope=email profile";
    }

    @Override
    public CallbackResponse callback(String authCode) {
        GoogleTokenDto.Response responseDto = getToken(authCode);
        String email = getEmail(responseDto.getAccessToken());

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        JwtInfoDto jwtInfoDto = jwtUtil.createToken(MemberInfoDto.of(member));

        return CallbackResponse.getJwtInfoDto(jwtInfoDto);
    }

    private GoogleTokenDto.Response getToken(String authCode) {
        HttpHeaders headers = new HttpHeaders();
        String content_type = "application/x-www-form-urlencoded;charset=utf-8";
        headers.add("Content-type", content_type);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<GoogleTokenDto.Response> response = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, GoogleTokenDto.Response.class);
        return response.getBody();
    }

    private String getEmail(String accessToken) {
        GoogleInfoResponseDto googleInfoResponseDto = getGoogleUserInfo(accessToken);
        return googleInfoResponseDto.getEmail();
    }

    private GoogleInfoResponseDto getGoogleUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<GoogleInfoResponseDto> response = restTemplate.exchange(getInfoUrl, HttpMethod.GET, entity, GoogleInfoResponseDto.class);
        return response.getBody();
    }
}