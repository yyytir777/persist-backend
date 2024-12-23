package yyytir777.persist.global.oauth.dto.kakao;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class KakaoTokenDto {

    @Builder
    @Getter
    public static class Request{
        private String grant_type;
        private String client_id;
        private String redirect_uri;
        private String code;
    }

    @Builder @Getter @ToString
    public static class Response{
        private String token_type;
        private String access_token;
        private Integer expires_in;
        private String refresh_token;
        private Integer refresh_token_expires_in;
        private String scope;

    }
}
