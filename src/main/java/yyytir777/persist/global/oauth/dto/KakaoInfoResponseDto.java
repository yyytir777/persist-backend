package yyytir777.persist.global.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoInfoResponseDto {
    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    public static class KakaoAccount {
        private String email;

        @JsonProperty("profile")
        private Profile profile;

        @Getter
        public static class Profile {
            private String nickname;
        }
    }
}