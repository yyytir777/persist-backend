package yyytir777.persist.global.oauth.service;

import yyytir777.persist.global.oauth.dto.CallbackResponse;

public interface SocialLoginService {

    String login();

    CallbackResponse callback(String authCode);
}
