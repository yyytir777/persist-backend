package yyytir777.persist.global.oauth.dto;

import lombok.Builder;
import lombok.Getter;
import yyytir777.persist.global.jwtToken.dto.JwtInfoDto;

@Getter
@Builder
public class CallbackResponse {

    private boolean isExisted;
    private JwtInfoDto jwtInfoDto;
    private String email;

    public static CallbackResponse getJwtInfoDto(JwtInfoDto jwtInfoDto) {
        return CallbackResponse.builder()
                .isExisted(true)
                .jwtInfoDto(jwtInfoDto)
                .email(null)
                .build();
    }

    public static CallbackResponse getEmail(String email) {
        return CallbackResponse.builder()
                .isExisted(false)
                .jwtInfoDto(null)
                .email(email)
                .build();
    }

    public boolean jwtInfoDtoIsNotNull() {
        return this.jwtInfoDto != null;
    }
}