package yyytir777.persist.global.oauth.service;

import yyytir777.persist.global.jwt.dto.JwtInfoDto;

public interface SocialLoginService {

    String login();

    JwtInfoDto callback(String authCode);
}
