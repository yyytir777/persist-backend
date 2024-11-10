package yyytir777.persist.global.oauth.dto.google;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

public class GoogleTokenDto {

    @Getter
    @Builder
    public static class Request {
        private String code;
        @JsonProperty("client_id")
        private String clientId;
        @JsonProperty("client_secret")
        private String clientSecret;
        @JsonProperty("redirect_uri")
        private String redirectUri;
        @JsonProperty("grant_type")
        private String grantType;
    }

    @Getter
    @Builder
    public static class Response {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("expires_in")
        private String expiresIn;
        private String scope;
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("id_token")
        private String idToken;
    }
}
