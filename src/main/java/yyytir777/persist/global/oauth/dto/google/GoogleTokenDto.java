package yyytir777.persist.global.oauth.dto.google;


import lombok.Builder;
import lombok.Getter;

public class GoogleTokenDto {

    @Getter
    @Builder
    public static class Request {
        private String code;
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private String grantType;
    }

    @Getter
    @Builder
    public static class Response {
        private String accessToken;
        private String expiresIn;
        private String scope;
        private String tokenType;
        private String idToken;
    }
}
