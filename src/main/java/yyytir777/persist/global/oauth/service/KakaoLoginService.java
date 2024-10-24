package yyytir777.persist.global.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import yyytir777.persist.global.oauth.dto.KakaoInfoResponseDto;
import yyytir777.persist.global.oauth.dto.KakaoTokenDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String content_type = "application/x-www-form-urlencoded;charset=utf-8";

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String client_id;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirect_uri;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String url;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String get_info_url;

    public KakaoTokenDto.Response getToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", content_type);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String grant_type = "authorization_code";
        params.add("grant_type", grant_type);
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<KakaoTokenDto.Response> response = restTemplate.exchange(url, HttpMethod.POST, entity, KakaoTokenDto.Response.class);

        return response.getBody();

    }

    public String getEmail(String accessToken) {
        KakaoInfoResponseDto userInfoResponseDto = getKakaoUserInfo(accessToken);
        log.info(userInfoResponseDto.getKakaoAccount().toString());
        KakaoInfoResponseDto.KakaoAccount kakaoAccount = userInfoResponseDto.getKakaoAccount();
        return kakaoAccount.getEmail();
    }

    private KakaoInfoResponseDto getKakaoUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-type", content_type);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoInfoResponseDto> userInfoResponseDto = restTemplate.exchange(get_info_url, HttpMethod.GET, entity, KakaoInfoResponseDto.class);

        return userInfoResponseDto.getBody();
    }
}
