//package yyytir777.persist.global.oauth.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import yyytir777.persist.domain.member.dto.MemberInfoDto;
//import yyytir777.persist.domain.member.entity.Member;
//import yyytir777.persist.domain.member.repository.MemberRepository;
//import yyytir777.persist.global.error.ErrorCode;
//import yyytir777.persist.global.error.exception.MemberException;
//import yyytir777.persist.global.jwt.JwtUtil;
//import yyytir777.persist.global.jwt.dto.JwtInfoDto;
//import yyytir777.persist.global.oauth.dto.kakao.KakaoInfoResponseDto;
//import yyytir777.persist.global.oauth.dto.kakao.KakaoTokenDto;
//
//@Service
//@RequiredArgsConstructor
//public class KakaoLoginServiceImpl implements SocialLoginService {
//
//    private final MemberRepository memberRepository;
//    private final JwtUtil jwtUtil;
//
//    private final RestTemplate restTemplate = new RestTemplate();
//    private final String content_type = "application/x-www-form-urlencoded;charset=utf-8";
//
//    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
//    private String clientId;
//
//    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
//    private String redirectUri;
//
//    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
//    private String loginUrl;
//
//    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
//    private String tokenUrl;
//
//    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
//    private String getInfoUrl;
//
//
//    @Override
//    public String login() {
//        return loginUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code";
//    }
//
//    @Override
//    public JwtInfoDto callback(String authCode) {
//        KakaoTokenDto.Response responseDto = getToken(authCode);
//        String email = getEmail(responseDto.getAccess_token());
//
//        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
//                new MemberException(ErrorCode.MEMBER_NOT_EXIST));
//
//        return jwtUtil.createToken(MemberInfoDto.of(member));
//    }
//
//    private KakaoTokenDto.Response getToken(String code) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-type", content_type);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        String grant_type = "authorization_code";
//        params.add("grant_type", grant_type);
//        params.add("client_id", clientId);
//        params.add("redirect_uri", redirectUri);
//        params.add("code", code);
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
//
//        ResponseEntity<KakaoTokenDto.Response> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, KakaoTokenDto.Response.class);
//        return response.getBody();
//    }
//
//    private String getEmail(String accessToken) {
//        KakaoInfoResponseDto kakaoInfoResponseDto = getKakaoUserInfo(accessToken);
//        KakaoInfoResponseDto.KakaoAccount kakaoAccount = kakaoInfoResponseDto.getKakaoAccount();
//        return kakaoAccount.getEmail();
//    }
//
//    private KakaoInfoResponseDto getKakaoUserInfo(String accessToken) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + accessToken);
//        headers.set("Content-type", content_type);
//
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<KakaoInfoResponseDto> userInfoResponseDto = restTemplate.exchange(getInfoUrl, HttpMethod.GET, entity, KakaoInfoResponseDto.class);
//
//        return userInfoResponseDto.getBody();
//    }
//
//}
